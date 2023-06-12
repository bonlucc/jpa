package com.jpatest.springjpa.controller;

import com.jpatest.springjpa.entity.User;
import com.jpatest.springjpa.event.RegistrationCompleteEvent;
import com.jpatest.springjpa.model.UserModel;
import com.jpatest.springjpa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class RegistrationController {
    private final UserService userService;
    private final ApplicationEventPublisher publisher;
    @Autowired
    public RegistrationController(UserService userService, ApplicationEventPublisher publisher){
        this.userService = userService;
        this.publisher = publisher;
    }
    @PostMapping("/register")
    public User registerUser(@RequestBody UserModel userModel){
        User user = userService.registerUser(userModel);
        publisher.publishEvent(new RegistrationCompleteEvent(user,"url"));
        return user;
    }
}
