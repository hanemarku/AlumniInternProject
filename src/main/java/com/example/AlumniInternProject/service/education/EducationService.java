package com.example.AlumniInternProject.service.education;

import com.example.AlumniInternProject.dto.EducationDto;
import com.example.AlumniInternProject.dto.EducationGetDto;

import java.util.List;
import java.util.UUID;

public interface EducationService {

    EducationGetDto save(EducationDto educationDto);

    List<EducationGetDto> findAll();

    EducationGetDto findById(UUID id);

    EducationGetDto update(UUID id, EducationDto educationDto);

    void delete(UUID id);
}
