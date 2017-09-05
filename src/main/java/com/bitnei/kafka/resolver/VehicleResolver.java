package com.bitnei.kafka.resolver;

import com.alibaba.fastjson.JSON;
import com.bitnei.core.constant.SymbolConstant;
import com.bitnei.es.bean.EsVehiclePojo;
import com.bitnei.es.bean.VehicleUpdateBasics;
import com.bitnei.es.client.ElasticSearchClient;
import kafka.consumer.KafkaStream;
import kafka.message.MessageAndMetadata;
import org.apache.commons.codec.binary.Base64;
import org.elasticsearch.common.xcontent.NamedXContentRegistry;
import org.elasticsearch.common.xcontent.XContent;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.script.Script;

import java.io.IOException;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhaogd
 * Date: 2017/8/24
 */
public class VehicleResolver implements KafkaStreamResolver {

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

            System.out.println(message);

            String type = message.split(SymbolConstant.COMMA)[0].split(SymbolConstant.COLON)[1];
            String value = message.split(SymbolConstant.COMMA)[1].split(SymbolConstant.COLON)[1];

            String vehicleString = new String(Base64.decodeBase64(value));

            if (MessageType.ADD_OR_UPDATE.type.equals(type)) {
                List<EsVehiclePojo> vehiclePojos = JSON.parseArray(vehicleString, EsVehiclePojo.class);
                for (EsVehiclePojo vehiclePojo : vehiclePojos) {
                    ElasticSearchClient.addUpdateRequestToBulk("vehicle", vehiclePojo.getUuid(), JSON.toJSONString(vehiclePojo));
                }
                ElasticSearchClient.flush();
            } else if (MessageType.DEL.type.equals(type)) {
                List<String> uuids = JSON.parseArray(vehicleString, String.class);
                for (String uuid : uuids) {
                    ElasticSearchClient.addDeleteRequestToBulk("vehicle", uuid);
                }
                ElasticSearchClient.flush();
            } else if (MessageType.UPDATE_BASICS.type.equals(type)) {
                VehicleUpdateBasics updateBasics = JSON.parseObject(vehicleString, VehicleUpdateBasics.class);

                TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery(updateBasics.getCondition(), updateBasics.getConditionValue());

                Script script = null;
                try {
                    XContent xContent = XContentType.JSON.xContent();
                    script = Script.parse(xContent.createParser(NamedXContentRegistry.EMPTY, JSON.toJSONString(updateBasics.getConditionValue())));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                BulkByScrollResponse response = ElasticSearchClient.updateByQuery("vehicle", termQueryBuilder, script);
                System.out.println(response.getBulkFailures().size());
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
