package com.example.AlumniInternProject.service.role;

import com.example.AlumniInternProject.dto.RoleDto;
import com.example.AlumniInternProject.dto.RoleGetDto;

import java.util.List;
import java.util.UUID;

public interface RoleService {

    RoleGetDto save(RoleDto roleDto);

    List<RoleGetDto> findAll();

    RoleGetDto findById(UUID id);

    RoleGetDto update(UUID id, RoleDto roleDto);

    void delete(UUID id);
}
