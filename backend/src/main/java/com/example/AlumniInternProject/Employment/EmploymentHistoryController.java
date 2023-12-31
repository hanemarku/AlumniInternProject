package com.example.AlumniInternProject.Employment;

import com.example.AlumniInternProject.Employment.Dto.EmploymentDto;
import com.example.AlumniInternProject.Employment.Dto.EmploymentGetDto;
import com.example.AlumniInternProject.entity.EmploymentHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/employmentHistory")
@RequiredArgsConstructor
public class EmploymentHistoryController {
    private final EmploymentHistoryService employmentHistoryService;
    @PostMapping
    public EmploymentGetDto save(@RequestBody EmploymentDto edt){
        return employmentHistoryService.save(edt);
    }
    @GetMapping
    public List<EmploymentGetDto> getAll() {
        return employmentHistoryService.findAll();
    }
    @GetMapping("{id}")
    public EmploymentGetDto findById(@PathVariable UUID id)
    {
        return employmentHistoryService.findById(id);
    }

    @PatchMapping("{id}")
    public EmploymentGetDto update(@PathVariable UUID id, @RequestBody EmploymentDto edt) {
        return employmentHistoryService.update(id, edt);
    }
    @DeleteMapping("{id}")
    public void delete(@PathVariable UUID id)
    {
        employmentHistoryService.delete(id);
    }
    @GetMapping("/findBy/{keyWord}")
    List<EmploymentHistory> findByKeyword(@PathVariable("keyWord") String keyWord){
        return employmentHistoryService.findByKeyword(keyWord);
    }
    @GetMapping("/timeLine")
    List<EmploymentHistory> historyTimeLine(){
        return employmentHistoryService.historyTimeLine();
    }
}
