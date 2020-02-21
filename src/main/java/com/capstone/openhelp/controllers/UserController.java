package com.capstone.openhelp.controllers;


import com.capstone.openhelp.models.Event;
import com.capstone.openhelp.models.User;
import com.capstone.openhelp.models.UserEvents;
import com.capstone.openhelp.models.VerificationToken;
import com.capstone.openhelp.repositories.UserEventRepository;
import com.capstone.openhelp.repositories.EventRepository;
import com.capstone.openhelp.repositories.UserRepository;
import com.capstone.openhelp.repositories.VerificationTokenRespository;
import com.capstone.openhelp.services.EmailService;
import com.capstone.openhelp.services.PasswordChecker;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    private UserRepository userDao;

    private EventRepository eventDao;

    private PasswordEncoder passwordEncoder;

    private VerificationTokenRespository verificationDao;

    private EmailService emailService;

    private PasswordChecker checkPassword;

    private final UserEventRepository userEventDao;


    public UserController(UserRepository userDao,
                          PasswordEncoder passwordEncoder,
                          VerificationTokenRespository verificationDao,
                          EmailService emailService,
                          PasswordChecker checkPassword,
                          UserEventRepository userEventDao,
                          EventRepository eventdao){

        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.verificationDao = verificationDao;
        this.emailService = emailService;
        this.checkPassword = checkPassword;
        this.userEventDao = userEventDao;
        this.eventDao = eventdao;
    }

    //displays all organization on our db
    @GetMapping("/users")
    public String showIndex(Model model) {
        List<User> users = userDao.findAll();
        List<User> corporations = new ArrayList<>();

        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).isIs_org()) {
                corporations.add(users.get(i));
            }
        }
        model.addAttribute("organizations", corporations);

        return "users/users";
    }

    //Send the current login use as json
    @GetMapping("/user/loginUser.json")
    public @ResponseBody User viewCurrentLoginUser(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user;
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
        return ("redirect:users/profile");
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
        return "redirect:events";
    }


    @GetMapping("/sign-up")
    public String showSignupForm(Model model) {
//        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/sign-up")
    public String saveUser(Model model,
                           @RequestParam("regEmail") String regEmail,
                           @RequestParam("regName") String regName,
                           @RequestParam("regPassword") String regPassword,
                           @RequestParam("regPasswordConfirm") String regPasswordConfirm) {
        model.getAttribute(regEmail);
        model.getAttribute(regName);
        model.getAttribute(regPassword);
        model.getAttribute(regPasswordConfirm);

//            checks if email already exists in DB
        if (userDao.findByEmail(regEmail) != null) {
            model.addAttribute("message", "*A user with that email already exists");
            return ("login");
//            checks that password and password confirm match
        } else if (!regPassword.equals(regPasswordConfirm)) {
            model.addAttribute("message", "*Please ensure your passwords match!");
            return ("login");
//            checks that password meets strength criteria
        } else if (!checkPassword.CheckPassword(regPassword)) {
            model.addAttribute("message", "*Please ensure your password meets the criteria");
            return ("login");
        } else {


            User user = new User();
            user.setEmail(regEmail);
            user.setUsername(user.getEmail());
            user.setName(regName);
            String hash = passwordEncoder.encode(regPassword);
            user.setPassword(hash);
            user.setImage("https://storage.jewnetwork.com/content/users/avatars/3746/avatar_3746_500.jpg");
            userDao.save(user);

            //this is for email verification
            VerificationToken verificationToken = new VerificationToken(user);
            verificationDao.save(verificationToken);
            //this is the section to send an email with the confirmation token
            emailService.confirmEmail(user, verificationToken);
            model.addAttribute("message", "A confirmation email was sent to " + user.getEmail());
            return "login";
        }
    }


    @GetMapping("/confirm-account")
    public String confirmUserAccount(@RequestParam String token, Model model) {
        VerificationToken confirm = verificationDao.findByConfirmationToken(token);

        if (confirm != null) {
            User user = userDao.findByEmail(confirm.getUser().getEmail());
            user.setEnabled(true);
            userDao.save(user);
            emailService.createAccountEmail(user);
            model.addAttribute("message", "Thank you for verifying your email. Please use now your credentials to login");
        } else {
            model.addAttribute("message", "Link is invalid or broken");
        }

//        model.addAttribute("user", new User());
        return "login";
    }

    @GetMapping("/users/profile")
    public String showUserProfile(Model model) {
        User log = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDao.findById(log.getId());
        model.addAttribute("user", user);
        model.addAttribute("futureevents", eventDao.findFutureEvents());
        model.addAttribute("pastevents", eventDao.findPastEvents());
        return ("users/profile");
    }

    @PostMapping("/users/imageupload")
    public String uploadUserImage(@RequestParam String filetoupload) {
        User log = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDao.findById(log.getId());
        user.setImage(filetoupload);
        userDao.save(user);
        return ("redirect:users/profile");
    }

    @GetMapping("/test")
    public String testlogin(Model model) {
//        model.addAttribute("user", new User());
        return "redirect:login";
    }

    @PostMapping("/users/confirm-enrollment")
    public String confirmEnrollment(@RequestParam("users") List<String> users, @RequestParam long id){
        Event event = eventDao.getOne(id);

        for(int x = 0; x < users.size(); x++){
            Long idC = Long.parseLong(users.get(x));
            userEventDao.deleteById(idC);
        }

        return "redirect:events/edit/" + id;
    }
    @PostMapping("/users/confirm-attendance")
    public String confirmAttendance(@RequestParam("users") List<String> users, @RequestParam long id){
       Event event = eventDao.getOne(id);

       for(int x = 0; x < event.getUserEvents().size(); x++){
           if(users.contains(Long.toString(event.getUserEvents().get(x).getId()))){
               event.getUserEvents().get(x).setAttended(true);
           }else {
               event.getUserEvents().get(x).setAttended(false);
           }

           userEventDao.save(event.getUserEvents().get(x));
       }

//        for(int x=0; x < users.size(); x++){
//            UserEvents event = userEventDao.getOne(Long.parseLong(users.get(x)));
//            event.setAttended(true);
//            userEventDao.save(event);
//            System.out.println(users.get(x));
//        }
        return "redirect:events/edit/" + id;
    }
}

