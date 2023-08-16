package com.example.AlumniInternProject.admin.settings.city;

import com.example.AlumniInternProject.entity.City;
import com.example.AlumniInternProject.entity.Country;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface CityRepository extends CrudRepository<City, UUID> {
    public List<City> findByCountryOrderByNameAsc(Country country);
    public List<City> findAllByOrderByNameAsc();
}
