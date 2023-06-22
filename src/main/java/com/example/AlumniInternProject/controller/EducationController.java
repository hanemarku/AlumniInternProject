package com.example.AlumniInternProject.controller;


import com.example.AlumniInternProject.dto.EducationDto;
import com.example.AlumniInternProject.dto.EducationGetDto;
import com.example.AlumniInternProject.service.education.EducationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/educations")
public class EducationController {
    private final EducationService educationService;

    @PostMapping
    public EducationGetDto save(@RequestBody EducationDto educationDto){
        return educationService.save(educationDto);
    }

    @GetMapping
    public List<EducationGetDto> findAll(){
        return educationService.findAll();
    }

    @GetMapping("{id}")
    public EducationGetDto findById(@PathVariable UUID id){
        return educationService.findById(id);
    }

    @PatchMapping("{id}")
    public EducationGetDto update(@PathVariable UUID id, @RequestBody EducationDto educationDto){
        return educationService.update(id, educationDto);
    }

    @DeleteMapping("{id}")
    void delete(@PathVariable UUID id){
        educationService.delete(id);
    }
}
