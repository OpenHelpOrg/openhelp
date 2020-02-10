package com.capstone.openhelp.controllers;

import com.capstone.openhelp.models.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class LoginController {
    private String username;

//    @GetMapping("/login")
//    public String showLogin(){
//        return "login";
//    }

    @GetMapping("/login")
    public String showLogin(Model model){
        model.addAttribute("user", new User());
        return "login";
    }

//    @PostMapping("/login")
//    public String Login(){
//        return "redirect:/index";
//    }

//    @PostMapping("/login")
//    public String authLogin(
//            @RequestParam String username,
//            @RequestParam String password,
//            Model model
//    ) {
//        System.out.println(username);
//        System.out.println(password);
//        if(username.equals("admin"))
//            if(password.equals("password")) {
//                this.username = username;
//                System.out.println("yes");
//                model.addAttribute("username", username);
//                return "profile";
//            }
//        model.addAttribute("alert", true);
//        return "users/login";
//
//    }
//
//    @GetMapping("/profile")
//    public String showProfile(Model model){
//        model.addAttribute("username", username);
//        return "profile";
//    }
}