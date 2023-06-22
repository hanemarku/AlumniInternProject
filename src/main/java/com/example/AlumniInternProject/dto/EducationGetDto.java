package com.example.AlumniInternProject.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@Getter
@Setter
public class EducationGetDto extends EducationDto{
    private UUID id;
}
