package oswaldo.KafkaConsumer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.StringSerializer;

public class KafkaTopicReader extends Thread{
	 private final ConsumerConnector consumer;
	 public static String topicName = "kafka"; //topic que vamos a escuchar
	 
	 Properties props = new Properties(); // para el producer
	 
	 
	 
	 

    public KafkaTopicReader(){
    	System.out.println("** Initialize **");
    	Properties props = new Properties();
    	props.put("zookeeper.connect","localhost:2181");
    	props.put("group.id","group_binod_test");
    	ConsumerConfig consumerConfig = new ConsumerConfig(props);
    	consumer = kafka.consumer.Consumer.createJavaConsumerConnector(consumerConfig);
    }
	public static void main(String[] args) {
		System.out.println("******* Consumer Started ***************");
		KafkaTopicReader demo = new KafkaTopicReader();
		demo.start();

	}
	
	public void send(ConsumerIterator<byte[], byte[]> messages){	
		props.setProperty("bootstrap.servers", "25.7.237.232:9092");
		props.setProperty("kafka.topic.name", "memsql"); //topic al que le vamos a escribir
		KafkaProducer<String, byte[]> producer = new KafkaProducer<String, byte[]>(this.props,new StringSerializer(), new ByteArraySerializer());
		
		int index = 0;
        while(messages.hasNext()){
        	index++;
        	String Message = new String(messages.next().message());
        	System.out.print("| Trabajando con el dato #" + String.valueOf(index) + "        |\r");
			byte[] payload = (Message).getBytes();
			ProducerRecord<String, byte[]> record = new ProducerRecord<String, byte[]>(props.getProperty("kafka.topic.name"), payload);
			producer.send(record);
		}
        System.out.println("\n **** \n");
		producer.close();
	}

	@Override
	public void run(){
		
		
				
		Map<String,Integer> topicCountMap = new HashMap<String,Integer>();
		topicCountMap.put(topicName, new Integer(1));
		Map<String,List<KafkaStream<byte[], byte[]>>> consuerMap = consumer.createMessageStreams(topicCountMap);
		KafkaStream<byte[], byte[]> stream = consuerMap.get(topicName).get(0);
		ConsumerIterator<byte[], byte[]> it = stream.iterator();
		send(it);

	}

}
//////////////////////////////////////////////////////////////

