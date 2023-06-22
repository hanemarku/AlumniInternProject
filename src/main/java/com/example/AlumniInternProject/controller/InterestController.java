package com.example.AlumniInternProject.controller;

import com.example.AlumniInternProject.dto.interest.InterestDto;
import com.example.AlumniInternProject.dto.interest.InterestGetDto;
import com.example.AlumniInternProject.service.interest.InterestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/v1/interests")
@RequiredArgsConstructor

public class InterestController  {
    private final InterestService interestService;

    @PostMapping
    public InterestGetDto save(@RequestBody InterestDto dto) {
        return interestService.save(dto);
    }

    @GetMapping
    public List<InterestGetDto> getAll(){
        return interestService.findAll();
    }

    @GetMapping("{id}")
    public InterestGetDto findById(@PathVariable UUID id){
        return interestService.findById(id);
    }

    @PatchMapping("{id}")
    public InterestGetDto update(@PathVariable UUID id, @RequestBody InterestDto dto){
        return interestService.update(id, dto);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable UUID id){
        interestService.delete(id);
    }

}
