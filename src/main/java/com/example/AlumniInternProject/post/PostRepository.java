package com.example.AlumniInternProject.post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {

    @Query("SELECT p FROM Post p WHERE p.dateOfPost BETWEEN ?1 AND ?2")
    Optional<Post> findPostsByDateOfPost(Date dateOfPost, Date startDate, Date endDate);
}
