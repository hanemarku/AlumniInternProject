package com.example.AlumniInternProject.Employment;

import com.example.AlumniInternProject.Employment.Dto.EmploymentDto;
import com.example.AlumniInternProject.Employment.Dto.EmploymentGetDto;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmploymentServiceImplement implements EmploymentHistoryService{

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
                employmentDto.getToDate()
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
        eh.setDepartment(edt.getDepartment());
        //eh.setOngoing(edt.isOngoing());
        var saved = employmentHistoryRepository.save(eh);
        return map(saved);
    }

    @Override
    public void delete(UUID id) {
        employmentHistoryRepository.deleteById(id);
    }
}
