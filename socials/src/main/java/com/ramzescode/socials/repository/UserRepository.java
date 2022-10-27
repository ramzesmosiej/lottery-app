package com.ramzescode.socials.repository;

import com.ramzescode.socials.domain.AppUser;
import com.ramzescode.socials.rest.errors.EntityNotFoundException;
import com.ramzescode.socials.security.SecurityUtils;
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

    // login is the same as username
    default AppUser getLoggedUser() {
        String login = getCurrentUserLogin();
        Optional<AppUser> appUser = findAppUserByUsername(login);
        return appUser.orElseThrow(() -> new EntityNotFoundException("no user found with login " + login));
    }
    private String getCurrentUserLogin() {
        Optional<String> currentUserLogin = SecurityUtils.getCurrentUserLogin();
        if (currentUserLogin.isPresent()) {
            return currentUserLogin.get();
        }
        throw new EntityNotFoundException("no logged user found");
    }

    default AppUser findAppUserByIdOrElseThrow(long id) {
        return findById(id).orElseThrow(() -> new EntityNotFoundException("User", id));
    }

}
