package oswaldo.kafka;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;



public class CSVkafkaProducer {

    private static String KafkaBrokerEndpoint = "25.7.237.232:29093";
    private static String KafkaTopic = "proyecto";

    public ArrayList<String> filesReviewed = new ArrayList<>();
    public ArrayList<File> files = new ArrayList<>();

    private Producer<String, String> ProducerProperties(){
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaBrokerEndpoint);
        properties.put(ProducerConfig.CLIENT_ID_CONFIG, "KafkaCsvProducer");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());


        return new KafkaProducer<>(properties);
    }

    public static void main(String[] args) throws URISyntaxException {

        CSVkafkaProducer kafkaProducer = new CSVkafkaProducer();
        FolderListener folderListener = new FolderListener(kafkaProducer);
        folderListener.start();

        while (true){
            ArrayList<File> processedFiles = new ArrayList<>();
            ArrayList<File> filesToProcess = (ArrayList<File>) kafkaProducer.files.clone();
            //Captura los archivos que ha encontrado hasta el momento
            // y trabaja solo con esos para no tener problemas si entran nuevos

            for (File file : filesToProcess) {
                kafkaProducer.PublishMessages(file);
                processedFiles.add(file);
                System.out.println("\n\n *********************** \n Processed file: "+file.getName() + "\n");
            }

            kafkaProducer.files.removeAll(processedFiles); //Removes processed files from queue

        }

    }



    @SuppressWarnings("resource")
    private void PublishMessages(File file) throws URISyntaxException{

        final Producer<String, String> csvProducer = ProducerProperties();
        System.out.println("\n \n Empezando a leer " + file.getName() + "\n");
        try{
            //URI uri = getClass().getClassLoader().getResource().toURI();
            Stream<String> FileStream = Files.lines(Paths.get(file.getPath()));

            final AtomicInteger count = new AtomicInteger();
            
            FileStream.forEach(line -> {
                
     
                final ProducerRecord<String, String> csvRecord = new ProducerRecord<String, String>(
                        KafkaTopic, UUID.randomUUID().toString(), line);

                csvProducer.send(csvRecord, (metadata, exception) -> {
                    if(metadata != null){
                    	System.out.print("| Trabajando con en la linea #" + count.incrementAndGet() + "        |\r");
                    	
                    }
                    else{
                        System.out.println("Error Sending Csv Record -> "+ csvRecord.value());
                    }
                });
                ;
            });

        } catch (IOException | NullPointerException e) {
            System.out.println("********** OH NO ***********");
            e.printStackTrace();
        }
    }


}