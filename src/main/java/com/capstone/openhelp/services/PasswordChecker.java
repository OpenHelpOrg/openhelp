package com.capstone.openhelp.services;

import org.springframework.stereotype.Service;

@Service("passwordChecker")
public class PasswordChecker {

    public boolean CheckPassword(String password) {
//  at least one uppercase, one lowercase, one number, one special character, and at least 8 characters long
        if ((password.matches("(?=.*[0-9]).*"))
        && (password.matches("(?=.*[a-z]).*"))
        && (password.matches("(?=.*[A-Z]).*"))
        && (password.matches("(?=.*[~!@#$%^&*()_><=+?.,/]).*"))
        && (password.length() >= 8)) {
            return true;
        } else  {
            return false;
        }
    }


}