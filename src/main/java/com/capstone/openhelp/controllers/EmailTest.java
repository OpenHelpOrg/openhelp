package com.capstone.openhelp.controllers;

import com.capstone.openhelp.models.Event;
import com.capstone.openhelp.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class EmailTest {

    @Autowired
    public EmailService emailsvc;
    @GetMapping("/email")
    @ResponseBody
    public String testemail() {
        emailsvc.prepareAndSend(new Event(), "blah","blah blah");
        return "test email";
    }


}
