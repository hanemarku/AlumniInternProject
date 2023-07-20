package com.example.AlumniInternProject.connectionRequest;

import java.util.UUID;

public class ConnectionRequestGetDto {
    private UUID user_id;
    private String firstName;
    private String lastName;
    private String email;

    public ConnectionRequestGetDto(UUID user_id, String firstName, String lastName, String email) {
        this.user_id = user_id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }


}
