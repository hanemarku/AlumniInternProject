package com.example.AlumniInternProject.admin.settings.role;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/roles")
public class RoleController {

    private final RoleService roleService;

    @PostMapping
    public RoleGetDto save(@RequestBody RoleDto roleDto){
        return roleService.save(roleDto);
    }

    @GetMapping
    public List<RoleGetDto> findAll(){
        return roleService.findAll();
    }

    @GetMapping("{id}")
    public RoleGetDto findById(@PathVariable UUID id){
        return roleService.findById(id);
    }

    @PatchMapping("{id}")
    public RoleGetDto update(@PathVariable UUID id, @RequestBody RoleDto roleDto){
        return roleService.update(id, roleDto);
    }

    @DeleteMapping("{id}")
    void delete(UUID id){
        roleService.delete(id);
    }
}
