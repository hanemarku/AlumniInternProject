package com.example.AlumniInternProject.education;

import com.example.AlumniInternProject.education.EducationDto;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@Getter
@Setter
public class EducationGetDto extends EducationDto {
    private UUID id;
}
