package com.example.AlumniInternProject.admin.settings.interest;
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

    @GetMapping("/sort-name")
    public List<InterestGetDto> sortByName(){
        return interestService.sortByName();
    }

}
