package com.capstone.openhelp.controllers;
//import com.capstone.openhelp.services.EmailService;
import com.capstone.openhelp.models.Event;
import com.capstone.openhelp.models.User;
import com.capstone.openhelp.repositories.CategoryRespository;
import com.capstone.openhelp.repositories.UserEventRepository;
import com.capstone.openhelp.services.EmailService;
import org.springframework.beans.factory.annotation.Value;
import com.capstone.openhelp.repositories.EventRepository;
import com.capstone.openhelp.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class SearchController {
    @Value("${spring.mail.mapbox}")
    private String mapbox;

    private final EventRepository eventDao;
    private final UserRepository userDao;
    private final CategoryRespository categoryDao;
    private final UserEventRepository userEventDao;
//    private EmailService emailService;
//    private final EmailService emailService;


    public SearchController(EventRepository eventDao, UserRepository userDao, CategoryRespository categoryDao, UserEventRepository userEventDao) {
        this.eventDao = eventDao;
        this.userDao = userDao;
        this.categoryDao = categoryDao;
        this.userEventDao = userEventDao;
    }


    @GetMapping("/search")
    public String eventsearch(@RequestParam String query, String search_param, Model model) {
        if (search_param.equals("event")) {
            List<Event> results = eventDao.findByTitleContainsOrSummaryContainsAllIgnoreCase(query, query);
            model.addAttribute("results", results);
            return "events/eventsearch";
        } else if (search_param.equals("user")) {
            List<User> results = userDao.findByNameContainsOrEmailContainsAllIgnoreCase(query, query);
            model.addAttribute("results", results);
//            System.out.println(model);
            return "users/usersearch";

        } else {
            System.out.println(search_param);
            System.out.println(query);
            return "home";
        }
    }
}
