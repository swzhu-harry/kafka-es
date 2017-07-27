package com.bitnei.test;

import com.bitnei.es.client.ElasticSearchClient;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * User: zhaogd
 * Date: 2017/7/25
 */
public class UpdateTest {
    public static void main(String[] args) {
        Map<String, Object> map = new HashMap<>();
        while (true){
            map.put("onlined", new Random().nextInt(10));
            ElasticSearchClient.addUpdateRequestToBulk("web","vehicle","81e7410f-d81a-4714-9064-0df518d1e69b",map);
        }
    }
}
