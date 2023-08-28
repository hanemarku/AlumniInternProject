package com.example.AlumniInternProject.admin.settings.city;

import com.example.AlumniInternProject.entity.City;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface CityRepository extends CrudRepository<City, UUID> {

//    @Query("SELECT c FROM City c WHERE c.country.id = :countryId ORDER BY c.name ASC")
//    List<City> findByCountryIdOrderByNameAsc(@Param("countryId") UUID countryId);
    List<City> findByCountryIdOrderByNameAsc(UUID countryId);
    List<City> findAllByOrderByNameAsc();
    @Query("SELECT c FROM City c WHERE c.id = :uuid")
    City findCityByIdIs(UUID uuid);

}
