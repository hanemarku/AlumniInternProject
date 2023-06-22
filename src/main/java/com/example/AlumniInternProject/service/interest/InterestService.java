package com.example.AlumniInternProject.service.interest;

import com.example.AlumniInternProject.dto.interest.InterestDto;
import com.example.AlumniInternProject.dto.interest.InterestGetDto;

import java.util.List;
import java.util.UUID;

public interface InterestService {
    InterestGetDto save (InterestDto interestDto);
    List<InterestGetDto> findAll();
    InterestGetDto findById(UUID id);
    InterestGetDto update(UUID id, InterestDto dto);
    void delete (UUID id);
}
