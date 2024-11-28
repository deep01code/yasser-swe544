package org.ksu.swe544.services;

import org.ksu.swe544.entities.CarCounter;
import org.ksu.swe544.entities.CarCounterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Lazy;

@Service
public class CarCounterService {

    @Autowired
    private CarCounterRepository carCounterRepository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    @Lazy
    private CarCounterService self; // Inject the self-proxy


    @Value("${NEXT_NODE_Name}") // Inject the NEXT_NODE URL
    private String nextNodeName;

    @Value("${NEXT_NODE_URL}") // Inject the NEXT_NODE URL
    private String nextNodeURL;

    @Value("${CURRENT_NODE_Name}") // Inject the NEXT_NODE URL
    private String currentNodeName;

    @Value("${CURRENT_NODE_URL}") // Inject the NEXT_NODE URL
    private String currentNodeURL;






    @Transactional
    public synchronized void notifyCarDeparture(){
        //decrementCounter in DB.
        self.decrementCounter();

        //based on successful db transaction

        //2- send kafka to DOOR1_CROSS_OUT

        //3- notify nextdoor
        TransactionSynchronizationManager.registerSynchronization(new org.springframework.transaction.support.TransactionSynchronizationAdapter() {
            @Override
            public void afterCommit() {
                // Logic to execute after the transaction successfully commits
                System.out.println("Transaction committed. Executing post-commit logic.");
                postNotifyCarDepartureLogic();
            }
        });
    }

    @Transactional
    public synchronized void notifyCarArrival(){
        self.incrementCounter();
        TransactionSynchronizationManager.registerSynchronization(new org.springframework.transaction.support.TransactionSynchronizationAdapter() {
            @Override
            public void afterCommit() {
                // Logic to execute after the transaction successfully commits
                System.out.println("Transaction committed. Executing post-commit logic.");
                postNotifyCarArrivalLogic();
            }
        });
    }




    public void postNotifyCarArrivalLogic(){
         RestTemplate restTemplate = new RestTemplate();
        // Your business logic here
         // Trigger the next node
        if (nextNodeURL != null && !nextNodeURL.isEmpty()) {
            System.out.println("Calling next node: " + nextNodeName);
            try {
                Map<String, String> requestBody = new HashMap<>();
                requestBody.put("message", currentNodeName);


                // Set headers if required
                HttpHeaders headers = new HttpHeaders();
                headers.set("Content-Type", "application/json");

                // Wrap the body and headers into an HttpEntity
                HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

                // Make the POST request
                ResponseEntity<String> response = restTemplate.exchange(
                        nextNodeURL+"/notifyCarArrivalMessage", HttpMethod.POST, requestEntity, String.class);

                  response.getBody();
             //   restTemplate.getForObject(nextNode + "/notifyCarArrivalMessage", String.class);
            } catch (Exception e) {
                System.err.println("Failed to call next node: " + e.getMessage());
            }
        }
    }
    public void postNotifyCarDepartureLogic(){
        RestTemplate restTemplate = new RestTemplate();
        // Your business logic here
        // Trigger the next node
        if (nextNodeURL != null && !nextNodeURL.isEmpty()) {
            System.out.println("Calling next node: " + nextNodeName);
            try {
                Map<String, String> requestBody = new HashMap<>();
                requestBody.put("message", currentNodeName);


                // Set headers if required
                HttpHeaders headers = new HttpHeaders();
                headers.set("Content-Type", "application/json");

                // Wrap the body and headers into an HttpEntity
                HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

                // Make the POST request
                ResponseEntity<String> response = restTemplate.exchange(
                        nextNodeURL+"/notifyCarDepartureMessage", HttpMethod.POST, requestEntity, String.class);

                response.getBody();
                //   restTemplate.getForObject(nextNode + "/notifyCarArrivalMessage", String.class);
            } catch (Exception e) {
                System.err.println("Failed to call next node: " + e.getMessage());
            }
        }
    }



    @Transactional
    public synchronized void incrementCounter() {
        CarCounter carCounter = entityManager.find(CarCounter.class, 1L, LockModeType.PESSIMISTIC_WRITE);
        if (carCounter.getCarCount() < 100) { // Ensure value doesn't go below 0
            carCounter.setCarCount(carCounter.getCarCount() + 1);
            entityManager.persist(carCounter);
        } else {
            System.out.println("Car count is already at 100. Cannot increment further.");
        }
    }

    @Transactional
    public synchronized void decrementCounter() {
        CarCounter carCounter = entityManager.find(CarCounter.class, 1L, LockModeType.PESSIMISTIC_WRITE);
        if (carCounter.getCarCount() > 0) { // Ensure value doesn't go below 0
            carCounter.setCarCount(carCounter.getCarCount() - 1);
            entityManager.persist(carCounter);
        } else {
            System.out.println("Car count is already at 0. Cannot decrement further.");
        }
    }
}

/*
*
java -jar swe544-1.0.0.jar --CURRENT_NODE_Name=Door1 --CURRENT_NODE_URL=http://localhost:8081 --NEXT_NODE_Name=Door2 --NEXT_NODE_URL=http://localhost:8082 --server.port=8081
java -jar swe544-1.0.0.jar --CURRENT_NODE_Name=Door2 --CURRENT_NODE_URL=http://localhost:8082 --NEXT_NODE_Name=Door3 --NEXT_NODE_URL=http://localhost:8083 --server.port=8082
java -jar swe544-1.0.0.jar --CURRENT_NODE_Name=Door3 --CURRENT_NODE_URL=http://localhost:8083 --NEXT_NODE_Name=Door4 --NEXT_NODE_URL=http://localhost:8084 --server.port=8083
java -jar swe544-1.0.0.jar --CURRENT_NODE_Name=Door4 --CURRENT_NODE_URL=http://localhost:8084 --NEXT_NODE_Name=Door1 --NEXT_NODE_URL=http://localhost:8081 --server.port=8084
*/

// ps -ef | grep swe544-1.0.0.jar | grep -v grep | awk '{print $2}' | xargs kill -9