package com.capstone.openhelp.controllers;

import com.capstone.openhelp.models.*;
import com.capstone.openhelp.repositories.CategoryRepository;
import com.capstone.openhelp.repositories.UserEventRepository;
import com.capstone.openhelp.services.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import com.capstone.openhelp.repositories.EventRepository;
import com.capstone.openhelp.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
public class EventController {
    @Value("${spring.mail.mapbox}")
    private String mapbox;

    private final EventRepository eventDao;
    private final UserRepository userDao;
    private final CategoryRepository categoryDao;
    private final UserEventRepository userEventDao;
    private final EmailService emailService;

    public EventController(EventRepository eventDao, UserRepository userDao, UserEventRepository userEventDao, CategoryRepository categoryDao, EmailService emailService) {
        this.eventDao = eventDao;
        this.userDao = userDao;
        this.userEventDao = userEventDao;
        this.categoryDao = categoryDao;
        this.emailService = emailService;
    }

    //    MINIMUM MAPPING !!!!!!
    @GetMapping("/index")
    public String eventsIndex(){
        return "index";
    }

    //SHOW ALL
    @GetMapping("/events")
    public String all(Model model) {
        List<Event> events = eventDao.findAll();
        model.addAttribute("events", events);
        List<User> users = userDao.findAll();
        model.addAttribute("users", users);
        model.addAttribute("mapbox", mapbox);
        model.addAttribute("loginUser", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
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

    @GetMapping("/events/userevents.json")
    public @ResponseBody List<Event> viewEventUserEvents(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<UserEvents> userEvents = userEventDao.findAll();
        List<Event> events = new ArrayList<>();

        for(int x = 0; x < userEvents.size(); x++){
            if(userEvents.get(x).getUser().getId() == user.getId()){
                events.add(userEvents.get(x).getEvent());
            }
        }

        return events;
    }

    @PostMapping("/events/edit/{id}/send-message")
    public String eventSendMessageAll(@PathVariable Long id,
                                      Model model,
                                      @RequestParam(name = "subject")String subject,
                                      @RequestParam(name = "body")String body){
        Event event = eventDao.getOne(id);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<UserEvents> combinations = event.getUserEvents();

        for(int x = 0; x < combinations.size(); x++){
            if(!combinations.get(x).isIs_creator()){
                emailService.sendEmailAllVolunteers(user, combinations.get(x).getUser().getEmail(), subject, body);
            }
        }

        model.addAttribute("confirmation", "Emails were sent to all volunteers");
        return "/events/edit/" + id;
    }

    @GetMapping("/events/edit/{id}")
    public String editEventForm
            (@PathVariable Long id,
             Model model){
        LocalDateTime currDate = LocalDateTime.now();
        Event event = eventDao.getOne(id);
        String date = event.getDate_time();
        date = date.replace(" ", "T");
        event.setDate_time(date);

        LocalDateTime eventDate = LocalDateTime.parse(event.getDate_time());

        model.addAttribute("event", event);
        model.addAttribute("categories", categoryDao.findAll());

        List<UserEvents> userevents = new ArrayList<>();

        for(int x =0; x < event.getUserEvents().size(); x++){
            if(!event.getUserEvents().get(x).isIs_creator()){
                userevents.add(event.getUserEvents().get(x));
            }
        }

        model.addAttribute("userevents", userevents);

        if(eventDate.compareTo(currDate) < 0){
            model.addAttribute("isDisabled", "disable");
            model.addAttribute("titleMsg", "You cannot Edit this Event due to past date");
        }else{
            model.addAttribute("titleMsg", "Enter you event details below");
        }

        return "events/edit";
    }

    @PostMapping("/events/edit")
    public String editEvent(@ModelAttribute Event event){
        eventDao.save(event);

        //sends an email to all volunteers of any changes in the event.
        for(int x = 0; x < event.getUserEvents().size(); x++){
            if(!event.getUserEvents().get(x).isIs_creator()){
                emailService.eventEditEmail(event, event.getUserEvents().get(x).getUser());
            }
        }
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


    @PostMapping("/events/create")
    public String createEvent(@ModelAttribute Event event, @RequestParam(required = false) String image){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        eventDao.save(event);
        userEventDao.save(new UserEvents(user,event, true, ""));
//        emailService.prepareAndSend(event,"You just made a event","you just made a event"); //EmailService.java model
        return "redirect:/events";

    }

    @PostMapping("/events/add-story/{id}")
    public String addEventStory(@RequestParam String story, @PathVariable long id, Model model){
        UserEvents event = userEventDao.getOne(id);
        event.setStory(story);
        userEventDao.save(event);
        model.addAttribute("user", (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return "redirect:/users/profile";
    }

    //DELETE
    @GetMapping("/events/delete/{id}")
    public String showDelete(
            @PathVariable long id,
            Model model) {
        Event event= eventDao.getOne(id);
        model.addAttribute("event", event);
        return "events/delete";
    }


    @PostMapping("/events/delete/{id}")
    public String deleteevent(
            @PathVariable long id, Model model){

        Event event = eventDao.getOne(id);
        model.addAttribute("event", event);

        userEventDao.deleteAll(event.getUserEvents());
        eventDao.deleteById(event.getId());
        return "/events/deleteconfirmed";
    }

    @GetMapping("/events/singleevent/{id}")
    public String returnOneToOneView(@PathVariable long id, Model model){
        Event event = eventDao.findById(id);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        LocalDateTime currDate = LocalDateTime.now();
        String date = event.getDate_time();
        Boolean isCreator = false;
        Boolean isEnrolled = false;
        List<UserEvents> userEvents = new ArrayList<>();

        for(int x=0; x < event.getUserEvents().size(); x++){
            UserEvents userevent = event.getUserEvents().get(x);

            if(event.getUserEvents().get(x).getUser().getId() == user.getId()){
                isEnrolled = true;
            }

            if(event.getUserEvents().get(x).isIs_creator()){
                model.addAttribute("creator", event.getUserEvents().get(x).getUser());
                if(user.getId() == event.getUserEvents().get(x).getUser().getId()){
                    isCreator = true;
                }
            }

            if(userevent.getAttended() && !userevent.getStory().equals("") && !userevent.isIs_creator()){
                userEvents.add(userevent);
            }
        }

        date = date.replace(" ", "T");
        LocalDateTime eventDate = LocalDateTime.parse(date);

        if(eventDate.compareTo(currDate) < 0 || isCreator || isEnrolled){
            model.addAttribute("display", false);
        }else{
            model.addAttribute("display", true);
        }

        if(!isCreator && isEnrolled){
            model.addAttribute("enrollBanner", true);
        }

        model.addAttribute("userId", user.getId());
        model.addAttribute("event", event);
        model.addAttribute("mapbox", mapbox);
        model.addAttribute("stories", userEvents);

        return "events/singleevent";
    }

    //creates a json file for the event selected
    @GetMapping("/event/{id}/event.json")
    public @ResponseBody Event viewEventInJSON(@PathVariable long id){
        return eventDao.findById(id);
    }

    @GetMapping("/events/singleevent/{id}/enroll")
    public String enrollEvent(@PathVariable long id, Model model){
        Event event = eventDao.findById(id);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        emailService.enrollEventEmail(eventDao);

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
            userEventDao.save(new UserEvents(user,event, false, ""));

            model.addAttribute("response", "yes");
        }else {
            model.addAttribute("response", "no");
        }

        model.addAttribute("user", user);
        model.addAttribute("event", event);

        return "/events/confirmevent";
    }

    @GetMapping("/timedEvents")
    public String timedEvents(Model model){
        model.addAttribute("futureevents", eventDao.findFutureEvents());
        model.addAttribute("pastevents", eventDao.findPastEvents());
        return("pastFutureTest");
    }


}