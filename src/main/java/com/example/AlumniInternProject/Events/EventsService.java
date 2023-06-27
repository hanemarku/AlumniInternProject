package com.example.AlumniInternProject.Events;

import com.example.AlumniInternProject.Employment.Dto.EmploymentDto;
import com.example.AlumniInternProject.Employment.Dto.EmploymentGetDto;

import java.util.List;
import java.util.UUID;

public interface EventsService {
    /*Do ishte mire te krijoje nje dto demo qe te testoje*/
    Events save(Events event);
    List<Events> findAll();
    Events findById(UUID id);
    Events update(UUID id , Events edt);
    void delete(UUID id);
}
