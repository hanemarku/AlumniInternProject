//package com.example.AlumniInternProject.entity;
//
//import jakarta.persistence.Entity;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.ManyToOne;
//import jakarta.persistence.Table;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.RequiredArgsConstructor;
//import lombok.Setter;
//
//import java.time.LocalDateTime;
//
//@Entity
//@Getter
//@Setter
//@NoArgsConstructor
//@RequiredArgsConstructor
//
//@Table(name = "reviews")
//public class Review extends IdBaseEntity{
//    private String review;
//
//    @ManyToOne
//    @JoinColumn(name = "user_id", referencedColumnName = "id")
//    private User user;
//
//    private String company;
//    private String position;
//    private String imgUrl;
//    private LocalDateTime timestamp;
//
//
//
//    public Review(String review, int rating, String user, String company, String position, String imgUrl, LocalDateTime timestamp){
//        this.review = review;
//        this.user = user;
//        this.company = company;
//        this.position = position;
//        this.imgUrl = imgUrl;
//        this.timestamp = LocalDateTime.now();
//    }
//
//}
