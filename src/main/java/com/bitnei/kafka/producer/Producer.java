package com.bitnei.kafka.producer;

import kafka.producer.ProducerConfig;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Producer {

	public static void main(String[] args) {
		ProducerConfig producerConfig = KafkaProducerConfig.createProducerConfig();
		KafkaProducer kafkaProducer = new KafkaProducer("user_action_ya", producerConfig);
		BufferedReader bufferedReader = null;
		try {
			bufferedReader = new BufferedReader(new FileReader("F:/test.txt"));
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
