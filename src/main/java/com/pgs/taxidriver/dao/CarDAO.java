package com.pgs.taxidriver.dao;

import com.pgs.taxidriver.model.Car;
import com.pgs.taxidriver.model.User;

import java.util.List;

/**
 * Created by akrawczyk on 2015-09-07.
 */
public interface CarDAO extends GenericDAO<Car> {

    /**
     * Get car stats (distance and incomes) within year, month, week or day.
     *
     * @param userId
     * @return
     */
    public List<Double> getCarStat(long userId);

    /**
     * Get car from dataBase by plateNumber
     *
     * @param plateNumber
     * @return
     */
    public Car getCarByPlateNumer(String plateNumber);

    public List<Car> getCarsForAllUserCompanies(User user);

    public void addCar(Car car);

    Car getCarByUserId(Long id);
}
