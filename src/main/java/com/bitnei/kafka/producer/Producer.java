package com.bitnei.kafka.producer;

import kafka.producer.ProducerConfig;

import java.io.BufferedReader;

public class Producer {

	public static void main(String[] args) {
		ProducerConfig producerConfig = KafkaProducerConfig.createProducerConfig();
		KafkaProducer kafkaProducer = new KafkaProducer("SYNC_DATABASE_ES", producerConfig);
		BufferedReader bufferedReader = null;

		String message = "TYPE:3,VALUE:eyJjb25kaXRpb24iOiJzYWxlc0FyZWFJZCIsImNvbmRpdGlvblZhbHVlIjoiMTMzIiwidmFsdWUiOnsic2FsZXNBcmVhTmFtZSI6IuW4gi3ljLoiLCJzYWxlc0FyZWFQYXRoIjpbIjAiLCIxMTAiLCIxMzMiXX19";
		kafkaProducer.send(message.getBytes());

//		try {
//			bufferedReader = new BufferedReader(new FileReader("F:/test.txt"));
//			String data = null;
//			while ((data = bufferedReader.readLine()) != null) {
//				kafkaProducer.send(data.getBytes());
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				bufferedReader.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
			kafkaProducer.close();
//		}
	}
}
