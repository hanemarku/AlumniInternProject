package com.example.AlumniInternProject.role;

import com.example.AlumniInternProject.entity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleImpl implements RoleService {

    final private RoleRepository roleRepository;

    @Override
    public RoleGetDto save(RoleDto roleDto) {
        var role = new Role(
                roleDto.getName(),
                roleDto.getDescription()
        );
        var saveRoe = roleRepository.save(role);
        return map(saveRoe);
    }

    @Override
    public List<RoleGetDto> findAll() {
        return roleRepository.findAll()
                .stream()
                .map(role -> map(role))
                .collect(Collectors.toList());
    }

    @Override
    public RoleGetDto findById(UUID id) {
        var optional = roleRepository.findById(id);
        if (optional.isPresent()){
            return map(optional.get());
        } throw new RuntimeException("Roli i cili kerkoni nuk gjendet!");
    }

    @Override
    public RoleGetDto update(UUID id, RoleDto roleDto) {
        var optional = roleRepository.findById(id).orElseThrow(RuntimeException::new);
        optional.setName(roleDto.getName());
        optional.setDescription(roleDto.getDescription());

        var saved_role = roleRepository.save(optional);
        return map(saved_role);
    }

    @Override
    public void delete(UUID id) {
        roleRepository.deleteById(id);
    }

    private RoleGetDto map(Role role){
        var dto = new RoleGetDto();
        dto.setName(role.getName());
        dto.setDescription(role.getDescription());
        dto.setId(role.getId());
        return dto;
    }
}
