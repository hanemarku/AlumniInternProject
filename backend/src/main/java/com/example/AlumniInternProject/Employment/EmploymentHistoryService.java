package com.example.AlumniInternProject.Employment;

import com.example.AlumniInternProject.Employment.Dto.EmploymentDto;
import com.example.AlumniInternProject.Employment.Dto.EmploymentGetDto;

import java.util.List;
import java.util.UUID;

public interface EmploymentHistoryService {
    EmploymentGetDto save(EmploymentDto employmentDto);
    List<EmploymentGetDto> findAll();
    EmploymentGetDto findById(UUID id);
    EmploymentGetDto update(UUID id , EmploymentDto edt);
    void delete(UUID id);
}
