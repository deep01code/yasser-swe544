package org.ksu.swe544.controller;

import org.ksu.swe544.unused.KafkaUtility;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@RestController
public class NodeController {

    @Value("${NEXT_NODE_Name}") // Inject the NEXT_NODE URL
    private String nextNodeName;

    @Value("${NEXT_NODE_URL}") // Inject the NEXT_NODE URL
    private String nextNodeURL;

    @Value("${CURRENT_NODE_Name}") // Inject the NEXT_NODE URL
    private String currentNodeName;

    @Value("${CURRENT_NODE_URL}") // Inject the NEXT_NODE URL
    private String currentNodeURL;



/*    @GetMapping("/process")
    public String process() {
        private final RestTemplate restTemplate = new RestTemplate();
        // Your business logic here
        System.out.println("Processing at current node");
        // Trigger the next node
        if (nextNode != null && !nextNode.isEmpty()) {
            System.out.println("Calling next node: " + nextNode);
            try {
                restTemplate.getForObject(nextNode + "/process", String.class);
            } catch (Exception e) {
                System.err.println("Failed to call next node: " + e.getMessage());
            }
        }
        return "Processed and called next node";
    }*/

    @PostMapping("/notifyCarDepartureMessage")
    public String notifyCarDepartureMessage(@RequestBody Map<String, String> requestBody) {
        String message = requestBody.get("message");

        System.out.println(currentNodeName+": notifyCarDepartureMessage from : "+message);

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String timestamp = now.format(formatter);

        String messageContent=timestamp+" "+currentNodeName+": notifyCarDepartureMessage from : "+message;

        KafkaUtility kafkaUtility = new KafkaUtility();
        kafkaUtility.sendEvent(currentNodeName+"_NOTIFICATION", messageContent,messageContent);

        return currentNodeName+": notifyCarDepartureMessage from : "+message;
    }

    @PostMapping("/notifyCarArrivalMessage")
    public String notifyCarArrivalNotification(@RequestBody Map<String, String> requestBody) {
        String message = requestBody.get("message");


        System.out.println(currentNodeName+": notifyCarArrivalNotification from  : "+message);

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String timestamp = now.format(formatter);

        String messageContent=timestamp+" "+currentNodeName+": notifyCarArrivalNotification from  : "+message;

        KafkaUtility kafkaUtility = new KafkaUtility();
        kafkaUtility.sendEvent(currentNodeName+"_NOTIFICATION", messageContent,messageContent);


        return currentNodeName+": notifyCarCarArrivalNotification from : "+message;
    }


}