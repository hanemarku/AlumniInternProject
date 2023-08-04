package com.example.AlumniInternProject.education;


import com.example.AlumniInternProject.entity.EducationHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import java.util.UUID;

@CrossOrigin(origins="http://localhost:4200")

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/educations")
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
    @GetMapping("/timeLine")
    public List<EducationHistory> historyTimeLine(){
        return educationService.historyTimeLine();
    }
    @GetMapping("/findBy/{keyword}")
    public List<EducationHistory> findByKeyword(@PathVariable("keyword") String keyword){
        return educationService.findByKeyword(keyword);
    }
}
