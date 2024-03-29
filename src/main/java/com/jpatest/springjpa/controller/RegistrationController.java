package com.jpatest.springjpa.controller;

import com.jpatest.springjpa.entity.AppUser;
import com.jpatest.springjpa.entity.Course;
import com.jpatest.springjpa.entity.VerificationToken;
import com.jpatest.springjpa.event.RegistrationCompleteEvent;
import com.jpatest.springjpa.model.PasswordModel;
import com.jpatest.springjpa.model.UserModel;
import com.jpatest.springjpa.service.UserService;
import com.jpatest.springjpa.controller.AppController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/")
@Transactional
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
    public String resendVerificationToken(@RequestParam("token") String oldToken, HttpServletRequest request) throws Exception {
      VerificationToken verificationToken = userService.generateNewVerificationToken(oldToken);
      //AppUser appUser = verificationToken.getAppUser();
      resendVerificationTokenMail(verificationToken, applicationUrl(request)); //or use an event to send it
      return "Verification Link Sent";
      

      
    }
    private String applicationUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + "/" + request.getContextPath();

    }
  
    private void resendVerificationTokenMail(VerificationToken verificationToken, String applicationUrl) throws Exception {
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
    public String resetPassword (@RequestBody PasswordModel passwordModel, HttpServletRequest request) throws Exception {
      AppUser appUser = userService.findAppUserByEmail(passwordModel.getEmail());
      if(appUser == null){
        return "";
      }
      String token = UUID.randomUUID().toString();
      userService.createPasswordResetTokenForUser(appUser, token);
      sendPasswordResetMail(token, applicationUrl(request));
      return "Email sent!";
    }

    private void sendPasswordResetMail(String token, String applicationUrl) throws Exception {
        String url = applicationUrl + "/verifyPasswordReset?token=" + token;
        appController.sendHtmlEmail("Password Reset Email:", "Click the link to reset your password:" + url);
        //log.info("Click the link to verify the account: {}", url);
    }

    @PostMapping("/verifyPasswordReset")
    public String verifyPasswordReset(@RequestParam("token") String token, @RequestBody PasswordModel passwordModel){
      if(!userService.validPasswordResetToken(token)){

        //TO DO
        return "Bad User";
      }
      Optional<AppUser> appUserOpt =  userService.getAppUserByPasswordResetToken(token);
        appUserOpt.ifPresent(appUser -> userService.changePassword(appUser, passwordModel.getNewPassword()));
      return "Password changed";
    }

    @PostMapping("/changePassword")
    public String changePassword(@RequestBody PasswordModel passwordModel){
      AppUser appUser = userService.findAppUserByEmail(passwordModel.getEmail());
      if(!userService.passwordsMatch(appUser.getPassword(), passwordModel.getOldPassword())){
        return "Passwords don't match";
      }
       userService.changePassword(appUser, passwordModel.getNewPassword());
       return "Passwords Changed";
    }

    @GetMapping("/mystring")
    public AppUser myString(){
        return AppUser.builder().firstName("Akasha").lastName("data").build();
    }



    //@GetMapping("/user/registration")
}
