package com.example.AlumniInternProject.like;

import com.example.AlumniInternProject.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


@Repository
public interface LikeRepository extends JpaRepository<Like, UUID> {


    //@Query("select s from userLike  where s.post = ?1")
    Optional<Like> findLikeByPost(Post post);
}
