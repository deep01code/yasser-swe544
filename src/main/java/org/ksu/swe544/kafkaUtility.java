package org.ksu.swe544;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.zookeeper.KeeperException;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

import static org.ksu.swe544.ZookeeperUtility.getValueFromZooPath;

public class kafkaUtility {

    //private static final String BOOTSTRAP_SERVERS = "localhost:9092"; // Replace with your Kafka broker address

    // Producer properties
    private static Properties getProducerProperties() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, System.getProperty("BOOTSTRAP_SERVERS"));
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        return props;
    }

    // Consumer properties
    private static Properties getConsumerProperties(String groupId) {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, System.getProperty("BOOTSTRAP_SERVERS"));
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"); // Consume from the beginning if no offset is found
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false"); // Disable auto offset commit
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "1"); // Fetch one record at a time
        return props;
    }

    // Method to send a message to a Kafka topic
    public static void sendEvent(String topic, String key, String value)  {
        KafkaProducer<String, String> producer = new KafkaProducer<>(getProducerProperties());
        try {

            String carCounter=getValueFromZooPath("/"+System.getProperty(LookupValues.DOOR_CLUSTER_CARS_COUNTER_PATH));
            int carCounterValue=Integer.parseInt(carCounter);
            if(carCounterValue>0){
                producer.send(new ProducerRecord<>(topic, key, value), (metadata, exception) -> {
                    if (exception == null) {
                        System.out.println("Sent***************************************************************************");
                        System.out.println("Sent***************************************************************************");

                        System.out.printf("Message sent to topic %s [partition: %d, offset: %d]%n",
                                metadata.topic(), metadata.partition(), metadata.offset());
                        ZookeeperUtility.decrementCarCounter();
                        System.out.println("-----");
                    } else {
                        System.err.println("Error sending message: " + exception.getMessage());
                    }
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            producer.close();
        }
    }

    // Method to consume messages from a Kafka topic (one by one with manual offset commit)
    public static void consumeEvent(String topic)  {

        try {
            String carCounter=getValueFromZooPath("/"+System.getProperty(LookupValues.DOOR_CLUSTER_CARS_COUNTER_PATH));
            int carCounterValue=Integer.parseInt(carCounter);

            System.out.println(carCounterValue);
            //max
            if(carCounterValue<51){
                KafkaConsumer<String, String> consumer = new KafkaConsumer<>(getConsumerProperties("1"));
                consumer.subscribe(Collections.singletonList(topic));

                try {

                    // Poll for one record at a time
                    ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(10));

                    for (ConsumerRecord<String, String> record : records) {
                        System.out.printf("Processing message: topic = %s, partition = %d, offset = %d, key = %s, value = %s%n",
                                record.topic(), record.partition(), record.offset(), record.key(), record.value());

                        // Custom processing logic
                        boolean success = processMessage(record);

                        // Commit offset only if processing succeeds
                        if (success) {
                            TopicPartition partition = new TopicPartition(record.topic(), record.partition());
                            OffsetAndMetadata offsetMetadata = new OffsetAndMetadata(record.offset() + 1);

                            consumer.commitSync(Collections.singletonMap(partition, offsetMetadata));

                            System.out.printf("Successfully processed and committed offset %d for topic %s%n",
                                    record.offset(), record.topic());
                            ZookeeperUtility.incrementCarCounter();
                        } else {
                            System.err.printf("Failed to process message at offset %d%n", record.offset());
                            // Optionally, implement retry logic here
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    consumer.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }


    }

    // Example method to process each message
    private static boolean processMessage(ConsumerRecord<String, String> record) {
        // Write your message processing logic here
        // Return true if processing succeeds, false otherwise
        try {
            System.out.printf("Processing value: %s%n", record.value());
            // Simulate some processing logic
            if (record.value().contains("error")) {
                throw new RuntimeException("Simulated processing error");
            }
            return true;
        } catch (Exception e) {
            System.err.println("Processing failed: " + e.getMessage());
            return false;
        }
    }



}
