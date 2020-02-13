package com.capstone.openhelp.repositories;


import com.capstone.openhelp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email); //this needs to be first for the Security Configuration to work
    User findById(long id);
    User getByName(String name);
    User findByName(String name);
}

