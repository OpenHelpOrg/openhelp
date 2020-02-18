package com.capstone.openhelp.repositories;

import com.capstone.openhelp.models.UserEvents;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserEventRepository extends JpaRepository<UserEvents, Long> {


}
