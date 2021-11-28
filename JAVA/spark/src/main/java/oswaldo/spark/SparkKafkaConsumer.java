package oswaldo.spark;

import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


import com.opencsv.CSVReader;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaPairInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka.KafkaUtils;

import kafka.serializer.StringDecoder;

import java.util.Properties;



import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.StringSerializer;

public class SparkKafkaConsumer {

	static Properties props = new Properties(); // para el producer
	
	public static void send(List<String> messages){	
		props.setProperty("bootstrap.servers", "localhost:9092");
		props.setProperty("kafka.topic.name", "kafka"); //topic al que le vamos a escribir
		KafkaProducer<String, byte[]> producer = new KafkaProducer<String, byte[]>(props,new StringSerializer(), new ByteArraySerializer());
		
		System.out.println("***************************************");
		System.out.println("ENVIANDO MENSAJES A KAFKA...");
		System.out.println("***************************************");

		for(String s : messages) {
			byte[] payload = (s).getBytes();
			ProducerRecord<String, byte[]> record = new ProducerRecord<String, byte[]>(props.getProperty("kafka.topic.name"), payload);
			producer.send(record);
		}
		producer.close();
	}
	
	
	public static void main(String[] args) throws IOException {
		
		analyzer analyzer = new analyzer();  //creamos un objeto del analizador de letras
		
		
		
		
		System.out.println("Spark Streaming started now .....");

		SparkConf conf = new SparkConf().setAppName("kafka-sandbox").setMaster("local[*]");
		JavaSparkContext sc = new JavaSparkContext(conf);
		// batchDuration - The time interval at which streaming data will be divided into batches
		JavaStreamingContext ssc = new JavaStreamingContext(sc, new Duration(20000));

		Map<String, String> kafkaParams = new HashMap<>();
		kafkaParams.put("metadata.broker.list", "localhost:9092");
		Set<String> topics = Collections.singleton("proyecto");

		JavaPairInputDStream<String, String> directKafkaStream = KafkaUtils.createDirectStream(ssc, String.class,
				String.class, StringDecoder.class, StringDecoder.class, kafkaParams, topics);

		  List<String> allRecord = new ArrayList<String>();
		  final String COMMA = ",";
		  final String QUOTE = "\"";
		  
		  directKafkaStream.foreachRDD(rdd -> {
			  
		  System.out.println("New data arrived  " + rdd.partitions().size() +" Partitions and " + rdd.count() + " Records");
			  if(rdd.count() > 0) {
				rdd.collect().forEach(rawRecord -> {
					  
					  System.out.println(rawRecord);
					  System.out.println("***************************************");
					  System.out.println(rawRecord._2);
					  String record = rawRecord._2();
					  
					  //StringTokenizer st = new StringTokenizer(record,",");
					  
					  CSVReader reader = new CSVReader(new StringReader(record));
					  String [] tokens;
					  
					  StringBuilder sb = new StringBuilder(); 
					  try {
						while((tokens = reader.readNext()) != null) {
							String id = tokens[0]; // id de la cancion
							String artist = tokens[1]; // astista de la cancion
							String trackName = tokens[2]; //nombre de la cancion
							String releaseDate = tokens[3]; //  fecha de estreno de la cancion
							String genre = tokens[4]; // genero musical de la cancion
							String lyrics = tokens[5]; // letra de la cancion
							String len = tokens[6]; // largo en segundos de la cancion
						
						    // hacemos el analisis de los pts y del tema
							float puntos = analyzer.analize(lyrics);
							String theme = analyzer.theme(lyrics);
							String pts = String.valueOf(puntos);
							
							sb.append(id).append(COMMA).append(QUOTE).append(artist).append(QUOTE).append(COMMA).append(QUOTE).append(trackName).append(QUOTE).append(COMMA).append(releaseDate).append(COMMA).append(genre).append(COMMA).append(QUOTE).append(lyrics).append(QUOTE).append(COMMA).append(len).append(COMMA).append(pts).append(COMMA).append(theme);
							allRecord.add(sb.toString());
						  }
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					  
				  });
				System.out.println("All records OUTER MOST :"+allRecord.size()); 
				FileWriter writer = new FileWriter("Master_dataset.csv", true);
				send(allRecord);
				for(String s : allRecord) {
					writer.write(s);
					writer.write("\n");
				}
				System.out.println("Master dataset has been created : ");
				
				writer.close();
			  }
		  });
		 
		ssc.start();
		ssc.awaitTermination();
	}
	

}