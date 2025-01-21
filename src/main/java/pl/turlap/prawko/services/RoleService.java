package pl.turlap.prawko.services;

import org.springframework.http.ResponseEntity;
import pl.turlap.prawko.dto.RoleDto;
import pl.turlap.prawko.models.Role;

import java.util.List;

public interface RoleService {

    List<RoleDto> findAll();

    ResponseEntity<String> save(RoleDto roleDto);

    ResponseEntity<String> addNewRole(RoleDto roleDto);

}
