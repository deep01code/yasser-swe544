package org.ksu.swe544.entities;

import javax.persistence.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
@Table(name = "singleton_value")
@EntityListeners(CarCounterActionListener.class)
public class CarCounter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Dummy ID for JPA
    private Long id;

    @Min(0) // Minimum value constraint
    @Max(100) // Maximum value constraint
    private int carCount;



    public Long getId() {
        return id;
    }

    public int getCarCount() {
        return carCount;
    }

    public void setCarCount(int carCount) {
        this.carCount=carCount;
    }

    public void decrement() {
        if (this.carCount > 0) {
            this.carCount--;
        }
    }
}