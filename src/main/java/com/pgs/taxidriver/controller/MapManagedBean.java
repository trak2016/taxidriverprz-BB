package com.pgs.taxidriver.controller;

/**
 * Created by mlasota on 2015-09-07.
 */

import com.pgs.taxidriver.model.Car;
import com.pgs.taxidriver.model.Company;
import com.pgs.taxidriver.model.Course;
import com.pgs.taxidriver.model.User;
import com.pgs.taxidriver.service.CarService;
import com.pgs.taxidriver.service.CompanyService;
import com.pgs.taxidriver.service.CourseService;
import com.pgs.taxidriver.service.UserService;
import com.pgs.taxidriver.utils.CustomComparator;
import com.pgs.taxidriver.utils.DistanceCalc;
import com.pgs.taxidriver.utils.XMLParser;
import org.primefaces.event.map.GeocodeEvent;
import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.map.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.xml.sax.SAXException;

import javax.annotation.PostConstruct;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.util.*;


@Component("mapView")
@Scope(scopeName = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MapManagedBean implements Serializable {

    private final static Logger logger = LoggerFactory.getLogger(MapManagedBean.class);

    @Autowired
    private DistanceCalc distanceCalc;

    @Autowired
    private CarService carService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private NavigationRule navigationRule;

    @Autowired
    private XMLParser xmlParser;

    @Autowired
    private UserService userService;

    private MapModel mapModel;
    private List<Car> cabsList = new ArrayList<>();
    private boolean searched;
    private String time;
    private LatLng destination;
    private Marker marker;
    private String centerMap;
    private LatLng origin;
    private String data;
    private Car selectedCar;
    private Course course;
    private String title;
    private List<Marker> freeCabsMarkerList = new ArrayList<>();
    private List<Car> freeCabsList = new ArrayList<>();
    private double distance;
    private User user;
    private Double dtime;
    private List<Car> sortedFreeCabsList = new ArrayList<>();


    @PostConstruct
    public void init() throws IOException, ParserConfigurationException, SAXException, URISyntaxException {
        mapModel = new DefaultMapModel();
        centerMap = "50.0397667,21.999881";
        searched = false;
        //mapModel = addMarkers();
        course = new Course();

    }

    public void reset() throws ParserConfigurationException, IOException, SAXException, URISyntaxException {
        mapModel = new DefaultMapModel();
        searched = false;
        mapModel = addMarkers();
        course = new Course();
    }

    public List<Car> getFreeCabsList() {
        return freeCabsList;
    }

    public MapModel addMarkers() throws URISyntaxException, ParserConfigurationException, SAXException, IOException {

        user = navigationRule.loggedUser();
        List<Company> companiesList = companyService.getCompaniesByLoggedUser(user.getLogin());

        for (Company company : companiesList) {
            if (company != null) {
                cabsList.addAll(userService.getCarsByCompany(company.getId()));
            } else {
                mapModel.getMarkers().clear();
            }

        }

        freeCabsMarkerList.clear();
        freeCabsList.clear();

        if (cabsList != null) {
            for (Car car : cabsList) {
                origin = new LatLng(car.getLatitude(), car.getLongitude());
                if (car.getStatus()) {
                    mapModel.addOverlay(new Marker(origin, String.valueOf(car.getPlateNumber()), "Occupied",
                            "http://maps.google.com/mapfiles/ms/micons/red-dot.png"));
                } else {
                    if (searched) {
                        data = "Arrival time: " + xmlParser.travelTime(origin, destination);
                    } else
                        data = "Free";

                    marker =
                            new Marker(origin, String.valueOf(car.getPlateNumber()), data,
                                    "http://maps.google.com/mapfiles/ms/micons/green-dot.png");

                    mapModel.addOverlay(marker);
                    freeCabsMarkerList.add(marker);
                    freeCabsList.add(car);
                }
            }
        }
        return mapModel;
    }

    public void onGeocode(GeocodeEvent event) throws ParserConfigurationException, IOException, SAXException, URISyntaxException {
        List<GeocodeResult> results = event.getResults();

        mapModel.getCircles().clear();
        mapModel.getMarkers().clear();
        if (results != null && !results.isEmpty()) {
            LatLng center = results.get(0).getLatLng();
            centerMap = center.getLat() + ", " + center.getLng();

            for (int i = 0; i < results.size(); i++) {
                GeocodeResult result = results.get(i);
                destination = result.getLatLng();
                searched = true;


                Circle circle = new Circle(result.getLatLng(), 2000);
                circle.setStrokeColor("#d93c3c");
                circle.setFillColor("#d93c3c");
                circle.setFillOpacity(0.5);
                circle.setId("circle");

                List<String> carInArea = new ArrayList<>();


                for (Marker marker : freeCabsMarkerList) {
                    distance =
                            distanceCalc.distance(marker.getLatlng().getLat(), marker.getLatlng().getLng(), result.getLatLng()
                                    .getLat(), result.getLatLng().getLng());
                    if (distance < 2000.0) {
                        carInArea.add(marker.getTitle());
                    }
                }

                mapModel.addOverlay(new Marker(result.getLatLng(), result.getAddress(), "Phone number: 123 456 789", "http://maps.google.com/mapfiles/ms/micons/blue-dot.png"));
                mapModel.addOverlay(circle);
            }
        }
        updateCabs();
        addMarkers();
    }

    public void onMarkerSelect(OverlaySelectEvent event) {
        String plateNumber = "";
        marker = (Marker) event.getOverlay();
        plateNumber = marker.getTitle();
        selectedCar = carService.getCarByPlateNumer(plateNumber);

    }

    /**
     * Function for adding new course
     */
    public void addCourse() throws ParserConfigurationException, IOException, SAXException, URISyntaxException {
        course.setDistance((float) 250.0);
        course.setCost((float) 300);

        //Fields on the database must be changed to NULL expected


        course.setDate(Calendar.getInstance().getTime());
        course.setUser(selectedCar.getDriver());
        courseService.addCourse(course);

        selectedCar.setStatus(true);
        carService.updateCar(selectedCar);

        updateCabs();
        reset();
    }

    public void updateCabs() throws URISyntaxException, ParserConfigurationException, SAXException, IOException {

        for (Car car : freeCabsList) {
            origin = new LatLng(car.getLatitude(), car.getLongitude());

            String tempTime = xmlParser.travelTime(origin, destination);
            String[] splitTime = tempTime.split(" ");
            if (splitTime.length < 3) {
                if (splitTime[0].length() > 1) {
                    dtime = Double.parseDouble("0." + splitTime[0]);

                } else {
                    dtime = Double.parseDouble("0.0" + splitTime[0]);
                }
                car.setTime(dtime);
            } else {
                if (splitTime[2].length() > 1) {
                    dtime = Double.parseDouble(splitTime[0] + "." + splitTime[2]);
                } else dtime = Double.parseDouble(splitTime[0] + ".0" + splitTime[2]);
                car.setTime(dtime);
            }

            sortedFreeCabsList.add(car);
        }

        Collections.sort(sortedFreeCabsList, new CustomComparator());
    }

    public void resetSelect() throws URISyntaxException, ParserConfigurationException, SAXException, IOException {
        selectedCar = null;

    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public MapModel getMapModel() {
        return mapModel;
    }

    public void setMapModel(MapModel mapModel) {
        this.mapModel = mapModel;
    }

    public List<Car> getCabsList() {
        return cabsList;
    }

    public void setCabsList(List<Car> carList) {
        this.cabsList = carList;
    }

    public boolean isSearched() {
        return searched;
    }

    public void setSearched(boolean searched) {
        this.searched = searched;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public LatLng getDestination() {
        return destination;
    }

    public void setDestination(LatLng destination) {
        this.destination = destination;
    }

    public Marker getMarker() {
        return marker;
    }

    public void setMarker(Marker marker) {
        this.marker = marker;
    }

    public String getCenterMap() {
        return centerMap;
    }

    public void setCenterMap(String centerMap) {
        this.centerMap = centerMap;
    }

    public LatLng getOrigin() {
        return origin;
    }

    public void setOrigin(LatLng origin) {
        this.origin = origin;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public List<Marker> getFreeCabsMarkerList() {
        return freeCabsMarkerList;
    }

    public void setFreeCabsMarkerList(List<Marker> freeCabsMarkerList) {
        this.freeCabsMarkerList = freeCabsMarkerList;
    }

    public void setFreeCabsList(List<Car> freeCabsList) {
        this.freeCabsList = freeCabsList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Car getSelectedCar() {
        return selectedCar;
    }

    public void setSelectedCar(Car selectedCar) {
        this.selectedCar = selectedCar;
    }

    public List<Car> getSortedFreeCabsList() {
        return sortedFreeCabsList;
    }

    public void setSortedFreeCabsList(List<Car> sortedFreeCabsList) {
        this.sortedFreeCabsList = sortedFreeCabsList;
    }

    public Double getDtime() {
        return dtime;
    }

    public void setDtime(Double dtime) {
        this.dtime = dtime;
    }
}