package com.capstone.openhelp.controllers;


import com.capstone.openhelp.models.User;
import com.capstone.openhelp.models.VerificationToken;
import com.capstone.openhelp.repositories.UserRepository;
import com.capstone.openhelp.repositories.VerificationTokenRespository;
import com.capstone.openhelp.services.EmailService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class UserController {

    private UserRepository userDao;

    private PasswordEncoder passwordEncoder;

    private VerificationTokenRespository verificationDao;

    private EmailService emailService;


    public UserController(UserRepository userDao, PasswordEncoder passwordEncoder, VerificationTokenRespository verificationDao, EmailService emailService) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.verificationDao = verificationDao;
        this.emailService = emailService;
    }

    //displays all organization on our db
    @GetMapping("/users")
    public String showIndex(Model model){
        List<User> users = userDao.findAll();
        List<User> corporations = new ArrayList<>();

        for(int i=0; i < users.size(); i++){
            if(users.get(i).isIs_org()){
                corporations.add(users.get(i));
            }
        }
        model.addAttribute("organizations", corporations);

        return "users/users";
    }

    @GetMapping("/user/{id}")
    public String showSingleUser(
            @PathVariable long id,
            Model model) {
        User user = userDao.getOne(id);
        model.addAttribute("user", user);
        return "users/singleuser";
    }

    @PostMapping("/users/profile")
    public String editUser(@ModelAttribute User user) {
        User log = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        user.setId(log.getId());
//        String currentImg = user.getImage();

        userDao.save(user);
        return ("redirect:/users/profile");
    }

    //! DELETE
    @GetMapping("/users/delete/{id}")
    public String showDelete(
            @PathVariable long id,
            Model model) {
        model.addAttribute("id", id);
        return "users/delete";
    }

    @PostMapping("/users/delete/{id}")
    public String deleteUser(
            @PathVariable long id) {
        User user = userDao.findById(id);
        userDao.delete(user);
        return "redirect:/events";
    }


    @GetMapping("/sign-up")
    public String showSignupForm(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/sign-up")
    public String saveUser(@ModelAttribute User user) {
        String hash = passwordEncoder.encode(user.getPassword());
        user.setPassword(hash);
        user.setUsername(user.getEmail());
        user.setImage("https://storage.jewnetwork.com/content/users/avatars/3746/avatar_3746_500.jpg");
        userDao.save(user);

        //this is for email verification
        VerificationToken verificationToken = new VerificationToken(user);
        verificationDao.save(verificationToken);
        /////////
        //this is the section to send an email with the confirmation token
        emailService.confirmEmail(user, verificationToken);
        return "login";
    }

    @GetMapping("/confirm-account")
    public String confirmUserAccount(@RequestParam String token, Model model){
        VerificationToken confirm = verificationDao.findByConfirmationToken(token);

        if(confirm != null){
            User user = userDao.findByEmail(confirm.getUser().getEmail());
            user.setEnabled(true);
            userDao.save(user);
            emailService.createAccountEmail(user);
            model.addAttribute("message", "Thank you for verifying your email. Please use now your credentials to login");
        }else {
            model.addAttribute("message", "Link is invalid or broken");
        }

        model.addAttribute("user", new User());
        return "login";
    }

    @GetMapping("/users/profile")
    public String showUserProfile(Model model) {
        User log = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDao.findById(log.getId());
        model.addAttribute("user", user);
        return ("users/profile");
    }

    @PostMapping("/users/imageupload")
    public String uploadUserImage(@RequestParam String filetoupload){
        User log = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDao.findById(log.getId());
        user.setImage(filetoupload);
        userDao.save(user);
        return("redirect:/users/profile");
    }

@GetMapping("/test")
    public String testlogin(Model model){
    model.addAttribute("user", new User());
    return "redirect:/login";
}
}

