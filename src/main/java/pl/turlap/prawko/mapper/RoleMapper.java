package pl.turlap.prawko.mapper;

import org.springframework.stereotype.Component;
import pl.turlap.prawko.model.Role;

@Component
public class RoleMapper {

    public Role fromDto(String roleName) {
        return new Role().withName(roleName);
    }

    public String toDto(Role role){
        return role.getName();
    }

}
