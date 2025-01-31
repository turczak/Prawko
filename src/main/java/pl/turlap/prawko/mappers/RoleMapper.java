package pl.turlap.prawko.mappers;

import org.springframework.stereotype.Component;
import pl.turlap.prawko.models.Role;

import java.util.List;

@Component
public class RoleMapper {

    public List<String> toRoleDtos(List<Role> roles) {
        return roles.stream()
                .map(Role::getName)
                .toList();
    }

    public Role fromDtoToRole(String roleName) {
        return new Role().withName(roleName);
    }

}