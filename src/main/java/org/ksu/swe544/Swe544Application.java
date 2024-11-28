package org.ksu.swe544;

//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.apache.zookeeper.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.ksu.swe544.services.CarCounterService;
import org.ksu.swe544.unused.ZookeeperUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Properties;

import static org.ksu.swe544.unused.kafkaUtility.consumeEvent;
import static org.ksu.swe544.unused.kafkaUtility.sendEvent;

/*
-DADDRESS=localhost -DBOOTSTRAP_SERVERS=localhost:9092 -DDOOR_CLUSTER_CARS_COUNTER_PATH=door1counter -DDOOR_CLUSTER_NUMBER=door1 -DINSTANCE_NUMBER=1 -DNEXT_DOOR_CLUSTER_NUMBER=door2

env values are:

DOOR_CLUSTER_NUMBER, door1

INSTANCE_NUMBER

NEXT_DOOR_CLUSTER_NUMBER
* */

/*
* kafka topics
* door1
* door2
* door3
* door4
*
* door1Count
* door2Count
* door3Count
* door4Count
*
* */

/*
docker run command

docker build -t swe544app .

docker run -p 80:80 -e DOOR_CLUSTER_NUMBER=/door1 -e INSTANCE_NUMBER=1 -e NEXT_DOOR_CLUSTER_NUMBER=/door2 swe544app

docker run --network=host -e DOOR_CLUSTER_NUMBER=/door1 -e INSTANCE_NUMBER=1 -e NEXT_DOOR_CLUSTER_NUMBER=/door2 swe544app
docker run --network=host -e DOOR_CLUSTER_NUMBER="door1" -e INSTANCE_NUMBER="instance-1" -e NEXT_DOOR_CLUSTER_NUMBER="door2" swe544app


* */

@SpringBootApplication
public class Swe544Application  {




    static ZookeeperUtility masterNode;
    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {

        System.out.println("running");

        SpringApplication.run(Swe544Application.class, args);



         runCarScheduler();


    }


    public static void runCarScheduler(){
        CarCounterService carCounterService = SpringContextHolder.getContext().getBean(CarCounterService.class);

        final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try{

                    System.out.println("Enter Scadualer***************************************************************************");





                    if (new Random().nextInt(2) == 0) {// Branch 1: 50% chance
                        //MAX_CAR_COUNTER
                        System.out.println("notifyCarDeparture executed.");
                        carCounterService.notifyCarDeparture();

                    } else {// Branch 2: 50% chance
                        System.out.println("notifyCarArrival executed.");
                        carCounterService.notifyCarArrival();
                    }




                }catch (Exception e){
                             e.printStackTrace();
                }

                try {
                    int random=new Random().nextInt(3)*1000;
                    System.out.println("Sleep for "+random);
                    Thread.sleep(random);

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, 0, 2, TimeUnit.SECONDS);
    }


    public static void testKafka(){
        // Kafka broker address
        String bootstrapServers = "localhost:9092";
        // Topic name
        String topicName = "test-topic";

        // Kafka producer configuration
        Properties properties = new Properties();
        properties.put("bootstrap.servers", bootstrapServers);
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        // Create Kafka producer
        Producer<String, String> producer = new KafkaProducer<>(properties);

        try {
            // Produce message
            String key = "key1";
            String value = "hello Kafka";
            producer.send(new ProducerRecord<>(topicName, key, value));

            System.out.println("Message sent successfully to topic " + topicName);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close producer
            producer.close();
        }
    }

 /*   public static void notifyCarDeparture(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String timestamp = now.format(formatter);
        String key="car-departure"
                +"-"+System.getProperty(LookupValues.DOOR_CLUSTER_NUMBER)
                +"-INSTANCE_NUMBER"+System.getProperty(LookupValues.INSTANCE_NUMBER)+" at:"+ timestamp;

        sendEvent(System.getProperty(LookupValues.NEXT_DOOR_CLUSTER_NUMBER),key,key);
    }

    public static void notifyCarArrival(){
        consumeEvent(System.getProperty(LookupValues.DOOR_CLUSTER_NUMBER));
    }*/
}