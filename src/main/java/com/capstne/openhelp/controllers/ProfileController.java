//package com.capstone.openhelp.controllers;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//
//@Controller
//public class ProfileController {
//
//    @GetMapping("/profile/{username}")
//    public String viewProfile(@PathVariable String username, Model model){
//        model.addAttribute("username", username);
//        return "login";
//    }
//}