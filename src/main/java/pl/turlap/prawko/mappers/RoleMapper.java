package pl.turlap.prawko.mappers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.turlap.prawko.dto.RoleDto;
import pl.turlap.prawko.models.Role;
import pl.turlap.prawko.repositories.RoleRepository;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class RoleMapper {

    private RoleRepository roleRepository;

    public List<RoleDto> toRoleDto(List<Role> roles) {
        return roles.stream()
                .map(role -> RoleDto.builder()
                        .name(role.getName())
                        .build())
                .collect(Collectors.toList());
    }

    public List<Role> roleForNewUser() {
        return List.of(roleRepository.findByName("USER"));
    }

}
