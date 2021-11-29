package oswaldo.KafkaConsumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.UUID;

public class Consumer {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "25.7.237.232:29093");
        props.put("group.id", "kafkaConsumer"); // default topic name
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);
        Producer<String, String> producer = new CustomProducer().getProducer();

        //Kafka Consumer subscribes list of topics here.
        consumer.subscribe(Collections.singletonList("kafka"));  // replace you topic name

        //print the topic name

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, String> record : records) {
                System.out.printf("value = %s%n", record.value());
                producer.send(new ProducerRecord<>("memsql", UUID.randomUUID().toString(), record.value()));
            }
        }
    }


}
