package de.main.springsession.controller;

import de.main.springsession.model.User;
import de.main.springsession.model.UserSession;
import de.main.springsession.service.UserService;
import org.apache.tomcat.util.http.parser.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200/")
public class RestApi {

    @Autowired
    UserService userService;


    @GetMapping("/resource")
    public Map<String, Object> home(){

        Map<String, Object> model = new HashMap<>();
        model.put("Id", UUID.randomUUID().toString());
        model.put("content", "Kakyoin");
        return model;
    }
    @PostMapping("/users/login")
    public Map<String, Object> user(@RequestBody User user) throws InterruptedException {

        Map<String, Object> model = new HashMap<>();

       // try{
            if(userService.login(user)){
                model.put("login", true);
                UserSession us = userService.createSession(user);
                model.put("sessionId",us.getSessionId());
                model.put("sessionTimeout", us.getSessionTimeout());
            }else{
                model.put("login",false);
            }
   /*     }catch(NullPointerException e){
            System.err.println("Nullpointer");
        }*/

        return model;
    }

    @PostMapping("/users/logout")
    public Map<String, Object> userLogout(@RequestBody String sessionId){
        Map<String, Object> model = new HashMap<>();
        model.put("logout", false);
        return model;

    }

}
