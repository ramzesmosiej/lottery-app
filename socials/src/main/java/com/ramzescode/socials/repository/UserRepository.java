package com.ramzescode.socials.repository;

import com.ramzescode.socials.domain.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {

    @Query("SELECT s FROM AppUser s WHERE s.username = ?1")
    Optional<AppUser> findUserBySurname(String username);

    Optional<AppUser> findUserByEmail(String email);

    @Query("SELECT s FROM AppUser s WHERE s.id = ?2 AND s.username = ?1")
    Optional<AppUser> findUserByUsernameEqualsAndIdEquals(String username, Long id);

    @Modifying
    @Transactional
    @Query("DELETE FROM AppUser u WHERE u.id = ?1")
    void deleteUserById(Long id);

    Optional<AppUser> findAppUserByUsername(String username);
}
