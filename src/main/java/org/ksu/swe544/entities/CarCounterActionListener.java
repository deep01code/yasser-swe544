package org.ksu.swe544.entities;


import javax.persistence.PostPersist;

public class CarCounterActionListener {

     @PostPersist
     public void methodAfterTransaction(final CarCounter reference){

     }
 }