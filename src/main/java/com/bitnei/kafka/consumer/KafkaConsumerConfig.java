package com.bitnei.kafka.consumer;

import kafka.consumer.ConsumerConfig;

import java.io.IOException;
import java.util.Properties;

public class KafkaConsumerConfig {

	public static ConsumerConfig createConfig(){
		Properties props = new Properties();  
		try {
			props.load(KafkaConsumerConfig.class.getResourceAsStream("/consumer.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ConsumerConfig(props);
	}
	
}
