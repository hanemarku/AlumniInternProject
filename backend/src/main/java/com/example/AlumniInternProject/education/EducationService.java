package com.example.AlumniInternProject.education;

import com.example.AlumniInternProject.entity.EducationHistory;

import java.util.List;
import java.util.UUID;

public interface EducationService {

    EducationGetDto save(EducationDto educationDto);
    List<EducationGetDto> findAll();
    EducationGetDto findById(UUID id);
    EducationGetDto update(UUID id, EducationDto educationDto);
    void delete(UUID id);
    List<EducationHistory> findByKeyword(String keyWord);
    List<EducationHistory> historyTimeLine();

}
