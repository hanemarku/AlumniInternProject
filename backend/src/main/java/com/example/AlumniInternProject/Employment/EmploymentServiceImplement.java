package com.example.AlumniInternProject.Employment;

import com.example.AlumniInternProject.Employment.Dto.EmploymentDto;
import com.example.AlumniInternProject.Employment.Dto.EmploymentGetDto;
import com.example.AlumniInternProject.entity.EmploymentHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmploymentServiceImplement implements EmploymentHistoryService{
    /*TODO: CREATE A HISTORY TIMELINE*/
    private final EmploymentHistoryRepository employmentHistoryRepository;

    private EmploymentGetDto map(EmploymentHistory eh) {
        var dto = new EmploymentGetDto();
        dto.setId(eh.getId());
        dto.setMainActivities(eh.getMainActivities());
        dto.setOccupationPosition(eh.getOccupationPosition());
        dto.setCompanyName(eh.getCompanyName());
        dto.setDepartment(eh.getDepartment());
        dto.setOngoing(eh.isOngoing());
        dto.setFromDate(eh.getFromDate());
        dto.setToDate(eh.getToDate());
        dto.setCity(eh.getCity());
        dto.setCountry(eh.getCountry());
        dto.setSkills(eh.getSkills());

        return dto;
    }
    @Override
    public EmploymentGetDto save(EmploymentDto employmentDto) {
        var ehDto = new EmploymentHistory(
                employmentDto.getMainActivities(),
                employmentDto.getOccupationPosition(),
                employmentDto.getCompanyName(),
                employmentDto.getDepartment(),
                employmentDto.isOngoing(),
                employmentDto.getFromDate(),
                employmentDto.getToDate(),
                employmentDto.getCity(),
                employmentDto.getCountry(),
                employmentDto.getSkills()
        );
      var saved = employmentHistoryRepository.save(ehDto);
      return map(saved);
    }

    @Override
    public List<EmploymentGetDto> findAll() {
        return employmentHistoryRepository.findAll()
                .stream().map(eh -> map(eh))
                .collect(Collectors.toList());
    }

    @Override
    public EmploymentGetDto findById(UUID id) {
        var ehOptional = employmentHistoryRepository.findById(id);

        if(ehOptional.isPresent()){
            return map(ehOptional.get());
        }

        throw new RuntimeException("Mendo nje zgjidhje me te lezetshme!");
    }

    @Override
    public EmploymentGetDto update(UUID id, EmploymentDto edt) {
        var eh = employmentHistoryRepository.findById(id)
                .orElseThrow(RuntimeException::new);
        /*Update te tjerat*/
        eh.setMainActivities(edt.getMainActivities());
        eh.setOccupationPosition(edt.getOccupationPosition());
        eh.setCompanyName(edt.getCompanyName());
        eh.setDepartment(edt.getDepartment());
        eh.setOngoing(edt.isOngoing());
        eh.setFromDate(edt.getFromDate());
        eh.setToDate(edt.getToDate());
        eh.setCity(edt.getCity());
        eh.setCountry(edt.getCountry());
        eh.setSkills(edt.getSkills());


        var saved = employmentHistoryRepository.save(eh);
        return map(saved);
    }
    @Override
    public void delete(UUID id) {
        employmentHistoryRepository.deleteById(id);
    }

    @Override
    public List<EmploymentHistory> findByKeyword(String keyWord) {
        List<EmploymentHistory> theList = employmentHistoryRepository.findAll();
        List<EmploymentHistory> matched = new ArrayList<>();

        for(EmploymentHistory employmentDto : theList){
            if( employmentDto.getMainActivities().toLowerCase().contains(keyWord.toLowerCase(Locale.ROOT))
                || employmentDto.getCompanyName().toLowerCase().contains(keyWord.toLowerCase(Locale.ROOT))
                || employmentDto.getDepartment().toLowerCase().contains(keyWord.toLowerCase(Locale.ROOT))
                || employmentDto.getCity().toLowerCase().contains(keyWord.toLowerCase(Locale.ROOT))
                || employmentDto.getOccupationPosition().toLowerCase().contains(keyWord.toLowerCase(Locale.ROOT))
                || employmentDto.getCountry().getName().toLowerCase().contains(keyWord.toLowerCase(Locale.ROOT))
                || employmentDto.getUser().getFirstname().toLowerCase().contains(keyWord.toLowerCase(Locale.ROOT))
                || employmentDto.getUser().getInterests().contains(keyWord.toLowerCase(Locale.ROOT))
                || employmentDto.getUser().getLastname().toLowerCase().contains(keyWord.toLowerCase(Locale.ROOT))
            ){
                matched.add(employmentDto);
            }
        }
        return matched;
    }

    @Override
    public List<EmploymentHistory> historyTimeLine() {
        List<EmploymentHistory> theEmploymentHistory = employmentHistoryRepository.findAll();
        List<EmploymentHistory> ordered = theEmploymentHistory.stream().sorted(Comparator.comparing(EmploymentHistory ::getFromDate)).
                collect(Collectors.toList());
        return ordered;
    }
}
