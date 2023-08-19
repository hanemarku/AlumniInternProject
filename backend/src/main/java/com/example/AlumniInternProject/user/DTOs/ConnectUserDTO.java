package com.example.AlumniInternProject.user.DTOs;

import com.example.AlumniInternProject.entity.IdBaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ConnectUserDTO  extends IdBaseEntity {
    private String email;
    private String firstName;
    private String lastName;
    private String profilePicture;

    public ConnectUserDTO(UUID id, String email, String firstName, String lastName, String profilePicture) {
        super(id);
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.profilePicture = profilePicture;
    }

    public ConnectUserDTO() {
    }
}
