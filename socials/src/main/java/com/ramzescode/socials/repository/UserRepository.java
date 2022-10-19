package com.ramzescode.socials.repository;

import com.ramzescode.socials.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT s FROM User s WHERE s.surname = ?1")
    Optional<User> findUserBySurname(String surname);

    Optional<User> findUserByEmail(String email);

    @Query("SELECT s FROM User s WHERE s.id = ?2 AND s.surname = ?1")
    Optional<User> findUserBySurnameEqualsAndIdEquals(String surname, Long id);

    @Modifying
    @Transactional
    @Query("DELETE FROM User u WHERE u.id = ?1")
    void deleteUserById(Long id);
}
