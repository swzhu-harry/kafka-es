package com.bitnei.kafka.producer;

import java.io.IOException;
import java.util.Properties;

import kafka.producer.ProducerConfig;

public class KafkaProducerConfig {

	public static ProducerConfig createProducerConfig(){
		Properties props = new Properties();  
		try {
			props.load(KafkaProducerConfig.class.getResourceAsStream("/producer.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		ProducerConfig config = new ProducerConfig(props);
		return config;
	}
}
