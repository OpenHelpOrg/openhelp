package com.capstone.openhelp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class AboutController {

    @GetMapping("/about")
    public String aboutPage(){
        return "about";
    }

    @GetMapping("/500")
    public String errorPage(){
        return  "error/500";
    }


}
