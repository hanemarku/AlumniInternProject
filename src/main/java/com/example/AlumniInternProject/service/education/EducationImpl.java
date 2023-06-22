package com.example.AlumniInternProject.service.education;

import com.example.AlumniInternProject.dto.EducationDto;
import com.example.AlumniInternProject.dto.EducationGetDto;
import com.example.AlumniInternProject.entity.EducationHistory;
import com.example.AlumniInternProject.entity.repository.EducationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EducationImpl implements EducationService{

    private final EducationRepository educationRepository;

    @Override
    public EducationGetDto save(EducationDto educationDto) {
        var educationHistory = new EducationHistory(
                educationDto.getInstitutionName(),
                educationDto.getFieldOfQualification(),
                educationDto.getFieldOfStudy(),
                educationDto.getStartDate(),
                educationDto.getEndDate(),
                educationDto.getFinalGrade(),
                educationDto.getWebsite()
        );
        var saved = educationRepository.save(educationHistory);
        return map(saved);
    }

    @Override
    public List<EducationGetDto> findAll() {
        return educationRepository.findAll()
                .stream()
                .map(educationHistory -> map(educationHistory))
                .collect(Collectors.toList());
    }

    @Override
    public EducationGetDto findById(UUID id) {
        var optional = educationRepository.findById(id);
        if (optional.isPresent()){
            return map(optional.get());
        } throw new RuntimeException("Your Education History can not be found!");

    }

    @Override
    public EducationGetDto update(UUID id, EducationDto educationDto) {
        var optional = educationRepository.findById(id).orElseThrow(RuntimeException::new);
        optional.setInstitutionName(educationDto.getInstitutionName());
        optional.setFieldOfQualification(educationDto.getFieldOfQualification());
        optional.setFieldOfStudy(educationDto.getFieldOfStudy());
        optional.setStartDate(educationDto.getStartDate());
        optional.setEndDate(educationDto.getEndDate());
        optional.setFinalGrade(educationDto.getFinalGrade());
        optional.setWebsite(educationDto.getWebsite());

        var saved_ed = educationRepository.save(optional);

        return map(saved_ed);
    }

    @Override
    public void delete(UUID id) {
        educationRepository.deleteById(id);

    }

    private EducationGetDto map(EducationHistory educationHistory) {
        var dto = new EducationGetDto();
        dto.setInstitutionName(educationHistory.getInstitutionName());
        dto.setFieldOfQualification(educationHistory.getFieldOfQualification());
        dto.setStartDate(educationHistory.getStartDate());
        dto.setEndDate(educationHistory.getEndDate());
        dto.setFinalGrade(educationHistory.getFinalGrade());
        dto.setWebsite(educationHistory.getWebsite());
        return dto;
    }
}
