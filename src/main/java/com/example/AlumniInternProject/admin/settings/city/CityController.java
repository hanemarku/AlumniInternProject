package com.example.AlumniInternProject.admin.settings.city;

import com.example.AlumniInternProject.entity.City;
import com.example.AlumniInternProject.entity.Country;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class CityController {

    private final CityRepository cityRepository;

    @GetMapping("/cities/list_by_country/{id}")
    public List<CityDto> listByCountry(@PathVariable("id") UUID countryId) {
        List<City> listCities = cityRepository.findByCountryOrderByNameAsc(new Country(countryId));
        List<CityDto> result = new ArrayList<>();

        for (City city : listCities) {
            result.add(new CityDto(city.getId(), city.getName()));
        }
        return result;
    }

    @PostMapping("/cities/save")
    public String save(@RequestBody City city) {
        City savedCity = cityRepository.save(city);
        return String.valueOf(savedCity.getId());
    }

    @GetMapping("/cities/delete/{id}")
    public void delete(@PathVariable("id") UUID id) {
        cityRepository.deleteById(id);
    }
}
