package com.example.AlumniInternProject.Employment;

import com.example.AlumniInternProject.Employment.Dto.EmploymentDto;
import com.example.AlumniInternProject.Employment.Dto.EmploymentGetDto;
import com.example.AlumniInternProject.Events.EventSpecifics.dto.EventSpecificGetDto;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface EmploymentHistoryService {
    EmploymentGetDto save(EmploymentDto employmentDto);
    List<EmploymentGetDto> findAll();
    EmploymentGetDto findById(UUID id);
    EmploymentGetDto update(UUID id , EmploymentDto edt);
    void delete(UUID id);
    Set<EmploymentGetDto> findByKeyword(String keyWord, Set<EmploymentGetDto> eventDto);
}
