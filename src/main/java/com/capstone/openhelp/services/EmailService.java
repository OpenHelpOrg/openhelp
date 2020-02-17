package com.capstone.openhelp.services;

import com.capstone.openhelp.models.Event;
import com.sendgrid.helpers.mail.objects.Email;
import com.capstone.openhelp.models.User;
import com.capstone.openhelp.models.VerificationToken;
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

    @Value("${spring.mail.from")
    private String from;


    public void prepareAndSend(Event event, String subject, String description) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(from);
        msg.setTo("christian.crousserkaiman@gmail.com");
        msg.setSubject(subject);
        msg.setText(description);
      
        try{
            this.emailSender.send(msg);
        }catch(MailException ex){
            System.err.println(ex.getMessage());
        }
    }
    public void confirmEmail(User user, VerificationToken token){
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(from);
        msg.setTo(user.getEmail());
        msg.setSubject("Confirm your email for OpenHelp");

        String body = "To confirm your account, please click here : http://localhost:8080/confirm-account?token="+ token.getConfirmationToken();
        msg.setText(body);

        try{
            this.emailSender.send(msg);
        }catch(MailException ex){
            System.err.println(ex.getMessage());
        }
    }


    public void MailEvent() {

    }

    public void EnrollEvent() {

    }
}


