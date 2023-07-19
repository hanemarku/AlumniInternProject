package com.example.AlumniInternProject.admin.settings.city;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CityDto {
    private UUID id;
    private String name;

    public CityDto() {

    }

    public CityDto(UUID id, String name) {
        this.id = id;
        this.name = name;
    }
}
