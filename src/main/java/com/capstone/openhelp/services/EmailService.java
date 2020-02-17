package com.capstone.openhelp.services;

import com.capstone.openhelp.models.Event;
import com.capstone.openhelp.repositories.EventRepository;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Email;
import com.capstone.openhelp.models.User;
import com.capstone.openhelp.models.VerificationToken;
import javassist.Loader;
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

    public void createAccountEmail(User user){
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(from);
        msg.setTo(user.getEmail());
        msg.setSubject("Thank you for register with OpenHelp");

        String body = "Hello " + user.getName() + ", \n\nThank you for register with OpenHelp. Now you will be able to create events, and" +
                " also volunteer as well. Remember that your email is your username. Enjoy the application. \n\nSincerely, \nOpenHelp Team.";
        msg.setText(body);

        try{
            this.emailSender.send(msg);
        }catch (MailException ex){
            System.err.println(ex.getMessage());
        }
    }

    public void createEventEmail(User user, Event event) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(from);
        msg.setTo(user.getEmail());
        msg.setSubject("Event Creation");

        String body = "You have successfully created an event!" + user.getName() + " Here are the details.";
        msg.setText(body);
    }


    public void confirmEventEmail(User user, Event event) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(from);
        msg.setTo(user.getEmail());
        msg.setSubject("Attendence confirmation");

        String body = "You have confirmed yourself for this event";
        msg.setText(body);
    }


    public void enrollEventEmail(EventRepository eventDao, User user) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(from);
        msg.setTo(user.getEmail());
        msg.setSubject("Thank you for enrolling");

        String body = "Thank you for enrolling in this event!  For more details please contact your event organizer.  Thank you for using OpenHelp!";
        msg.setText(body);

    }
}


