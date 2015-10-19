package com.pgs.taxidriver.utils;

import com.pgs.taxidriver.model.Car;

import java.util.Comparator;

/**
 * Created by mlasota on 2015-09-16.
 */
public class CustomComparator implements Comparator<Car> {
    @Override
    public int compare(Car car1, Car car2) {
        return car1.getTime().compareTo(car2.getTime());
    }
}
