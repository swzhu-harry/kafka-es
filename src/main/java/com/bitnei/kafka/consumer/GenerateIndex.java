package com.bitnei.kafka.consumer;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.bitnei.es.client.ElasticSearchClient;
import com.bitnei.utils.Utils;

import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.message.MessageAndMetadata;

public class GenerateIndex {

	private static final Logger logger = LogManager.getLogger(GenerateIndex.class);

	private static ExecutorService executor = null;

	private static String ignoreField;

	public static void main(String[] args) {

		ignoreField = args[0];
		logger.info("ignoreField：" + ignoreField);

		try {
			ElasticSearchClient.init();
			ConsumerConfig consumerConfig = KafkaConsumerConfig.createConfig();
			KafkaConsumer kafkaConsumerManager = new KafkaConsumer(consumerConfig, "testTopic", 6);

			while (true) {
				logger.info("重新初始化");
				List<KafkaStream<byte[], byte[]>> kafkaStreams = kafkaConsumerManager.createKafkaStreams();
				// 创建一个固定线程池
				executor = Executors.newFixedThreadPool(kafkaStreams.size());
				for (final KafkaStream<byte[], byte[]> kafkaStream : kafkaStreams) {
					KafkaReader reader = new KafkaReader(kafkaStream);
					executor.submit(reader);
				}
				logger.info("等待中。。。");
				try {
					TimeUnit.MINUTES.sleep(600);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				executor.shutdown();
				executor = null;
				kafkaConsumerManager.shutdown();
			}
		} catch (Exception e) {
			ElasticSearchClient.close();
		}
	}

	private static class KafkaReader implements Runnable {

		private KafkaStream<byte[], byte[]> stream;

		public KafkaReader(KafkaStream<byte[], byte[]> stream) {
			this.stream = stream;
		}

		public void run() {
			ConsumerIterator<byte[], byte[]> it = stream.iterator();
			int a = 1;
			while (it.hasNext()) {
				try {
					MessageAndMetadata<byte[], byte[]> mm = it.next();
					if (mm == null) {
						break;
					}
					String message = new String(mm.message(), "UTF-8");
					Map<String, String> keyValues = Utils.processMessageToMap(message);
					String VID = keyValues.get("VID");
					String TIME = keyValues.get("2000");
					long timestamp = Utils.convertToUTC(TIME);

					String indexId = VID + "_" + timestamp;
					logger.info(indexId);
					if (StringUtils.isNotBlank(ignoreField)) {
						String[] split = ignoreField.split(",");
						for (int i = 0; i < split.length; i++) {
							keyValues.remove(split[i]);
						}
					}
					ElasticSearchClient.addUpdateBuilderToBulk(indexId, keyValues);
					System.out.println("索引计数：" + a++);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
