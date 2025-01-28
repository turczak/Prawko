package pl.turlap.prawko.mappers;

import org.springframework.stereotype.Component;
import pl.turlap.prawko.dto.RoleDto;
import pl.turlap.prawko.models.Role;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RoleMapper {

    public List<RoleDto> toRoleDto(List<Role> roles) {
        return roles.stream()
                .map(role -> RoleDto.builder()
                        .name(role.getName())
                        .build())
                .collect(Collectors.toList());
    }

}
