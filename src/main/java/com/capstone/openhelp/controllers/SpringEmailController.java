package com.capstone.openhelp.controllers;

import com.capstone.openhelp.models.Event;
import com.capstone.openhelp.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
class SpringEmailController {

    @Autowired
    public EmailService emailsvc;

    @GetMapping("/email")
    @ResponseBody
    public String testemail() {
        emailsvc.prepareAndSend(new Event(), "Test","This is kinda working how I would like it");
        return "test email";
    }

}
