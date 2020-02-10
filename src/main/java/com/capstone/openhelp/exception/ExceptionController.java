package com.capstone.openhelp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class ExceptionController {

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public class PostException extends Exception {

        public PostException(){
            super("User Not Found");
        }


    }
}
