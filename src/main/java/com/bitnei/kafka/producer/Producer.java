package com.bitnei.kafka.producer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import kafka.producer.ProducerConfig;

public class Producer {

	public static void main(String[] args) {
		ProducerConfig producerConfig = KafkaProducerConfig.createProducerConfig();
		KafkaProducer kafkaProducer = new KafkaProducer("testTopic", producerConfig);
		BufferedReader bufferedReader = null;
		try {
			bufferedReader = new BufferedReader(new FileReader("/home/es/m1.txt"));
			String data = null;
			while ((data = bufferedReader.readLine()) != null) {
				kafkaProducer.send(data.getBytes());
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
