package com.example.AlumniInternProject.post;

import com.example.AlumniInternProject.entity.IdBaseEntity;
import com.example.AlumniInternProject.entity.User;
import com.example.AlumniInternProject.like.Like;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.*;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Post extends IdBaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "like_id")
    private Like like;

    private String content;
    private Date dateOfPost;
    private Integer likesCount;
    private Integer commentsCount;
    private String keyword;
    private String category;
    private String tag;

    public void setLikeId(UUID likeId) {
        if (this.like == null) {
            this.like = new Like();
        }
        this.like.setId(likeId);
    }


    @ManyToMany
    private Set<User> sharedByUsers = new HashSet<>();

    @ManyToMany
    private Set<User> sharedWithUsers = new HashSet<>();

    // Getters and setters

    public Set<User> getSharedByUsers() {
        return sharedByUsers;
    }

    public Set<User> getSharedWithUsers() {
        return sharedWithUsers;
    }
}
