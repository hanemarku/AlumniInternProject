package com.example.AlumniInternProject.dto;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

import static jakarta.persistence.GenerationType.IDENTITY;

@Setter
@Getter
public class RoleGetDto extends RoleDto{
    @Id
    private UUID id = UUID.randomUUID();
}
