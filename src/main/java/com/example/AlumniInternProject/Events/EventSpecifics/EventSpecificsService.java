package com.example.AlumniInternProject.Events.EventSpecifics;

import com.example.AlumniInternProject.Events.EventSpecifics.dto.EventSpecificDto;
import com.example.AlumniInternProject.Events.EventSpecifics.dto.EventSpecificGetDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface EventSpecificsService {
    EventSpecificGetDto save(EventSpecificDto eventSpecificDto);

    EventSpecificGetDto update(EventSpecificDto eventSpecificDto, UUID id);

    List<String> findAll();

    EventSpecificGetDto findById(UUID id);

    void delete(UUID id);
}