package com.example.AlumniInternProject.admin.settings.country;

import com.example.AlumniInternProject.entity.Country;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class CountryController {
    private final CountryRepository countryRepository;

    @GetMapping("/countries/list")
    public List<Country> listAll(){
        return countryRepository.findAllByOrderByNameAsc();
    }

    @PostMapping("countries/save")
    public String save(@RequestBody Country country){
        Country savedCountry = countryRepository.save(country);
        return String.valueOf(savedCountry.getId());
    }

    @GetMapping("/countries/delete/{id}")
    public void delete(@PathVariable("id") UUID id){
        countryRepository.deleteById(id);
    }
}
