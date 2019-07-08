package com.thecodinginterface.randomnumber.models;

public class RandomNumber {
    private int number;
    private int lowerBounds;
    private int upperBounds;

    public RandomNumber() {}

    public RandomNumber(int number, int lowerBounds, int upperBounds) {
        this.number = number;
        this.lowerBounds = lowerBounds;
        this.upperBounds = upperBounds;
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
