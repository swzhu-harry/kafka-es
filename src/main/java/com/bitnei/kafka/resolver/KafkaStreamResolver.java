package com.bitnei.kafka.resolver;

import kafka.consumer.KafkaStream;


/**
 * Kafka消息解析器
 * <p>
 * Created by IntelliJ IDEA.
 * User: zhaogd
 * Date: 2017/8/21
 */
public interface KafkaStreamResolver extends Runnable {

    /**
     * 初始化解析器
     *
     * @param kafkaStream kafka流数据
     * @return 初始化之后的解析器
     */
    KafkaStreamResolver init(KafkaStream<byte[], byte[]> kafkaStream);
}
