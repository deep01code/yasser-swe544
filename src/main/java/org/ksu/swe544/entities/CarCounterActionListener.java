package org.ksu.swe544.entities;


import org.ksu.swe544.unused.kafkaUtility;

import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CarCounterActionListener {

    @PostUpdate
     public void methodAfterTransaction(final CarCounter reference){

         System.out.println("The DB Value is ---> :"+reference.getCarCount());

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String timestamp = now.format(formatter);
        String message=timestamp+" Car Count is : "+reference.getCarCount();
        kafkaUtility.sendEvent("cars_counter", message,message);
     }
 }