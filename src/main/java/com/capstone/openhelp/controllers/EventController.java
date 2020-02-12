package com.capstone.openhelp.controllers;


import com.capstone.openhelp.models.Event;
import com.capstone.openhelp.models.User;
//import com.capstone.openhelp.services.EmailService;
import com.capstone.openhelp.models.UserEvents;
import com.capstone.openhelp.repositories.UserEventRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import com.capstone.openhelp.repositories.EventRepository;
import com.capstone.openhelp.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class EventController {

//    MINIMUM MAPPING !!!!!!
    @GetMapping("/index")
    public String eventsIndex(){
        return "index";
    }

    private final EventRepository eventDao;
    private final UserRepository userDao;
    private final UserEventRepository userEventDao;
//    private final EmailService emailService;


    public EventController(EventRepository eventDao, UserRepository userDao, UserEventRepository userEventDao) {
        this.eventDao = eventDao;
        this.userDao = userDao;
        this.userEventDao = userEventDao;
//        this.emailService = emailService;
    }



    //SHOW ALL
    @GetMapping("/events")
    public String all(Model model) {
        List<Event> events = eventDao.findAll();
        model.addAttribute("events", events);
        List<User> users = userDao.findAll();
        model.addAttribute("users", users);
        return "events/eventsindex";
    }

    @GetMapping("/events/edit/{id}")
    public String editEventForm
            (@PathVariable Long id,
             Model model){
        model.addAttribute("event", eventDao.getOne(id));
        return "events/edit";
    }


    @PostMapping("/events/edit")
    public String editEvent(@ModelAttribute Event event){
        eventDao.save(event);
//        return "redirect:/users/profile";
        return "redirect:/index";
    }


    //CREATE
    @GetMapping("/events/create")
    public String createForm(Model model) {
        model.addAttribute("event", new Event());
        return "events/create";
    }



    //NEED TO ATTACH USER TO EVENT !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    @PostMapping("/events/create")
    public String createEvent(@ModelAttribute Event event){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        event.setUser(user); //event model- set user to specific event
        eventDao.save(event); //event repo extends jpa repo
//        emailService.prepareAndSend(event,"You just made a event","you just made a event"); //EmailService.java model
        return "redirect:/events";
    }

    //DELETE  *** NOT SURE ON RETURN PATH !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    @GetMapping("/events/delete/{id}")
    public String showDelete(
            @PathVariable long id,
            Model model) {
        model.addAttribute("id", id);
        return "events/delete";
    }


    @PostMapping("/events/delete/{id}")
    public String deleteevent(
            @PathVariable long id){
        eventDao.deleteById(id);
        return "redirect:/events";
    }

    @GetMapping("/events/singleevent/{id}")
    public String returnOneToOneView(@PathVariable long id, Model model){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("userId", user.getId());
        model.addAttribute("event", eventDao.findById(id));
        return "events/singleevent";
    }

    @GetMapping("/events/singleevent/{id}/enroll")
    public String enrollEnvent(@PathVariable long id, Model model){
        Event event = eventDao.findById(id);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        int limit = event.getVol_limit();
        int current = 0;

        for (int x =0; x < event.getUserEvents().size(); x++){
            if(!event.getUserEvents().get(x).isIs_creator()){
                current++;
            }
        }

        if(current < limit){
//            List<UserEvents> enroll = new ArrayList<>();
//            enroll.add(new UserEvents(user,event, false));
//            event.setUserEvents(enroll);

            userEventDao.save(new UserEvents(user,event, false));
            model.addAttribute("response", "yes");
        }else {
            model.addAttribute("response", "no");
        }

        model.addAttribute("user", user);
        model.addAttribute("event", event);

        return "/events/confirmevent";
    }

}