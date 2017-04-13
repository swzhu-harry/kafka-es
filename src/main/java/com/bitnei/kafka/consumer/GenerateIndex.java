package com.bitnei.kafka.consumer;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.bitnei.es.builder.IndexNameBuilder;
import com.bitnei.es.client.ElasticSearchClient;
import com.bitnei.es.client.ElasticSearchConfig.ClientConfig;
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

		String topicName = args[0];
		ClientConfig.indexPrefix = args[1];
		ClientConfig.typeName = args[2];
		ignoreField = args[3] == null ? "" : args[3];

		logger.info("ignoreField：" + ignoreField);

		try {
			ElasticSearchClient.init();
			ConsumerConfig consumerConfig = KafkaConsumerConfig.createConfig();
			KafkaConsumer kafkaConsumerManager = new KafkaConsumer(consumerConfig, topicName, 6);

			while (true) {
				List<KafkaStream<byte[], byte[]>> kafkaStreams = kafkaConsumerManager.createKafkaStreams();
				// 创建一个固定线程池
				executor = Executors.newFixedThreadPool(kafkaStreams.size());
				for (final KafkaStream<byte[], byte[]> kafkaStream : kafkaStreams) {
					KafkaReader reader = new KafkaReader(kafkaStream);
					executor.submit(reader);
				}
				try {
					TimeUnit.MINUTES.sleep(60);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				executor.shutdown();
				executor = null;
				kafkaConsumerManager.shutdown();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
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
			while (it.hasNext()) {
				try {
					MessageAndMetadata<byte[], byte[]> mm = it.next();
					if (mm == null) {
						break;
					}
					String message = new String(mm.message(), "UTF-8");
					Map<String, String> keyValues = Utils.processMessageToMap(message);

					ignoreFields(keyValues);
					String vid = getVID(keyValues);
					long timeStamp = getTimeStamp(keyValues);
					String indexId = getIndexId(vid, timeStamp);
					
					String indexName = IndexNameBuilder.getIndexName(timeStamp);
					
					keyValues.put("createTime", String.valueOf(new Date().getTime()));

					ElasticSearchClient.addIndexRequestToBulk(indexName, indexId, keyValues);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
		}

		private void ignoreFields(Map<String, String> keyValues) {
			if (StringUtils.isNotBlank(ignoreField)) {
				String[] split = ignoreField.split(",");
				for (int i = 0; i < split.length; i++) {
					keyValues.remove(split[i]);
				}
			}
		}

		private String getIndexId(String VId, long timestamp) {
			String indexId = VId + "_" + timestamp;
			return indexId;
		}

		private String getVID(Map<String, String> keyValues) {
			String VId = keyValues.get("VID");
			return VId;
		}

		private long getTimeStamp(Map<String, String> keyValues) {
			String time = keyValues.get("2000");
			long timestamp = 0;
			try {
				timestamp = Utils.convertToUTC(time);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return timestamp;
		}
	}
}
