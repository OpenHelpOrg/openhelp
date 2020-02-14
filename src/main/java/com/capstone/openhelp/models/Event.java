package com.capstone.openhelp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String title;


    @Column(nullable = false)
    private String location;

    @Column(nullable = false, length = 500)
    private String address;

    @Column(columnDefinition = "TEXT NOT NULL")
    @JsonIgnore
    private String details;

    @Column(columnDefinition = "DATETIME NOT NULL")
    private String date_time;

    @Column(columnDefinition = "TEXT NOT NULL")
    @JsonIgnore
    private String summary;

    @Column(nullable = false)
    @JsonIgnore
    private String images;

    @Column(columnDefinition = "TEXT")
    @JsonIgnore
    private String notes;

    @Column
    @JsonIgnore
    private int vol_limit;

    //relates to User Model
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name="event_cat",
            joinColumns={@JoinColumn(name="event_id")},
            inverseJoinColumns={@JoinColumn(name="category_id")}
    )
    @JsonIgnore
    private List<Category> categories;

    @OneToMany(mappedBy = "event")
    @JsonIgnore
    private List<UserEvents> userEvents = new ArrayList<>();

    public Event() {

    }
    public Event(long id, String title, String location, String address, String details, String date, String summary, String images, String notes, int limit) {
        this.id = id;
        this.title = title;
        this.location = location;
        this.address = address;
        this.details = details;
        this.date_time = date;
        this.summary = summary;
        this.images = images;
        this.notes = notes;
        this.vol_limit = limit;

    }

    public Event(Event event,List<UserEvents> userEvents){
        this.id = event.id;
        this.title = event.title;
        this.location = event.location;
        this.address = event.address;
        this.details = event.details;
        this.date_time = event.date_time;
        this.summary = event.summary;
        this.images = event.images;
        this.notes = event.notes;
        this.vol_limit = event.vol_limit;
        this.userEvents.addAll(userEvents);
    }

    public Event(String title, String location, String address, String details, String date_time, String summary, String images, String notes, int vol_limit) {
        this.title = title;
        this.location = location;
        this.address = address;
        this.details = details;
        this.date_time = date_time;
        this.summary = summary;
        this.images = images;
        this.notes = notes;
        this.vol_limit = vol_limit;
    }
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public int getVol_limit() {
        return vol_limit;
    }

    public void setVol_limit(int vol_limit) {
        this.vol_limit = vol_limit;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<UserEvents> getUserEvents() {
        return userEvents;
    }

    public void setUserEvents(List<UserEvents> userEvents) {
        this.userEvents = userEvents;
    }


    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getLimit() {
        return vol_limit;
    }

    public void setLimit(int limit) {
        this.vol_limit = limit;
    }

//    public Event convertToJson(Event event){
//        Event bucket = new Event();
//        bucket.id = event.id;
//        bucket.title = event.title;
//        bucket.location = event.location;
//        bucket.address = event.address;
//        bucket.date_time = event.date_time;
//
//        return bucket;
//    }

}
