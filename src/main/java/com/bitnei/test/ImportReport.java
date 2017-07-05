package com.bitnei.test;

import com.alibaba.fastjson.JSON;
import com.bitnei.es.client.ElasticSearchClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchScrollRequestBuilder;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.search.SearchHit;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by IntelliJ IDEA.
 * User: zhaogd
 * Date: 2017/7/3
 */
public class ImportReport {
    private static final Logger logger = LogManager.getLogger(ElasticSearchClient.class);

    private static TransportClient client;

    static {
        ElasticSearchClient.init();
        client = ElasticSearchClient.getClient();
    }

    private static String searchByScroll() {
        String index = "web";
        String type = "vehicle";
        // 搜索条件
        SearchRequestBuilder searchRequestBuilder = client.prepareSearch();
        searchRequestBuilder.setIndices(index);
        searchRequestBuilder.setTypes(type);
        searchRequestBuilder.setScroll(TimeValue.timeValueSeconds(5));
        // 执行
        SearchResponse searchResponse = searchRequestBuilder.get();
        String scrollId = searchResponse.getScrollId();
        logger.info("--------- searchByScroll scrollID {}", scrollId);
        SearchHit[] searchHits = searchResponse.getHits().getHits();
        for (SearchHit searchHit : searchHits) {
            String source = searchHit.getSource().toString();
            logger.info("--------- searchByScroll source {}", source);
            insert(searchHit);
        } // for
        return scrollId;
    }

    private static void searchByScrollId(String scrollId) {
        SearchScrollRequestBuilder searchScrollRequestBuilder;
        SearchResponse response;
        // 结果
        while (true) {
            logger.info("--------- searchByScroll scrollID {}", scrollId);
            searchScrollRequestBuilder = client.prepareSearchScroll(scrollId);
            // 重新设定滚动时间
            searchScrollRequestBuilder.setScroll(TimeValue.timeValueSeconds(5));
            // 请求
            response = searchScrollRequestBuilder.get();
            // 每次返回下一个批次结果 直到没有结果返回时停止 即hits数组空时
            if (response.getHits().getHits().length == 0) {
                try {
                    TimeUnit.MINUTES.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            } // if
            // 这一批次结果
            SearchHit[] searchHits = response.getHits().getHits();
            for (SearchHit searchHit : searchHits) {
                String source = searchHit.getSource().toString();
                logger.info("--------- searchByScroll source {}", source);
                insert(searchHit);
            } // for
            // 只有最近的滚动ID才能被使用
            scrollId = response.getScrollId();
        } // while
    }


    private static void insert(SearchHit hit) {
        for (int j = 1; j <= 30; j++) {
            Map<String, Object> report = new HashMap<>();
            report.put("vin", hit.getSource().get("vin").toString());
            report.put("id", UUID.randomUUID().toString());

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.MONTH, 5);
            calendar.set(Calendar.DAY_OF_MONTH, j);
            report.put("reportTime", calendar.getTime().getTime());

            report.put("onlineTimeSum", new Random().nextDouble() * 2.5 + 3);
            report.put("runTimeSum", new Random().nextDouble() * 2.5 + 3);
            report.put("runTimes", new Random().nextDouble() * 2.5 + 3);
            report.put("runKm", new Random().nextDouble() * 2.5 + 3);
            report.put("runTimeAvg", new Random().nextDouble() * 2.5 + 3);
            report.put("runKmAvg", new Random().nextDouble() * 2.5 + 3);
            report.put("runSpeedAvg", new Random().nextDouble() * 2.5 + 3);
            report.put("runTimeMax", new Random().nextDouble() * 2.5 + 3);
            report.put("runTimeMin", new Random().nextDouble() * 2.5 + 3);
            report.put("runKmMax", new Random().nextDouble() * 2.5 + 3);
            report.put("runSpeedMax", new Random().nextDouble() * 2.5 + 3);
            report.put("runVolMax", new Random().nextDouble() * 2.5 + 3);
            report.put("runVolMin", new Random().nextDouble() * 2.5 + 3);
            report.put("runCurMax", new Random().nextDouble() * 2.5 + 3);
            report.put("runCurMin", new Random().nextDouble() * 2.5 + 3);
            report.put("runSocMax", new Random().nextDouble() * 2.5 + 3);
            report.put("runSocMin", new Random().nextDouble() * 2.5 + 3);
            report.put("runSvolMax", new Random().nextDouble() * 2.5 + 3);
            report.put("runSvolMin", new Random().nextDouble() * 2.5 + 3);
            report.put("runCptempMax", new Random().nextDouble() * 2.5 + 3);
            report.put("runCptempMin", new Random().nextDouble() * 2.5 + 3);
            report.put("runEngtempMax", new Random().nextDouble() * 2.5 + 3);
            report.put("runEngtempMin", new Random().nextDouble() * 2.5 + 3);

            report.put("veh_type_id", hit.getSource().get("veh_type_id"));
            report.put("manu_unit_id", hit.getSource().get("manu_unit_id"));
            report.put("veh_model_id", hit.getSource().get("veh_model_id"));
            report.put("term_unit_id", hit.getSource().get("term_unit_id"));
            report.put("term_model_id", hit.getSource().get("term_model_id"));
            report.put("sys_division_id", hit.getSource().get("sys_division_id"));
            report.put("sys_store_point_id", hit.getSource().get("sys_store_point_id"));

            ElasticSearchClient.addIndexRequestToBulk("report", report.get("id").toString(), report);
            System.out.println(report.get("id"));
        }
    }


