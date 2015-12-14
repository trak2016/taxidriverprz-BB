package com.pgs.taxidriver.websocket;

import com.pgs.taxidriver.controller.MapManagedBean;
import com.pgs.taxidriver.model.Car;
import com.pgs.taxidriver.model.Course;
import com.pgs.taxidriver.model.Notification;
import com.pgs.taxidriver.model.User;
import com.pgs.taxidriver.service.CarService;
import com.pgs.taxidriver.service.CourseService;
import com.pgs.taxidriver.service.UserService;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.List;

@Getter
@Setter
@Controller("webSocket")
public class MessageController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    MapManagedBean mapManagedBean;

    @Autowired
    UserService userService;

    @Autowired
    CourseService courseService;

    @Autowired
    CarService carService;

    private Boolean statusOfResponse = false;
    private String clientPhoneNumber;

   /* @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Message greeting(HelloMessage message) throws Exception {
        //Thread.sleep(3000); // simulated delay
        return new Message("Hello, " + message.getName() + "!");
    }*/

    //@MessageMapping("/addcourse")
    //@SendTo("/topic/notification")
    public void addcourse() throws Exception {
        Message m = new Message("bla");
        Notification notification = new Notification();
        notification.setClientAddress(mapManagedBean.getAddress());
        notification.setClientPhone(clientPhoneNumber);
        List<Car> listOfFreeCabs = mapManagedBean.getSortedFreeCabsList();
        listOfFreeCabs.stream().map(x->x.getDriver().getLogin()).forEach(System.out::println);
        listOfFreeCabs.stream().map(x->x.getDriver().getLogin()).forEach(x ->
        {
            this.messagingTemplate.convertAndSendToUser(x, "/topic/notification", notification);
        }
        );

       /* String user = "owner2";
        this.messagingTemplate.convertAndSendToUser(user, "/queue/notification", m);*/
    }

    @MessageMapping("/notificationResponse")
    public void responseSocket(Notification response){
        //statusOfResponse = response.getStatus();
        Course course = new Course();
        course.setDistance((float) 250.0);
        course.setCost((float) 300);
        course.setDate(Calendar.getInstance().getTime());
        course.setCustomerPhoneNumber(Integer.parseInt(response.getClientPhone()));
        User user = new User();
        user = userService.findByLogin(response.getUser());
        course.setUser(user);
        courseService.addCourse(course);

        Car car = new Car();
        car = carService.getCarByUserId(user.getId());
        car.setStatus(true);
        carService.updateCar(car);
    }



}
