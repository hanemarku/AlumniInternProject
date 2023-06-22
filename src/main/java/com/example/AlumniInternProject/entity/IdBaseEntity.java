package com.example.AlumniInternProject.entity;


import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;


import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
public abstract class IdBaseEntity{
    @Id
    private UUID id = UUID.randomUUID();
}
