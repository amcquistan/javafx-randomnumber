package com.thecodinginterface.randomnumber.models;

import java.time.LocalDate;

public class RandomNumber {
    private LocalDate createdAt;
    private int number;
    private int lowerBounds;
    private int upperBounds;

    public RandomNumber() {}

    public RandomNumber(LocalDate createdAt, int number, int lowerBounds, int upperBounds) {
        this.createdAt = createdAt;
        this.number = number;
        this.lowerBounds = lowerBounds;
        this.upperBounds = upperBounds;
    }
    
    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public void setLowerBounds(int lowerBounds) {
        this.lowerBounds = lowerBounds;
    }

    public int getLowerBounds() {
        return lowerBounds;
    }

    public void setUpperBounds(int upperBounds) {
        this.upperBounds = upperBounds;
    }

    public int getUpperBounds() {
        return upperBounds;
    }
}
