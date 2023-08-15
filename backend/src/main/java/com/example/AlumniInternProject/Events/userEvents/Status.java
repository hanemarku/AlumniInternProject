package com.example.AlumniInternProject.Events.userEvents;

public enum Status {
    /*When the request is sent but is still not confirmed*/
    PENDING ,
    /*The request is confirmed on time*/
    CONFIRMED,
    /*The request is no longer valid*/
    EXPIRED
}
