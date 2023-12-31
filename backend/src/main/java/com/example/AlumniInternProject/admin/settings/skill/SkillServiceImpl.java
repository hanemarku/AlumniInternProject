package com.example.AlumniInternProject.admin.settings.skill;

import com.example.AlumniInternProject.entity.Skill;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class SkillServiceImpl implements SkillService{


    private final SkillRepository skillRepository;

    @Override
    public SkillGetDto save(SkillDto skillDto){
        var skill = new Skill(
                skillDto.getName()
        );

        var saved = skillRepository.save(skill);
        return map(saved);
    }

    @Override
    public List<SkillGetDto> findAll(){
        return skillRepository.findAll()
                .stream()
                .map(skill -> map(skill))
                .collect(Collectors.toList());
    }

    @Override
    public SkillGetDto findById(UUID id){
        var optional = skillRepository.findById(id);
        if (optional.isPresent()){
            return map(optional.get());
        }
        throw new RuntimeException("Skill not found");
    }

    @Override
    public SkillGetDto update(UUID id, SkillDto dto){
        var skill = skillRepository.findById(id).orElseThrow(RuntimeException::new);
        skill.setName(dto.getName());
        var saved = skillRepository.save(skill);
        return map(saved);
    }

    @Override
    public void delete(UUID id) {
        skillRepository.deleteById(id);
    }

    @Override
    public List<TopSkillGetDto> getTopSkills() {
        Map<String, Integer> frequencyMap = new HashMap<>();
        List<Skill> allSkills = skillRepository.findAll();

        for (Skill skill : allSkills) {
            frequencyMap.put(skill.getName(), frequencyMap.getOrDefault(skill.getName(), 0) + 1);
        }

        List<TopSkillGetDto> topSkills = frequencyMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .map(entry -> mapTopSkills(entry.getKey()))
                .collect(Collectors.toList());

        return topSkills;
    }

    private SkillGetDto map(Skill skill) {
        var dto = new SkillGetDto();
        dto.setId(skill.getId());
        dto.setName(skill.getName());
        return dto;
    }

    private TopSkillGetDto mapTopSkills(String skillName) {
        var dto = new TopSkillGetDto();
        dto.setName(skillName);
        return dto;
    }

    @Override
    public List<SkillGetDto> sortByName() {
        return skillRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Skill::getName, String.CASE_INSENSITIVE_ORDER))
                .map(this::map)
                .collect(Collectors.toList());
    }


}
