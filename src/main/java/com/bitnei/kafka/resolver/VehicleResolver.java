package com.bitnei.kafka.resolver;

import com.alibaba.fastjson.JSON;
import com.bitnei.core.constant.SymbolConstant;
import com.bitnei.es.bean.EsVehiclePojo;
import com.bitnei.es.bean.VehicleUpdateBasics;
import com.bitnei.es.client.ElasticSearchClient;
import kafka.consumer.KafkaStream;
import kafka.message.MessageAndMetadata;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.script.Script;

import java.util.List;
import java.util.Map;

/**
 * 车辆表数据解析器
 * <p>
 * Created by IntelliJ IDEA.
 * User: zhaogd
 * Date: 2017/8/24
 */
public class VehicleResolver implements KafkaStreamResolver {

    private static final Logger logger = LogManager.getLogger(VehicleResolver.class);

    private KafkaStream<byte[], byte[]> kafkaStream;

    public VehicleResolver() {
    }

    private VehicleResolver(KafkaStream<byte[], byte[]> kafkaStream) {
        this.kafkaStream = kafkaStream;
    }

    @Override
    public KafkaStreamResolver init(KafkaStream<byte[], byte[]> kafkaStream) {
        return new VehicleResolver(kafkaStream);
    }

    @Override
    public void run() {
        for (MessageAndMetadata<byte[], byte[]> mm : kafkaStream) {
            if (mm == null) {
                break;
            }
            String message = new String(mm.message());

            if (logger.isDebugEnabled()) {
                logger.debug("接收到消息：" + message);
            }

            String type = message.split(SymbolConstant.COMMA)[0].split(SymbolConstant.COLON)[1];
            String value = message.split(SymbolConstant.COMMA)[1].split(SymbolConstant.COLON)[1];
            String vehicleString = new String(Base64.decodeBase64(value));

            if (logger.isDebugEnabled()) {
                logger.debug("解析后消息：" + vehicleString);
            }

            // 新增或修改车辆表
            if (MessageType.ADD_OR_UPDATE.type.equals(type)) {

                try {
                    List<EsVehiclePojo> vehiclePojos = JSON.parseArray(vehicleString, EsVehiclePojo.class);
                    for (EsVehiclePojo vehiclePojo : vehiclePojos) {
                        if (StringUtils.isNotBlank(vehiclePojo.getUuid())) {
                            ElasticSearchClient.addIndexRequestToBulk("vehicle", vehiclePojo.getUuid(), JSON.toJSONString(vehiclePojo));
                        }
                    }
                    ElasticSearchClient.flush();
                } catch (Exception e) {
                    logger.error("新增或修改车辆发生异常,消息体=" + vehicleString, e);
                }
            }

            // 删除车辆
            if (MessageType.DEL.type.equals(type)) {

                try {
                    List<String> uuids = JSON.parseArray(vehicleString, String.class);
                    for (String uuid : uuids) {
                        ElasticSearchClient.addDeleteRequestToBulk("vehicle", uuid);
                    }
                    ElasticSearchClient.flush();
                } catch (Exception e) {
                    logger.error("删除车辆发生异常,消息体=" + vehicleString, e);
                }
            }

            // 修改车辆基础信息
            if (MessageType.UPDATE_BASICS.type.equals(type)) {

                try {
                    VehicleUpdateBasics updateBasics = JSON.parseObject(vehicleString, VehicleUpdateBasics.class);

                    // 构建查询条件
                    TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery(updateBasics.getCondition(), updateBasics.getConditionValue());

                    // 构建修改脚本
                    StringBuilder scriptString = new StringBuilder();
                    Map<String, Object> objectMap = updateBasics.getValue();
                    for (Map.Entry<String, Object> entry : objectMap.entrySet()) {
                        scriptString.append("ctx._source.");
                        scriptString.append(entry.getKey());
                        scriptString.append("=params.");
                        scriptString.append(entry.getKey());
                        scriptString.append(";");
                    }
                    Script script = new Script(Script.DEFAULT_SCRIPT_TYPE, Script.DEFAULT_SCRIPT_LANG, scriptString.toString(), objectMap);

                    BulkByScrollResponse response = ElasticSearchClient.updateByQuery("vehicle", termQueryBuilder, script);

                    List<BulkItemResponse.Failure> bulkFailures = response.getBulkFailures();
                    for (BulkItemResponse.Failure bulkFailure : bulkFailures) {
                        logger.error("车辆基础信息更新失败：[ id : " + bulkFailure.getId() + ";" + "status : " + bulkFailure.getStatus() + ";" + "message : " + bulkFailure.getMessage() + "]");
                    }
                } catch (Exception e) {
                    logger.error("修改车辆基础信息发生异常,消息体=" + vehicleString, e);
                }
            }
        }
    }


    enum MessageType {

        ADD_OR_UPDATE("1"),
        DEL("2"),
        UPDATE_BASICS("3");

        private final String type;

        MessageType(String type) {
            this.type = type;
        }
    }
}
