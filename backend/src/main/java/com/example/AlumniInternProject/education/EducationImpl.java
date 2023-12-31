package com.example.AlumniInternProject.education;

import com.example.AlumniInternProject.entity.EducationHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EducationImpl implements EducationService {

    private final EducationRepository educationRepository;

    public EducationGetDto mapEducationHistoryToDto(EducationDto educationHistory) {
        var dto = new EducationGetDto();
        dto.setInstitutionName(educationHistory.getInstitutionName());
        dto.setFieldOfQualification(educationHistory.getFieldOfQualification());
        dto.setFieldOfStudy(educationHistory.getFieldOfStudy());
        dto.setStartDate(educationHistory.getStartDate());
        dto.setEndDate(educationHistory.getEndDate());
        dto.setFinalGrade(educationHistory.getFinalGrade());
        dto.setWebsite(educationHistory.getWebsite());
        dto.setCity(educationHistory.getCity());
        dto.setCountry(educationHistory.getCountry()); // Assuming `Country` has a `getName()` method.
        return dto;
    }
    @Override
    public EducationGetDto save(EducationDto educationDto) {
        var educationHistory = new EducationHistory(
                educationDto.getInstitutionName(),
                educationDto.getFieldOfQualification(),
                educationDto.getFieldOfStudy(),
                educationDto.getStartDate(),
                educationDto.getEndDate(),
                educationDto.getFinalGrade(),
                educationDto.getWebsite(),
                educationDto.getCity(),
                educationDto.getCountry(),
                educationDto.getUser()
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
        optional.setCity(educationDto.getCity());
        optional.setCountry(educationDto.getCountry());

        var saved_ed = educationRepository.save(optional);

        return map(saved_ed);
    }

    @Override
    public void delete(UUID id) {
        educationRepository.deleteById(id);
    }

    @Override
    public List<EducationHistory> findByKeyword(String keyWord) {
        if(educationRepository.findAll().isEmpty()){
            throw new IllegalArgumentException("There is no education history made!");
        }
        List<EducationHistory> theEdHistory = educationRepository.findAll();
        List<EducationHistory> matched = new ArrayList<>(theEdHistory.size());

        for (EducationHistory ed : theEdHistory){
            if(
                    ed.getCity().toLowerCase().contains(keyWord.toLowerCase(Locale.ROOT))
                    || ed.getCountry().getName().toLowerCase().contains(keyWord.toLowerCase(Locale.ROOT))
                    || ed.getUser().getFirstname().toLowerCase().contains(keyWord.toLowerCase(Locale.ROOT))
                    || ed.getUser().getLastname().toLowerCase().contains(keyWord.toLowerCase(Locale.ROOT))
                    || ed.getFieldOfQualification().toLowerCase().contains(keyWord.toLowerCase(Locale.ROOT))
                    || ed.getFieldOfStudy().toLowerCase().contains(keyWord.toLowerCase(Locale.ROOT))
                    || ed.getInstitutionName().toLowerCase().contains(keyWord.toLowerCase(Locale.ROOT))
            ){
                matched.add(ed);
            }
        }
        return matched;
    }

    @Override
    public List<EducationHistory> historyTimeLine() {
        List<EducationHistory> theEducationHistory = educationRepository.findAll();
        List<EducationHistory> ordered = theEducationHistory.stream().sorted(Comparator.comparing(EducationHistory ::getStartDate)).
                collect(Collectors.toList());
        return ordered;
    }

    private EducationGetDto map(EducationHistory educationHistory) {
        var dto = new EducationGetDto();
        dto.setId(educationHistory.getId());
        dto.setInstitutionName(educationHistory.getInstitutionName());
        dto.setFieldOfQualification(educationHistory.getFieldOfQualification());
        dto.setStartDate(educationHistory.getStartDate());
        dto.setEndDate(educationHistory.getEndDate());
        dto.setFinalGrade(educationHistory.getFinalGrade());
        dto.setWebsite(educationHistory.getWebsite());
        dto.setCity(educationHistory.getCity());
        dto.setCountry(educationHistory.getCountry());
        dto.setUser(educationHistory.getUser());
        return dto;
    }
}
