//package com.capstone.openhelp.controllers;
//
//import com.capstone.openhelp.services.Email;
//import com.capstone.openhelp.services.EmailConfig;
//import net.sargue.mailgun.Mail;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSenderImpl;
//import org.springframework.stereotype.Controller;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.xml.bind.ValidationException;
//
//
//@RestController
//@RequestMapping("/email")
//public class MailController {
//
//
//    private EmailConfig emailConfig;
//
//    public MailController(EmailConfig emailConfig) {
//        this.emailConfig = emailConfig;
//    }
//    public void sendEmail(@RequestBody Email email, BindingResult bindingResult) throws ValidationException {
//        if(bindingResult.hasErrors()) {
//            throw new ValidationException("Feedback is not valid");
//        }
//
//        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//        mailSender.setHost(this.emailConfig.getHost());
//        mailSender.setPort(this.emailConfig.getPort());
//        mailSender.setUsername(this.emailConfig.getUsername());
//        mailSender.setPassword(this.emailConfig.getPassword());
//
//        SimpleMailMessage mailMessage = new SimpleMailMessage();
//        mailMessage.setFrom(email.getEmail());
//        mailMessage.setTo("test@openhelp.com");
//        mailMessage.setSubject("New email from " + email.getName());
//        mailMessage.setText(email.getEmail());
//
//        mailSender.send(mailMessage);
//
//    }
//
//}
