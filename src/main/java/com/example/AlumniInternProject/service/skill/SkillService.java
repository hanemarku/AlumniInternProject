package com.example.AlumniInternProject.service.skill;

import com.example.AlumniInternProject.dto.skill.SkillDto;
import com.example.AlumniInternProject.dto.skill.SkillGetDto;

import java.util.List;
import java.util.UUID;

public interface SkillService {
    SkillGetDto save (SkillDto skillDto);
    List<SkillGetDto> findAll();
    SkillGetDto findById(UUID id);
    SkillGetDto update(UUID id, SkillDto dto);
    void delete(UUID id);


}
