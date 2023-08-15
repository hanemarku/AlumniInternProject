package com.example.AlumniInternProject.admin.settings.skill;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins="http://localhost:4200")
@RestController
@RequestMapping(path = "api/v1/skills")
@RequiredArgsConstructor

public class SkillController {
    private final SkillService skillService;

    @PostMapping
    public SkillGetDto save(@RequestBody SkillDto dto) {
        return skillService.save(dto);
    }

    @GetMapping
    public List<SkillGetDto> getAll(){
        return skillService.findAll();
    }

    @GetMapping("{id}")
    public SkillGetDto findById(@PathVariable UUID id){
        return skillService.findById(id);
    }

    @PatchMapping("{id}")
    public SkillGetDto update(@PathVariable UUID id, @RequestBody SkillDto dto){
        return skillService.update(id, dto);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable UUID id){
        skillService.delete(id);
    }

    @GetMapping("/top-skills")
    public List<TopSkillGetDto> getTopSkills(){
        return skillService.getTopSkills();
    }

    @GetMapping("/sort-name")
    public List<SkillGetDto> sortByName(){
        return skillService.sortByName();
    }
}
