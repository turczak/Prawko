package pl.turlap.prawko.services;

import pl.turlap.prawko.models.Role;

import java.util.List;

public interface RoleService {
    List<Role> findAll();
    void addNewRole(Role role);
}
