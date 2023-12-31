package com.example.AlumniInternProject.user;

import com.example.AlumniInternProject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;
import java.util.List;
import java.util.UUID;

@Repository
@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, UUID> {
    User findUserByEmail(String email);
    int countUserById(UUID id);
    @Query("UPDATE User u SET u.enabled = ?2 WHERE u.id = ?1")
    @Modifying
    void updateEnabledStatus(UUID id, boolean enabled);

    User findUserById(UUID id);

    User findByVerificationCode(String verificationCode);

    User getUserByFirstname(String firstName);

    @Query("SELECT u FROM User u WHERE u.firstname LIKE %:keyword% OR u.lastname LIKE %:keyword%")
    List<User> findByKeyword(@Param("keyword") String keyword);

}
