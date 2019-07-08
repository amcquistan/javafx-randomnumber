package com.thecodinginterface.randomnumber.repository;

import java.util.List;

import com.thecodinginterface.randomnumber.models.RandomNumber;

public interface RandomNumberDAO {

    boolean save(RandomNumber number);

    void loadNumbers();

    List<RandomNumber> getNumbers();
}
