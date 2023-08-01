package com.example.AlumniInternProject.admin.settings.interest;

import java.util.List;
import java.util.UUID;

public interface InterestService {
    InterestGetDto save (InterestDto interestDto);
    List<InterestGetDto> findAll();
    InterestGetDto findById(UUID id);
    InterestGetDto update(UUID id, InterestDto dto);
    void delete (UUID id);
    List<InterestGetDto> sortByName();
}
