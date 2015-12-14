package com.pgs.taxidriver.service;

import com.pgs.taxidriver.model.Car;
import com.pgs.taxidriver.model.User;

import java.util.List;

/**
 * Created by akrawczyk on 2015-09-07.
 */
public interface CarService {

    List<Car> getAll();

    void addCar(Car car);

    void deleteCar(Car car);

    void updateCar(Car car);

    Car getCarById(long id);

    List<Double> getCarStats(Car car);

    /**
     * Get car from dataBase by plateNumber
     *
     * @param plateNumber
     * @return
     */
    Car getCarByPlateNumer(String plateNumber);

    List<Car> getCarsForAllUserCompanies(User loggedUser);

    Car getCarByUserId(Long id);
}
