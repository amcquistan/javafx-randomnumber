package com.thecodinginterface.randomnumber.repository;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import com.thecodinginterface.randomnumber.models.RandomNumber;

public class LocalRandomNumberDAO implements RandomNumberDAO {

    private List<RandomNumber> numbers = new ArrayList<>();

    @Override
    public boolean save(RandomNumber number) {
        return numbers.add(number);
    }

    @Override
    public void loadNumbers() {

    }

    @Override
    public List<RandomNumber> getNumbers() {
        return Collections.unmodifiableList(numbers);
    }
}
