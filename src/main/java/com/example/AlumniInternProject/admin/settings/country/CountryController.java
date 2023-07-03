package com.example.AlumniInternProject.admin.settings.country;

import com.example.AlumniInternProject.entity.Country;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/countries")
public class CountryController {
    private final CountryRepository countryRepository;

    @GetMapping("list")
    public List<Country> listAll(){
        return countryRepository.findAllByOrderByNameAsc();
    }

    @PostMapping("save")
    public String save(@RequestBody Country country){
        Country savedCountry = countryRepository.save(country);
        return String.valueOf(savedCountry.getId());
    }

    @GetMapping("delete/{id}")
    public void delete(@PathVariable("id") UUID id){
        countryRepository.deleteById(id);
    }
}
