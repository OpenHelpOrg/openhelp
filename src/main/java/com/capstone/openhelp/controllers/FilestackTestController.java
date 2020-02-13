package com.capstone.openhelp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FilestackTestController {

    @GetMapping("/fs")
    public String filestackTest(){
        return ("filestacktest");
    }
}
