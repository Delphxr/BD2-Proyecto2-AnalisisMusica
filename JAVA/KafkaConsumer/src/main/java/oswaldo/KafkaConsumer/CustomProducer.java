package oswaldo.KafkaConsumer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;

import java.util.Properties;

public class CustomProducer {

    Producer<String, String> producer;

    public CustomProducer(){
        Properties props = new Properties();
        props.put("bootstrap.servers", "25.7.237.232:29093");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        this.producer = new KafkaProducer<>(props);
    }

    public Producer<String, String> getProducer() {
        return producer;
    }
}
