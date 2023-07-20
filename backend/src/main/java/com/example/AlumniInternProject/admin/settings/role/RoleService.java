package com.example.AlumniInternProject.admin.settings.role;

import java.util.List;
import java.util.UUID;

public interface RoleService {

    RoleGetDto save(RoleDto roleDto);

    List<RoleGetDto> findAll();

    RoleGetDto findById(UUID id);

    RoleGetDto update(UUID id, RoleDto roleDto);

    void delete(UUID id);
}
