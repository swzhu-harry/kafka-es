package com.bitnei.kafka.producer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import kafka.producer.ProducerConfig;

public class Producer {
	private static final Logger logger = LogManager.getLogger(Producer.class);

	public static void main(String[] args) {
		ProducerConfig producerConfig = KafkaProducerConfig.createProducerConfig();
		KafkaProducer kafkaProducer = new KafkaProducer("testTopic1", producerConfig);
		BufferedReader bufferedReader = null;
		try {
			bufferedReader = new BufferedReader(new FileReader("/home/es/m1.txt"));
			String data = null;
			int i = 1;
			while ((data = bufferedReader.readLine()) != null) {
				kafkaProducer.send(data.getBytes());
				logger.info("读取" + i++ + "行");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				bufferedReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			kafkaProducer.close();
		}
	}
}
