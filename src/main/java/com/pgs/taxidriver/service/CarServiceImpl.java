package com.pgs.taxidriver.service;

import com.pgs.taxidriver.dao.CarDAO;
import com.pgs.taxidriver.model.Car;
import com.pgs.taxidriver.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by akrawczyk on 2015-09-07.
 */
@Service
public class CarServiceImpl implements CarService {

    @Autowired
    CarDAO carDAO;

    @Override
    @Transactional
    public List<Car> getAll() {
        return carDAO.findAll();
    }

    @Override
    @Transactional
    public void addCar(Car car) {
        carDAO.addCar(car);
    }

    @Override
    @Transactional
    public void deleteCar(Car car) {
        carDAO.delete(car);
    }

    @Override
    @Transactional
    public void updateCar(Car car) {
        carDAO.update(car);
    }

    @Override
    @Transactional
    public Car getCarById(long id) {
        return carDAO.getById(Car.class, id);
    }

    @Override
    @Transactional
    public List<Double> getCarStats(Car car) {
        return carDAO.getCarStat(car.getDriver().getId());
    }

    @Override
    public List<Car> getCarsForAllUserCompanies(User loggedUser) {
        return carDAO.getCarsForAllUserCompanies(loggedUser);
    }

    /**
     * Get car from dataBase by plateNumber
     *
     * @param plateNumber
     * @return
     */
    @Override
    @Transactional
    public Car getCarByPlateNumer(String plateNumber) {
        return carDAO.getCarByPlateNumer(plateNumber);
    }

    @Transactional
    @Override
    public Car getCarByUserId(Long id){
        return carDAO.getCarByUserId(id);
    }
}
