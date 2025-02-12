package pl.turlap.prawko.mappers;

import org.springframework.stereotype.Component;
import pl.turlap.prawko.models.Role;

@Component
public class RoleMapper {

    public Role fromDtoToRole(String roleName) {
        return new Role().withName(roleName);
    }

    public String toRoleDto(Role role){
        return role.getName();
    }

}