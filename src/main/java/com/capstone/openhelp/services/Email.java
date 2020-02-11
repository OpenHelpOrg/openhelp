package com.capstone.openhelp.services;

import com.sun.istack.NotNull;

import javax.validation.constraints.Size;

public class Email {
    @NotNull
    private String name;

    @NotNull
    private String email;

    @NotNull
    private String subject;

    @NotNull
    private String url;

    @NotNull
    @Size(min = 10)
    private String messageText;


    public String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(@NotNull String email) {
        this.email = email;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}