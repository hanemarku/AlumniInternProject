package com.example.AlumniInternProject.admin.settings.skill;

import java.util.List;
import java.util.UUID;

public interface SkillService {
    SkillGetDto save (SkillDto skillDto);
    List<SkillGetDto> findAll();
    SkillGetDto findById(UUID id);
    SkillGetDto update(UUID id, SkillDto dto);
    void delete(UUID id);
    List<TopSkillGetDto> getTopSkills();
    List<SkillGetDto> sortByName();


}
