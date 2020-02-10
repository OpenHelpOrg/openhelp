package com.capstone.openhelp.services;

import com.capstone.openhelp.models.User;
import com.capstone.openhelp.models.UserWithRoles;
import com.capstone.openhelp.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsLoader implements UserDetailsService {
    private final UserRepository userDao;

    public UserDetailsLoader(UserRepository userDao) {
        this.userDao = userDao;

    }

    //IS THIS CORRECT??? -ROGER !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    //we're not using name for authentication, so will need to refactor to search for email
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userDao.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("No user found for " + email);
        }
        return new UserWithRoles(user);
    }
}