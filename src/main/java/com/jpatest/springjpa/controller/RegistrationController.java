package com.jpatest.springjpa.controller;

import com.jpatest.springjpa.entity.AppUser;
import com.jpatest.springjpa.entity.VerificationToken;
import com.jpatest.springjpa.event.RegistrationCompleteEvent;
import com.jpatest.springjpa.model.PasswordModel;
import com.jpatest.springjpa.model.UserModel;
import com.jpatest.springjpa.service.UserService;
import com.jpatest.springjpa.controller.AppController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/")
public class RegistrationController {
    private final UserService userService;
    private final ApplicationEventPublisher publisher;
    private final AppController appController;
    @Autowired
    public RegistrationController(UserService userService, ApplicationEventPublisher publisher, AppController appController){
        this.userService = userService;
        this.publisher = publisher;
        this.appController = appController;
    }
    @PostMapping("/register")
    public AppUser registerUser(@RequestBody UserModel userModel, final HttpServletRequest request){
        AppUser appUser = userService.registerUser(userModel);
        publisher.publishEvent(new RegistrationCompleteEvent(appUser, applicationUrl(request)));
        return appUser;
    }

    @GetMapping("/verifyRegistration")
    public String verifyRegistration(@RequestParam("token") String token){
      if(userService.validVerificationToken(token)){
        return "User Verified";
      }
      return "Bad User";
    }

    @GetMapping("/resendVerifyToken")
    public String resendVerificationToken(@RequestParam("token") String oldToken, HttpServletRequest request){
      VerificationToken verificationToken = userService.generateNewVerificationToken(oldToken);
      //AppUser appUser = verificationToken.getAppUser();
      resendVerificationTokenMail(verificationToken, applicationUrl(request)); //or use an event to send it
      return "Verification Link Sent";
      

      
    }
    private String applicationUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + "/" + request.getContextPath();

    }
  
    private void resendVerificationTokenMail(VerificationToken verificationToken, String applicationUrl){
      String url = applicationUrl + "/verifyRegistration?token=" + verificationToken.getToken();
      appController.sendHtmlEmail("Verification Email:", "Click the link to verify your account:" + url);
      //log.info("Click the link to verify your account: {}", url);
      
    }
    /*
    @PostMapping("/resetPassword")
    public String resetPassword (@RequestBody String email){
      AppUser appUser = userService.getUserByEmail(email);
      
      if(appUser == null){
        return "";
      }
      publisher.publishEvent();
    }
  */

    @PostMapping("/resetPassword")
    public String resetPassword (@RequestBody PasswordModel passwordModel, HttpServletRequest request){
      AppUser appUser = userService.findUserByEmail(passwordModel.getEmail());
      if(appUser == null){
        return "";
      }
      String token = UUID.randomUUID().toString();
      userService.createPasswordResetTokenForUser(appUser, token);
      sendPasswordResetMail(token, applicationUrl(request));
      return "Email sent!";
    }

    private void sendPasswordResetMail(String token, String applicationUrl) {
        String url = applicationUrl + "/verifyPasswordReset?token=" + token;
        appController.sendHtmlEmail("Password Reset Email:", "Click the link to reset your password:" + url);
        //log.info("Click the link to verify the account: {}", url);
    }

    @GetMapping("/verifyPasswordReset")
    public String verifyPasswordReset(@RequestParam("token") String token){
      if(userService.validPasswordResetToken(token)){

        //TO DO
        return "User Verified";
      }
      return "Bad User";
    }

}
