package com.bitnei.kafka.resolver;

import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.message.MessageAndMetadata;

import java.io.UnsupportedEncodingException;

/**
 *
 * Created by IntelliJ IDEA.
 * User: zhaogd
 * Date: 2017/8/24
 */
public class ElectronicRailingResolver implements KafkaStreamResolver {

    private KafkaStream<byte[], byte[]> kafkaStream;

    public ElectronicRailingResolver(){
    }

    private ElectronicRailingResolver(KafkaStream<byte[], byte[]> kafkaStream) {
        this.kafkaStream = kafkaStream;
    }

    @Override
    public KafkaStreamResolver init(KafkaStream<byte[], byte[]> kafkaStream) {
        return new ElectronicRailingResolver(kafkaStream);
    }

    @Override
    public void run() {
        ConsumerIterator<byte[], byte[]> it = kafkaStream.iterator();
        while (it.hasNext()) {
            MessageAndMetadata<byte[], byte[]> mm = it.next();
            if (mm == null) {
                break;
            }
            try {
                String message = new String(mm.message(), "UTF-8");
                System.out.println(message);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }
}
