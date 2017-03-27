package com.bitnei.kafka.consumer;

import java.util.List;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;
import kafka.consumer.Whitelist;
import kafka.javaapi.consumer.ConsumerConnector;

/**
 * @ClassName: Consumer
 * @Description: kafka消费者
 * @author 卢海友
 * @date 2015-4-8 下午1:43:59
 */
public class KafkaConsumer {
	private ConsumerConfig config;

	private ConsumerConnector javaConsumerConnector;
	private int numStreams;
	private String topic;

	/**
	 * 
	 * @param config
	 *            参数配置
	 * @param topic
	 *            消息的主题
	 * @param numStreams
	 *            最小持有的分区数
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
		List<KafkaStream<byte[], byte[]>> streams = javaConsumerConnector.createMessageStreamsByFilter(whitelist,
				numStreams);
		return streams;
	}

	public void shutdown() {
		javaConsumerConnector.shutdown();
		javaConsumerConnector = null;
	}
}
