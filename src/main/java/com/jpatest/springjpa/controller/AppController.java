package com.jpatest.springjpa.controller;

//import

@Controller
  public class AppController {
    
    private final JavaMailSender javaMailSender;

    @Autowired
    public AppController(JavaMailSender javaMailSender){
      this.javaMailSender = javaMailSender;
    }

    public void sendPlainTextEmail(String head, String body) throws Exception{
      String sender = "sender@gmail.com";
      String recipient = "recipient@gmail.com";

      SimpleMailMessage message = new SimpleMailMessage();

      message.setFrom(sender);
      message.setTo(recipient);
      message.setSubject(head);
      message.setTest(body);
      
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
      helper.SetText(body, html);

      mailSender.send(message);
      
    }

    public void sendHtmlEmailWithAttachment(String head, String body, String attachmentPath, String fileName){
      String sender = "sender@gmail.com";
      String recipient = "recipient@gmail.com";

      MimeMessage message = mailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(message);

      helper.setSubject(head);
      helper.setFrom(sender);
      helper.setTo(recipient);
      

      boolean html = true;
      helper.SetText(body, html);

      FileSystemResource file = new FileSystemResource(new File(attachmentPath));
      helper.addAttachment(fileName, file)

      mailSender.send(message);
    }

    public void sendHtmlEmailWithInlineImage(String head, String body, String imagePath, String imageName){
       String sender = "sender@gmail.com";
      String recipient = "recipient@gmail.com";

      MimeMessage message = mailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(message);

      helper.setSubject(head);
      helper.setFrom(sender);
      helper.setTo(recipient);
      

      boolean html = true;
      helper.SetText(body, html);

      FileSystemResource resource = new FileSystemResource(new File(imagePath));

      helper.addInline(imageName, resource);

      mailSender.send(message);
    }
    
  }