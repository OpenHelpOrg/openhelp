package com.capstone.openhelp.services;

import com.capstone.openhelp.models.Event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service("mailService")
public class EmailService {

    @Autowired
    public JavaMailSender emailSender;


    @Value("${spring.mail.from}")
    private String from;

    //NEED TO FIGURE OUT HOW TO ATTACH USER TO EVENT -ROGER !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    public void prepareAndSend(Event event, String subject, String description) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(from);
        msg.setTo("christian.crousserkaiman@gmail.com");
        msg.setSubject(subject);
        msg.setText(description);

        try{
            this.emailSender.send(msg);
        }
        catch (MailException ex) {
            // simply log it and go on...
            System.err.println(ex.getMessage());
        }
    }
}