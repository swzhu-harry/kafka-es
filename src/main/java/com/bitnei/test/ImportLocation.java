package com.bitnei.test;

import com.bitnei.es.client.ElasticSearchClient;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.client.transport.TransportClient;

import java.io.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by IntelliJ IDEA.
 * User: zhaogd
 * Date: 2017/7/3
 */
public class ImportLocation {
    private static final Logger logger = LogManager.getLogger(ElasticSearchClient.class);

    private static TransportClient client;

    static {
        ElasticSearchClient.init();
        client = ElasticSearchClient.getClient();
    }

    private static void readData() {
        InputStreamReader read = null;
        try {
            read = new InputStreamReader(new FileInputStream("/var/local/es/data.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assert read != null;
        BufferedReader bufferedReader = new BufferedReader(read);
        String lineTxt = null;
        try {
            while ((lineTxt = bufferedReader.readLine()) != null) {
                if (StringUtils.isNotBlank(lineTxt)) {
                    insert(lineTxt);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                read.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void insert(String data) {
        String[] split = data.split(",");
        if (split.length != 7) {
            return;
        }
        System.out.println(data);
        String lng = split[4].split(":")[1];
        String lat = split[5].split(":")[1];
        BigDecimal lngD = new BigDecimal(lng);
        String lngS = lngD.divide(BigDecimal.valueOf(1000000)).toString();
        BigDecimal latD = new BigDecimal(lat);
        String latS = latD.divide(BigDecimal.valueOf(1000000)).toString();

        if (StringUtils.isBlank(lngS) || StringUtils.isBlank(latS)) {
            return;
        }
        HashMap<String, Object> hashMap = new HashMap<>();
        HashMap<String, String> map = new HashMap<>();
        map.put("lon",lngS);
        map.put("lat",latS);
        hashMap.put("location", map);

        ElasticSearchClient.addIndexRequestToBulk("location", UUID.randomUUID().toString(), hashMap);
    }


    public static void main(String[] args) {
        readData();
//        insert("05110a85-849c-4bbf-a68e-16a8154b8df2_1498950551000,2000:20170702070911,2201:90,2202:156870,2502:116338935,2503:39754155,2615:248");
        try {
            TimeUnit.HOURS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
