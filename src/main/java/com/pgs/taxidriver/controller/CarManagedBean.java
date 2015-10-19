package com.pgs.taxidriver.controller;

import com.pgs.taxidriver.model.Car;
import com.pgs.taxidriver.model.Company;
import com.pgs.taxidriver.model.User;
import com.pgs.taxidriver.service.CarService;
import com.pgs.taxidriver.service.UserService;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by akrawczyk on 2015-09-07.
 */
@Component("carMB")
@Scope(scopeName = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CarManagedBean {

    @Autowired
    private CarService carService;

    @Autowired
    private NavigationRule navigationRule;

    @Autowired
    UserService userService;

    @Autowired
    private CompanyManagedBean companyMB;

    List<Car> carList;

    private List<Car> filteredCars;

    List<Company> companyList;

    private Car car;

    List<User> employeeList;

    private User selectedUser;

    private BarChartModel categoryChart;

    private Car selectedCar;

    private Company selectedCompany;

    public List<User> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<User> employeeList) {
        this.employeeList = employeeList;
    }

    public User getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
    }

    public void setCarList(List<Car> carList) {
        this.carList = carList;
    }

    public List<Car> getCarList() {
        return carList;
    }

    public List<Company> getCompanyList() {
        return this.companyList;
    }

    public void setCompanyList(List<Company> companyList) {
        this.companyList = companyList;
    }

    public Company getSelectedCompany() {
        return selectedCompany;
    }

    public void setSelectedCompany(Company selectedCompany) {
        this.selectedCompany = selectedCompany;
    }

    public Car getSelectedCar() {
        return selectedCar;
    }

    public void setSelectedCar(Car selectedCar) {
        this.selectedCar = selectedCar;
        createMultiAxisModel();
    }

    public Car getCar() {
        return this.car;
    }

    public void setCar(Car someCar) {
        this.car = someCar;
    }

    @PostConstruct
    void init() {
        car = new Car();
    }

    public List<Car> getCarsForUserCompanies() {
        try {
            List<Car> cars = carService.getCarsForAllUserCompanies(navigationRule.loggedUser());
            return cars;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void onStartup() {
        this.carList = getCarsForUserCompanies();
    }

    public void addCar() {
        User companyOwner = selectedUser;
        car.setDriver(companyOwner);
        System.out.println(selectedCompany.getName());
        car.setCompany(selectedCompany);
        car.setStatus(false);
        car.setLatitude(0.0);
        car.setLongitude(0.0);
        car.setActive(true);
        carService.updateCar(car);
        reset();
    }

    public void deleteCar() {
        selectedCar.setActive(false);
        carService.updateCar(selectedCar);
        selectedCar = null;
    }

    public void updateCar() {
        carService.updateCar(selectedCar);
        this.selectedCar = null;

    }

    public List<User> getEmployeeListForSelectedCompany() {
        if (selectedCompany != null)
            this.employeeList = userService.getEmployeesByCompany(selectedCompany.getId());
        return employeeList;
    }

    public void reset() {
        car.setBrandModel(null);
        car.setCapacity(null);
        car.setNumberOfSeats(null);
        car.setPlateNumber(null);
        car.setYearOfProd(null);
        car.setDriver(null);
        car.setCompany(null);
        /*
         * RequestContext context = RequestContext.class; context.execute("document.getElementById("myForm").reset()");
         * <---- maybe try this way?
         */
        // RequestContext.getCurrentInstance().reset("addCarForm");
        selectedCar = null;
        selectedCompany = null;
        selectedUser = null;
    }

    public BarChartModel getMultiAxisModel() {
        return categoryChart;
    }

    /**
     * Incomes are in cells with numbers 0, 2, 4, 6
     * Distance are in cells with numbers 1, 3, 5, 7
     * First two numbers indicates for year stats, next two for month stats, next two for last 7 days stats and last two indicates for day stats
     * Last one store information about maximum value to set proper diagram height
     * Get statistic for selected cab
     */
    private void createMultiAxisModel() {
        categoryChart = new BarChartModel();

        ChartSeries incomes = new ChartSeries();
        incomes.setLabel("Income");

        ChartSeries distance = new ChartSeries();
        distance.setLabel("Distance [km]");

        // get data from database
        // 1. check if selected car exist in database
        if (this.selectedCar == null) {
            return;
        }
        car = carService.getCarById(this.selectedCar.getId());
        if (car == null) {
            return;
        }
        // 2. if selected car exist in database, then get its stats
        List<Double> list = this.carService.getCarStats(car);

        categoryChart.setTitle("Cab Statistics for " + selectedCar.getPlateNumber());
        categoryChart.setLegendPosition("ne");
        categoryChart.setAnimate(true);
        Axis yAxis = categoryChart.getAxis(AxisType.Y);
        yAxis.setMin(0);
        yAxis.setMax(list.get(8) + 0.1 * list.get(8));

        incomes.set("Year", list.get(0));
        incomes.set("Month", list.get(2));
        incomes.set("Week", list.get(4));
        incomes.set("Day", list.get(6));

        distance.set("Year", list.get(1));
        distance.set("Month", list.get(3));
        distance.set("Week", list.get(5));
        distance.set("Day", list.get(7));

        categoryChart.addSeries(incomes);
        categoryChart.addSeries(distance);
    }

    // @Override
    public List<Company> getCompanyListByLoggedUser() {
        this.companyList = companyMB.getCompaniesByLoggedUser();
        return companyList;
    }

}
