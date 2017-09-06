package com.bitnei.kafka.consumer;

import com.bitnei.es.client.ElasticSearchClient;
import com.bitnei.kafka.resolver.KafkaStreamResolver;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 消费者执行器
 * <p>
 * Created by IntelliJ IDEA.
 * User: zhaogd
 * Date: 2017/8/21
 */
public class ConsumerExecutor {

    private static final Logger logger = LogManager.getLogger(ConsumerExecutor.class);

    /**
     * 执行消费任务
     *
     * @param topicName           topic名称
     * @param kafkaStreamResolver 消息解析器
     * @see com.bitnei.kafka.resolver.KafkaStreamResolver
     */
    public static void execute(String topicName, KafkaStreamResolver kafkaStreamResolver) {
        try {
            // 初始化消费者
            ConsumerConfig consumerConfig = KafkaConsumerConfig.createConfig();
            KafkaConsumer kafkaConsumerManager = new KafkaConsumer(consumerConfig, topicName, 1);

            while (true) {
                List<KafkaStream<byte[], byte[]>> kafkaStreams = kafkaConsumerManager.createKafkaStreams();
                // 创建一个固定线程池
                ExecutorService executor = Executors.newFixedThreadPool(kafkaStreams.size());
                for (final KafkaStream<byte[], byte[]> kafkaStream : kafkaStreams) {
                    // 开启解析线程
                    executor.submit(kafkaStreamResolver.init(kafkaStream));
                }
                try {
                    TimeUnit.MINUTES.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                executor.shutdown();
                kafkaConsumerManager.shutdown();
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        } finally {
            ElasticSearchClient.close();
        }
    }
}
