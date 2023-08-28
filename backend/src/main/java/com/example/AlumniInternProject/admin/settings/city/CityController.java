package com.example.AlumniInternProject.admin.settings.city;

import com.example.AlumniInternProject.admin.settings.country.CountryRepository;
import com.example.AlumniInternProject.entity.City;
import com.example.AlumniInternProject.entity.Country;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cities")
//@CrossOrigin("http://localhost:4200/events")
public class CityController {

    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;

    @GetMapping("")
    public List<City> list() {
        return cityRepository.findAllByOrderByNameAsc();
    }

    @PostMapping()
    public City save(@RequestBody CityDto cityDto) {
        Optional<Country> optionalCountry = countryRepository.findById(cityDto.getCountryId());

        if (optionalCountry.isEmpty()) {
            return null;
        }

        Country country = optionalCountry.get();
        City city = new City(cityDto.getName(), country);
        City savedCity = cityRepository.save(city);
        return savedCity;
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") UUID id) {
        cityRepository.deleteById(id);
    }

    @GetMapping("/countries/{id}")
    public List<City> findByCountryIdOrderByNameAsc(@PathVariable("id") UUID id) {
        return cityRepository.findByCountryIdOrderByNameAsc(id);
    }
    @GetMapping("/{id}")
    public City findCityById(@PathVariable("id") UUID id){
        return cityRepository.findCityByIdIs(id);
    }
}