package com.bitnei.test;

import com.alibaba.fastjson.JSON;
import com.bitnei.es.client.ElasticSearchClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.index.query.QueryBuilders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: zhaogd
 * Date: 2017/7/3
 */
public class GetLocation {
    private static final Logger logger = LogManager.getLogger(ElasticSearchClient.class);

    private static TransportClient client;

    static {
        ElasticSearchClient.init();
        client = ElasticSearchClient.getClient();
    }


    private static long getLocation(GeoPoint topLeft, GeoPoint bottomRight) {
        SearchRequestBuilder searchRequestBuilder = client.prepareSearch();
        searchRequestBuilder.setIndices("location");
        searchRequestBuilder.setTypes("doc");

        searchRequestBuilder.setQuery(QueryBuilders.geoBoundingBoxQuery("location").setCorners(topLeft, bottomRight));
        SearchResponse searchResponse = searchRequestBuilder.get();
        return searchResponse.getHits().getTotalHits();
    }

    private static Map<String, Object> GPS2BaiDu(double lng, double lat) {
        Map<String, Object> map = new HashMap<>();
        lng += 0.0127042854;
        lat += 0.00764409405;
        map.put("lng", lng);
        map.put("lat", lat);
        return map;
    }


    public static void main(String[] args) {
        GeoPoint bjTopLeft = new GeoPoint(41.066571, 115.428692);
        GeoPoint bjBottomRight = new GeoPoint(39.446875, 117.52171);

        ArrayList<Map<String, Object>> list = new ArrayList<>();
        for (double lng = bjTopLeft.getLon(); lng < bjBottomRight.getLon(); lng += 0.00301256) {
            for (double lat = bjBottomRight.getLat(); lat < bjTopLeft.getLat(); lat += 0.00241178) {
                GeoPoint topLeft = new GeoPoint(lat + 0.00241178, lng);
                GeoPoint bottomRight = new GeoPoint(lat, lng + 0.00301256);
                long total = getLocation(topLeft, bottomRight);
                if (total == 0) {
                    continue;
                }
                double lons = (topLeft.getLon() + bottomRight.getLon()) / 2;
                double lats = (topLeft.getLat() + bottomRight.getLat()) / 2;
                Map<String, Object> map = GPS2BaiDu(lons, lats);

                logger.error(topLeft.toString() + "====" + bottomRight.toString());
                logger.error(lons + "," + lats + "===" + total);
                map.put("count", total);
                list.add(map);
            }
        }
        logger.error(JSON.toJSONString(list));
    }
}
