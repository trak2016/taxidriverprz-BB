package com.pgs.taxidriver.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;


   /* @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Message greeting(HelloMessage message) throws Exception {
        //Thread.sleep(3000); // simulated delay
        return new Message("Hello, " + message.getName() + "!");
    }*/

    @MessageMapping("/addcourse")
    //@SendTo("/topic/notification")
    public void addcourse(HelloMessage message) throws Exception {
        Message m = new Message(message.getName());
        String user = "owner2";
        this.messagingTemplate.convertAndSendToUser(user, "/queue/notification", m);
    }

}
