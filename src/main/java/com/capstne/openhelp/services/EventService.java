package com.capstone.openhelp.services;


import com.capstone.openhelp.models.User;
import com.capstone.openhelp.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {
    private UserRepository userDao;

    public EventService(UserRepository userDao) {
        this.userDao = userDao;
    }

    //IS GET USER BY NAME CORRECT????? -ROGER
    public User getUserByName(String name){
        List<User> users = userDao.findAll();
        User found = new User();
        for (User user : users) {
            if(user.getName().toLowerCase().equals(name.toLowerCase())){
                found.setId(user.getId());
                found.setName(name);
                found.setEmail(user.getEmail());
                found.setPassword(user.getPassword());
            }
        }
        return found;
    }

//    //! Add user to categories
//    public void addPostToCategories(Event event){
//        System.out.println("AddPostToCategories");
//        for (Categories category : event.getCategories()) {
//            category.addEvent(event);
//        }
//    }
}