package com.jpatest.springjpa.controller;

import com.jpatest.springjpa.entity.AppUser;
import com.jpatest.springjpa.event.RegistrationCompleteEvent;
import com.jpatest.springjpa.model.UserModel;
import com.jpatest.springjpa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

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
    public AppUser registerUser(@RequestBody UserModel userModel, final HttpServletRequest request){
        AppUser appUser = userService.registerUser(userModel);
        publisher.publishEvent(new RegistrationCompleteEvent(appUser, applicationUrl(request)));
        return appUser;
    }

    private String applicationUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
}
