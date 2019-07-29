package com.thecodinginterface.randomnumber.viewmodels;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.thecodinginterface.randomnumber.models.RandomNumber;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

public class RandomNumberViewModel {
    private static final DateTimeFormatter dateFmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final ObjectProperty<LocalDate> createdAt = new SimpleObjectProperty<>();
    private final IntegerProperty number = new SimpleIntegerProperty();
    private final IntegerProperty lowerBounds = new SimpleIntegerProperty();
    private final IntegerProperty upperBounds = new SimpleIntegerProperty();
    
    public RandomNumberViewModel() {}
    
    public RandomNumberViewModel(RandomNumber randomNumber) {
        setCreatedAt(randomNumber.getCreatedAt());
        setNumber(randomNumber.getNumber());
        setLowerBounds(randomNumber.getLowerBounds());
        setUpperBounds(randomNumber.getUpperBounds());
    }
    
    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt.set(createdAt);
    }
    
    public LocalDate getCreatedAt() {
        return createdAt.get();
    }
    
    public ObjectProperty<LocalDate> createdAtProperty() {
        return createdAt;
    }
    
    public void setNumber(int number) {
        this.number.set(number);
    }
    
    public int getNumber() {
        return number.get();
    }
    
    public IntegerProperty numberProperty() {
        return number;
    }
    
    public void setLowerBounds(int lowerBounds) {
        this.lowerBounds.set(lowerBounds);
    }
    
    public int getLowerBounds() {
        return lowerBounds.get();
    }
    
    public IntegerProperty lowerBoundsProperty() {
        return lowerBounds;
    }
    
    public void setUpperBounds(int upperBounds) {
        this.upperBounds.set(upperBounds);
    }
    
    public int getUpperBounds() {
        return upperBounds.get();
    }
    
    public IntegerProperty upperBoundsProperty() {
        return upperBounds;
    }
    
    public RandomNumber toRandomNumber() {
        return new RandomNumber(getCreatedAt(), getNumber(), getLowerBounds(), getUpperBounds());
    }
    
    public static String formatDate(LocalDate date) {
        return dateFmt.format(date);
    }
}
