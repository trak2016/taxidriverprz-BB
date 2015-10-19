package com.pgs.taxidriver.controller;

import com.pgs.taxidriver.dao.CarDAO;
import com.pgs.taxidriver.init.WebApplicationConfig;
import com.pgs.taxidriver.model.Car;
import com.pgs.taxidriver.model.Company;
import com.pgs.taxidriver.model.User;
import com.pgs.taxidriver.model.UserRole;
import com.pgs.taxidriver.service.CarService;
import com.pgs.taxidriver.service.CarServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import static junit.framework.TestCase.assertSame;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by akrawczyk on 2015-09-14.
 */

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = WebApplicationConfig.class)
@WebAppConfiguration
@Transactional
public class CarDAOMockitoTest {

    @Mock
    private CarDAO carDAO;

    @InjectMocks
    private CarService carService = new CarServiceImpl();

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddCar()
    {
        //given
        final Car car = createCar();
        //then
        carService.addCar(car);
        //result
        verify(carDAO, Mockito.times(1)).create(car);
    }

    private static Car createCar()
    {
        Car car = new Car();

        car.setPlateNumber("RZE00001");
        car.setBrandModel("Mercedes Benz");
        car.setYearOfProd(new Date());
        car.setStatus(false);
        car.setCapacity("500 kg");
        car.setNumberOfSeats((short) 6);
        car.setCompany(new Company());
        car.setDriver(new User());
        car.setLatitude(0.0);
        car.setLongitude(0.0);

        return car;
    }

    @Test
    public void testDeleteUser() throws Exception {

        when(carDAO.getById(Car.class, 0l)).thenReturn(new Car());

        carService.deleteCar(anyObject());

        verify(carDAO, Mockito.times(1)).delete(anyObject());
    }

    @Test
    public void testFindUserById(){
        //given
        final Long id = 123l;
        Mockito.doReturn(new Car()).when(carDAO).getById(Car.class, id);
        //when
        final Car result = carService.getCarById(id);
        //then
        assertNotNull(result);
    }

    @Test
    public void testGetAll() {
        //given
        List<Car> expectedCarList = new ArrayList<Car>();
        expectedCarList.add(new Car("RZE00001", "Ford Kuga", new Date(), false, "1450 kg", (short)4, new Company(),
                new User(), 0.0, 0.0));
        expectedCarList.add(new Car("RZE00002", "Ford Mondeo", new Date(), false, "1750 kg", (short)4, new Company(),
                new User(), 0.0, 0.0));
        expectedCarList.add(new Car("RZE00001", "Ford Fiesta", new Date(), true, "1350 kg", (short) 4, new Company(),
                new User(), 0.0, 0.0));
        //when
        when(carService.getAll()).thenReturn(expectedCarList);
        List<Car> actualCarList = carDAO.findAll();
        //then
        assertNotNull(actualCarList);
        assertSame(expectedCarList, actualCarList);
    }

    @Test
    public void testUpdateCarData()
    {
        Car car = createCar();
        Car updatedCarData =  new Car(car.getId(), "RZE12345", "Ford Mondeo", new Date(), false, "500 kg", (short)4, new Company(), new User(),
                0.0, 0.0);
        carService.updateCar(updatedCarData);
        Mockito.verify(carDAO, Mockito.times(1)).update(updatedCarData);
        ArgumentCaptor<Car> carArgumentCaptor = ArgumentCaptor.forClass(Car.class);
        verify( carDAO ).update(carArgumentCaptor.capture());
        Car updatedCar = carArgumentCaptor.getValue();
        assertEquals(updatedCarData, updatedCar);
    }

    @Test
    public void testGetCarsForAllUserCompanies(){
        //given
        final User user = new User("login", "pasword", new ArrayList<UserRole>());
        Mockito.doReturn(new ArrayList<Car>()).when(carDAO).getCarsForAllUserCompanies(user);
        //when
        final List<Car> result = carService.getCarsForAllUserCompanies(user);
        //then
        assertNotNull(result);
    }

    @Test
    public void testGetCarByPlateNumber(){
        //given
        final String plateNb = "RZE12345";
        Mockito.doReturn(new Car()).when(carDAO).getCarByPlateNumer(plateNb);
        //when
        final Car result = carService.getCarByPlateNumer(plateNb);
        //then
        assertNotNull(result);
    }

}
