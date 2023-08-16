package com.example.AlumniInternProject.admin.settings.country;

import com.example.AlumniInternProject.entity.Country;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/countries")
@CrossOrigin(origins="http://localhost:4200")
public class CountryController {
    private final CountryRepository countryRepository;

    @GetMapping()
    public List<Country> listAll(){
        return countryRepository.findAllByOrderByNameAsc();
    }

    @PostMapping()
    public Country save(@RequestBody CountryDTO countryDto){
        Country country = new Country(countryDto.getName(), countryDto.getCode());
        Country savedCountry = countryRepository.save(country);
        return savedCountry;

    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") UUID id){
        countryRepository.deleteById(id);
    }
}
