package com.bitnei.kafka.consumer;

import java.io.IOException;
import java.util.Properties;

import kafka.consumer.ConsumerConfig;

public class KafkaConsumerConfig {

	public static ConsumerConfig createConfig(){
		Properties props = new Properties();  
		try {
			props.load(KafkaConsumerConfig.class.getResourceAsStream("/consumer.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		ConsumerConfig consumerConfig = new ConsumerConfig(props);  
		return consumerConfig;
	}
	
}
