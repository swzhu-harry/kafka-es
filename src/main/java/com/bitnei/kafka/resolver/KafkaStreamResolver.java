package com.bitnei.kafka.resolver;

import kafka.consumer.KafkaStream; /**
 * Created by IntelliJ IDEA.
 * User: zhaogd
 * Date: 2017/8/21
 */
public interface KafkaStreamResolver extends Runnable{

    KafkaStreamResolver getInstance(KafkaStream<byte[], byte[]> kafkaStream);
}
