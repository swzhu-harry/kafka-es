package com.bitnei.kafka.producer;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

public class KafkaProducer{

	private  Producer<byte[], byte[]> producer;
	private  String topic;
	byte[] value=null;
	
	public KafkaProducer(String topic,ProducerConfig config){
		this.topic = topic;
		producer = new Producer<byte[], byte[]>(config);
	}
	/**
	 * 
	 * @param messageByteArray
	 * 按topic发送消息
	 */
	public void send(byte[] messageByteArray){
		KeyedMessage<byte[],byte[]> km = new KeyedMessage<>(topic,messageByteArray);
		producer.send(km);
	}
	/**
	 * 按key分区发送
	 * @param key
	 * @param messageByteArray
	 */
	public void sendKeyMessage(byte[] key,byte[] messageByteArray){
		KeyedMessage<byte[],byte[]> km = new KeyedMessage<>(topic,key,messageByteArray);
		producer.send(km);
	}
	
	public void close(){
		if(producer!=null){
			producer.close();
		}
	}
}
