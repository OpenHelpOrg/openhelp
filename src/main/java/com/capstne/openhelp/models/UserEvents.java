package com.capstone.openhelp.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user_event")
public class UserEvents implements Serializable {
    @Id
    @ManyToOne
    @JoinColumn
    private User user;

    @Id
    @ManyToOne
    @JoinColumn
    private Event event;

    @Column(nullable = false)
    private boolean is_creator;

    @Column(columnDefinition = "TEXT")
    private String story;

    public UserEvents() {
    }

    public UserEvents(User user, Event event, boolean is_creator, String story) {
        this.user = user;
        this.event = event;
        this.is_creator = is_creator;
        this.story = story;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public boolean isIs_creator() {
        return is_creator;
    }

    public void setIs_creator(boolean is_creator) {
        this.is_creator = is_creator;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

}
