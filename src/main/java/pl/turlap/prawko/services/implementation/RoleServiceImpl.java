package pl.turlap.prawko.services.implementation;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.turlap.prawko.dto.RoleDto;
import pl.turlap.prawko.mappers.RoleMapper;
import pl.turlap.prawko.models.Role;
import pl.turlap.prawko.repositories.RoleRepository;
import pl.turlap.prawko.services.RoleService;

import java.util.List;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Override
    public List<RoleDto> findAll() {
        List<Role> roles = roleRepository.findAll();
        return roleMapper.toRoleDto(roles);
    }

    @Override
    public ResponseEntity<String> addNewRole(RoleDto roleDto) {
        Role byName = roleRepository.findByName(roleDto.getName());
        if (byName == null) {
            Role newRole = Role.builder()
                    .name(roleDto.getName())
                    .build();
            roleRepository.save(newRole);
            return ResponseEntity.ok("New role added.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Role already existed.");
        }
    }

    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }

    @Override
    public List<Role> roleForNewUser() {
        return List.of(roleRepository.findByName("USER"));
    }
}
