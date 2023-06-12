package com.jpatest.springjpa.event.listener;

import com.jpatest.springjpa.entity.User;
import com.jpatest.springjpa.event.RegistrationCompleteEvent;
import com.jpatest.springjpa.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {
    private final UserService userService;
    @Autowired
    public RegistrationCompleteEventListener(UserService userService){
        this.userService = userService;
    }
    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event){
        //Create verification token with link
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        userService.saveVerificationTokenForUser(token, user);

        //Send mail to user
        String url =
                event.getApplicationUrl() + "verifyRegistration?token=" + token;

        //sendVerificationEmail()

        log.info("Click the link to verify your account: {}", url);

    }
}
