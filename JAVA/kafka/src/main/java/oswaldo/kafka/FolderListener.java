package oswaldo.kafka;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class FolderListener extends Thread {

    public CSVkafkaProducer csVkafkaProducer;
    public boolean breakLoop = false;

    public FolderListener(CSVkafkaProducer csVkafkaProducer){
        this.csVkafkaProducer = csVkafkaProducer;
    }

    @Override
    public void run() {
        while (!breakLoop){
            File folder = new File("/home/hadoopuser/mapr");
            File[] fileArray = folder.listFiles();
            if(fileArray != null){
                ArrayList<File> files = new ArrayList<>(Arrays.asList(fileArray));
                files = files.stream().filter(file -> file.getName()
                        .contains(".csv"))
                        .collect(Collectors.toCollection(ArrayList::new));

                files.forEach(this::addNewFilesToProducer);
            }
        }
    }

    public void addNewFilesToProducer(File file){
        if(!csVkafkaProducer.filesReviewed.contains(file.getName())){
            csVkafkaProducer.files.add(file);
            csVkafkaProducer.filesReviewed.add(file.getName());
        }
    }
}
