package com.capstone.openhelp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

    @Controller
public class TestimonialController {

        @GetMapping("/testimonials")
        @ResponseBody
        public String testimonialPage() {
            return "testimonials";
        }

    }


