package com.capstone.openhelp.repositories;

import com.capstone.openhelp.models.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationTokenRespository extends JpaRepository<VerificationToken, Long> {
    VerificationToken findByConfirmationToken(String confirmationToken);
}
