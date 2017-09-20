package com.bitnei.sync;

import com.bitnei.es.client.ElasticSearchClient;
import com.bitnei.kafka.consumer.ConsumerExecutor;
import com.bitnei.kafka.resolver.VehicleResolver;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.node.DiscoveryNode;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhaogd
 * Date: 2017/8/24
 */
public class VehicleSync {

    /**
     * 同步车辆信息入口
     *
     */
    public static void main(String[] args) {
        TransportClient client = ElasticSearchClient.getClient();
        List<DiscoveryNode> discoveryNodes = client.connectedNodes();
        for (DiscoveryNode discoveryNode : discoveryNodes) {
            System.out.println(discoveryNode.getHostAddress());
        }
        ConsumerExecutor.execute("SYNC_DATABASE_ES", new VehicleResolver());
    }
}
