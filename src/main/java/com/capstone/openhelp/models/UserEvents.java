package com.capstone.openhelp.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user_event")
public class UserEvents implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn
    private User user;

    @ManyToOne
    @JoinColumn
    private Event event;

    @Column(nullable = false)
    private boolean is_creator;

    @Column(columnDefinition = "TEXT")
    private String story;

    @Column
    private boolean attended;

    public UserEvents() {
        this.attended = false;
    }

    public UserEvents(long id, User user, Event event, boolean is_creator, String story) {
        this.id = id;
        this.user = user;
        this.event = event;
        this.is_creator = is_creator;
        this.story = story;
    }

    public UserEvents(User user, Event event, boolean is_creator, String story) {
        this.user = user;
        this.event = event;
        this.is_creator = is_creator;
        this.story = story;
    }

    public UserEvents(User user, Event event, boolean is_creator){
        this.user = user;
        this.event = event;
        this.is_creator = is_creator;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public boolean isAttended() {
        return attended;
    }

    public void setAttended(boolean attended) {
        this.attended = attended;
    }
}
