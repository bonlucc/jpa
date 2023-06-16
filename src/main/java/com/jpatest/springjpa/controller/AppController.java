package com.jpatest.springjpa.controller;

//import

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Controller
  public class AppController {
    
    private final JavaMailSender mailSender;

    @Autowired
    public AppController(JavaMailSender javaMailSender){
      this.mailSender = javaMailSender;
    }

    public void sendPlainTextEmail(String head, String body) throws Exception{
      String sender = "sender@gmail.com";
      String recipient = "recipient@gmail.com";

      SimpleMailMessage message = new SimpleMailMessage();

      message.setFrom(sender);
      message.setTo(recipient);
      message.setSubject(head);
      message.setText(body);
      
      mailSender.send(message);
    }

    public void sendHtmlEmail(String head, String body) throws Exception{
      String sender = "sender@gmail.com";
      String recipient = "recipient@gmail.com";

      MimeMessage message = mailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(message);

      helper.setSubject(head);
      helper.setFrom(sender);
      helper.setTo(recipient);

      boolean html = true;
      helper.setText(body, html);

      mailSender.send(message);
      
    }

    public void sendHtmlEmailWithAttachment(String head, String body, String attachmentPath, String fileName) throws MessagingException {
      String sender = "sender@gmail.com";
      String recipient = "recipient@gmail.com";

      MimeMessage message = mailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(message);

      helper.setSubject(head);
      helper.setFrom(sender);
      helper.setTo(recipient);
      

      boolean html = true;
      helper.setText(body, html);

      FileSystemResource file = new FileSystemResource(new File(attachmentPath));
      helper.addAttachment(fileName, file);

      mailSender.send(message);
    }

    public void sendHtmlEmailWithInlineImage(String head, String body, String imagePath, String imageName) throws MessagingException {
       String sender = "sender@gmail.com";
      String recipient = "recipient@gmail.com";

      MimeMessage message = mailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(message);

      helper.setSubject(head);
      helper.setFrom(sender);
      helper.setTo(recipient);
      

      boolean html = true;
      helper.setText(body, html);

      FileSystemResource resource = new FileSystemResource(new File(imagePath));

      helper.addInline(imageName, resource);

      mailSender.send(message);
    }
    
  }