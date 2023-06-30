package com.example.AlumniInternProject.skill;

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
                .map(entry -> mapTopSkills(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());

        return topSkills;
    }

    private SkillGetDto map(Skill skill) {
        var dto = new SkillGetDto();
        dto.setId(skill.getId());
        dto.setName(skill.getName());
        return dto;
    }

    private TopSkillGetDto mapTopSkills(String skillName, int frequency) {
        var dto = new TopSkillGetDto();
        dto.setName(skillName);
        dto.setCount(frequency);
        return dto;
    }


}
