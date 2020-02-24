package com.capstone.openhelp.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @Column(columnDefinition = "bit(1) DEFAULT 0", nullable = false)
    private boolean is_org;

    @Column(columnDefinition="TEXT")
    private String image;

    private String phone;


    private String about;

    private String address;

    private String website;

    @ManyToMany
    @JoinTable(
            name = "user_org",
            joinColumns={@JoinColumn(name = "user_id")},
            inverseJoinColumns={@JoinColumn(name = "org_id")}
            )
    @JsonIgnore
    private List<User> users;

    @ManyToMany(mappedBy = "users")
    @JsonIgnore
    private List<User> organizations;

    @OneToMany(mappedBy = "user")
    @JsonBackReference
    private List<UserEvents> userEvents;

    //this is for email verification
    @Column
    private boolean enabled;



    public User() {
        this.enabled = false;
    }

    public User(long id, String name, String username, String email, String password, boolean is_org, String phone, String image, String about, String address, String website, boolean enabled) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.is_org = is_org;
        this.phone = phone;
        this.image = image;
        this.about = about;
        this.address = address;
        this.website = website;
        this.enabled = enabled;
    }

    public User(String name, String username, String email, String password, boolean is_org, String phone, String image, String about, String address, String website, boolean enabled) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.is_org = is_org;
        this.phone = phone;
        this.image = image;
        this.about = about;
        this.address = address;
        this.website = website;
        this.enabled = enabled;
    }


    //pw config. needs review
    public User(User copy) {
        id = copy.id; // This line is SUPER important! Many things won't work if it's absent
        email = copy.email;
        username = copy.username;
        name = copy.name;
        password = copy.password;
        about = copy.about;
        address = copy.address;
        image = copy.image;
        is_org = copy.is_org;
        phone = copy.phone;
        website =copy.website;
        enabled = copy.enabled;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isIs_org() {
        return is_org;
    }

    public void setIs_org(boolean is_org) {
        this.is_org = is_org;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAbout() {
        return about;
    }

    public List<UserEvents> getUserEvents() {
        return userEvents;
    }

    public void setUserEvents(List<UserEvents> userEvents) {
        this.userEvents = userEvents;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<User> getOrganizations() {
        return organizations;
    }

    public void setOrganizations(List<User> organizations) {
        this.organizations = organizations;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
