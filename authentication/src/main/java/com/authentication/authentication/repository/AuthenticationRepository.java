package com.authentication.authentication.repository;

import com.authentication.authentication.model.Authentication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthenticationRepository extends JpaRepository<Authentication, Integer> {
    boolean existsByPesel(String pesel);

    Optional<Authentication> findByPesel(String pesel);
}
