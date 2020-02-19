package com.capstone.openhelp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

    @Controller
public class TestimonialController {

        @GetMapping("/testimonials")
        @ResponseBody
        public String testimonials(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
            model.addAttribute("name", name);
            return "testimonials";
        }

    }


