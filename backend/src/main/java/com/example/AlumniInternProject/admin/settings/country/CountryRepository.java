package com.example.AlumniInternProject.admin.settings.country;

import com.example.AlumniInternProject.entity.Country;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface CountryRepository extends CrudRepository<Country, UUID> {
    List<Country> findAllByOrderByNameAsc();
}
