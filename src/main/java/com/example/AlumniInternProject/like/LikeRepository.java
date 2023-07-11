package com.example.AlumniInternProject.like;

import com.example.AlumniInternProject.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LikeRepository extends JpaRepository<Like, Integer> {

}
