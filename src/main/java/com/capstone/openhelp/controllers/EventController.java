package com.capstone.openhelp.controllers;


import com.capstone.openhelp.models.*;
//import com.capstone.openhelp.services.EmailService;
import com.capstone.openhelp.repositories.CategoryRespository;
import com.capstone.openhelp.repositories.UserEventRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import com.capstone.openhelp.repositories.EventRepository;
import com.capstone.openhelp.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Controller
public class EventController {
    @Value("${spring.mail.mapbox}")
    private String mapbox;

//    MINIMUM MAPPING !!!!!!
    @GetMapping("/index")
    public String eventsIndex(){
        return "index";
    }

    private final EventRepository eventDao;
    private final UserRepository userDao;
    private final CategoryRespository categoryDao;
    private final UserEventRepository userEventDao;

//    private final EmailService emailService;


    public EventController(EventRepository eventDao, UserRepository userDao, UserEventRepository userEventDao, CategoryRespository categoryDao) {
        this.eventDao = eventDao;
        this.userDao = userDao;
        this.userEventDao = userEventDao;
        this.categoryDao = categoryDao;
//        this.emailService = emailService;
    }



    //SHOW ALL
    @GetMapping("/events")
    public String all(Model model) {
        List<Event> events = eventDao.findAll();
        model.addAttribute("events", events);
        List<User> users = userDao.findAll();
        model.addAttribute("users", users);
        model.addAttribute("mapbox", mapbox);
        return "events/eventsindex";
    }

    //Send all the events as json objects
    @GetMapping("/event/events.json")
    public @ResponseBody List<Event> viewAllEventsInJSON(){
        List<Event> events = eventDao.findAll();

        for(int x = 0; x < events.size(); x++){
            String date = events.get(x).getDate_time();
            date = date.replace(" ", "T");
            events.get(x).setDate_time(date);
        }

        return events;
    }

    @GetMapping("/events/edit/{id}")
    public String editEventForm
            (@PathVariable Long id,
             Model model){
        model.addAttribute("event", eventDao.getOne(id));
        model.addAttribute("categories", categoryDao.findAll());
        return "events/edit";
    }


    @PostMapping("/events/edit")
    public String editEvent(@ModelAttribute Event event){
        eventDao.save(event);
//        return "redirect:/users/profile";
        return "redirect:/events";
    }


    //CREATE
    @GetMapping("/events/create")
    public String createForm(Model model) {
        model.addAttribute("event", new Event());
        model.addAttribute("categories", categoryDao.findAll());
        System.out.println("create get");
        return "events/create";
    }


    //NEED TO ATTACH DEFAULT IMAGE TO EVENT !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    @PostMapping("/events/create")
    public String createEvent(@ModelAttribute Event event, @RequestParam(required = false) String image){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println("image :" + image);
        event.setImages(Objects.requireNonNullElse(image, "https://storage.jewnetwork.com/content/users/avatars/3746/avatar_3746_500.jpg"));

        eventDao.save(event);
        userEventDao.save(new UserEvents(user,event, true));
//        emailService.prepareAndSend(event,"You just made a event","you just made a event"); //EmailService.java model
        return "redirect:/events";
    }


    //DELETE
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
        Event event = eventDao.findById(id);

        for(int x=0; x < event.getUserEvents().size(); x++){
            if(event.getUserEvents().get(x).isIs_creator()){
                model.addAttribute("creator", event.getUserEvents().get(x).getUser());
            }
        }
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("userId", user.getId());
        model.addAttribute("event", event);
        model.addAttribute("mapbox", mapbox);

        return "events/singleevent";
    }

    //creates a json file for the event selected
    @GetMapping("/event/{id}/event.json")
    public @ResponseBody Event viewEventInJSON(@PathVariable long id){
        return eventDao.findById(id);
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
            }else {
                model.addAttribute("creator", event.getUserEvents().get(x).getUser());
            }
        }

        if(current < limit){
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