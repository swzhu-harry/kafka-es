package com.bitnei.kafka.consumer;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;
import kafka.consumer.Whitelist;
import kafka.javaapi.consumer.ConsumerConnector;

import java.util.List;

/**
 *  kafka消费者
 *  Date: 2017/8/21
 */
public class KafkaConsumer {
    private ConsumerConfig config;

    private ConsumerConnector javaConsumerConnector;
    private int numStreams;
    private String topic;

    /**
     * @param config     参数配置
     * @param topic      消息的主题
     * @param numStreams 最小持有的分区数
     */
    public KafkaConsumer(ConsumerConfig config, String topic, int numStreams) {
        this.config = config;
        this.topic = topic;
        this.numStreams = numStreams;
    }

    public List<KafkaStream<byte[], byte[]>> createKafkaStreams() {
        javaConsumerConnector = Consumer.createJavaConsumerConnector(config);
        // topic的过滤器
        Whitelist whitelist = new Whitelist(topic);
        return javaConsumerConnector.createMessageStreamsByFilter(whitelist, numStreams);
    }

    public void shutdown() {
        javaConsumerConnector.shutdown();
        javaConsumerConnector = null;
    }
}
