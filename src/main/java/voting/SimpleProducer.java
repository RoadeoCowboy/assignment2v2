package voting;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import java.util.Properties;

/**
 * Created by davchen on 4/15/15.
 */

public class SimpleProducer {
    private static Producer<Integer, String> producer;
    private final Properties properties = new Properties();

    public SimpleProducer() {
//        properties.put("metadata.broker.list", "localhost:9092"); For testing on localhost only.
        properties.put("metadata.broker.list", "54.68.83.161:9092"); //For submission only, professor's endpoint.
        properties.put("serializer.class", "kafka.serializer.StringEncoder");
        properties.put("request.required.acks", "1");
        producer = new Producer<>(new ProducerConfig(properties));
    }


    public static void sendMessage(String inputTopic, String message) {
        new SimpleProducer();
        String topic = inputTopic;
        String msg = message;
        KeyedMessage<Integer, String> data = new KeyedMessage<>(topic, msg);
        producer.send(data);
        producer.close();
    }

}