    private static void insertRunstate(SearchHit hit) {
        for (int j = 20; j <= 30; j++) {
            VehDayreportRunstatePojo runstate = new VehDayreportRunstatePojo();
            runstate.setVin(hit.getSource().get("vin").toString());
            runstate.setId(UUID.randomUUID().toString());

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.MONTH, 5);
            calendar.set(Calendar.DAY_OF_MONTH, j);
            runstate.setReportTime(calendar.getTime());

            runstate.setOnlineTimeSum(new Random().nextDouble() * 2.5 + 3);
            runstate.setRunTimeSum(new Random().nextDouble() * 2.5 + 3);
            runstate.setRunTimes(new Random().nextDouble() * 2.5 + 3);
            runstate.setRunKm(new Random().nextDouble() * 2.5 + 3);
            runstate.setRunTimeAvg(new Random().nextDouble() * 2.5 + 3);
            runstate.setRunKmAvg(new Random().nextDouble() * 2.5 + 3);
            runstate.setRunSpeedAvg(new Random().nextDouble() * 2.5 + 3);
            runstate.setRunTimeMax(new Random().nextDouble() * 2.5 + 3);
            runstate.setRunTimeMin(new Random().nextDouble() * 2.5 + 3);
            runstate.setRunKmMax(new Random().nextDouble() * 2.5 + 3);
            runstate.setRunSpeedMax(new Random().nextDouble() * 2.5 + 3);
            runstate.setRunVolMax(new Random().nextDouble() * 2.5 + 3);
            runstate.setRunVolMin(new Random().nextDouble() * 2.5 + 3);
            runstate.setRunCurMax(new Random().nextDouble() * 2.5 + 3);
            runstate.setRunCurMin(new Random().nextDouble() * 2.5 + 3);
            runstate.setRunSocMax(new Random().nextDouble() * 2.5 + 3);
            runstate.setRunSocMin(new Random().nextDouble() * 2.5 + 3);
            runstate.setRunSvolMax(new Random().nextDouble() * 2.5 + 3);
            runstate.setRunSvolMin(new Random().nextDouble() * 2.5 + 3);
            runstate.setRunCptempMax(new Random().nextDouble() * 2.5 + 3);
            runstate.setRunCptempMin(new Random().nextDouble() * 2.5 + 3);
            runstate.setRunEngtempMax(new Random().nextDouble() * 2.5 + 3);
            runstate.setRunEngtempMin(new Random().nextDouble() * 2.5 + 3);

            ElasticSearchClient.addParentIndexRequestToBulk("web", "runstate", runstate.getId(), hit.getId(), JSON.toJSONString(runstate));
            System.out.println(runstate.getId());
        }
    }

    public static void main(String[] args) {
        String scrollId = searchByScroll();
        searchByScrollId(scrollId);
    }
}
