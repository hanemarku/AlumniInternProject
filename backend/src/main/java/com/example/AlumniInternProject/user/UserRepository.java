package com.example.AlumniInternProject.user;

import com.example.AlumniInternProject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, UUID> {
    User findUserByEmail(String email);
    User getUserByEmail(String email);
    int countUserById(UUID id);
    @Query("UPDATE User u SET u.enabled = ?2 WHERE u.id = ?1")
    @Modifying
    void updateEnabledStatus(UUID id, boolean enabled);
    User findUserEventsByConfirmationToken(String confirmationToken);

    @Query("select u from User u where u.confirmationToken = ?1")
    User findByToken(String token);
    Optional<User> findByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.verified = false WHERE u.email = ?1")
    int confirmUser(String email);

}
