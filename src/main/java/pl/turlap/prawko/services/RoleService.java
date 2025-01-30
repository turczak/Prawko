package pl.turlap.prawko.services;

import pl.turlap.prawko.models.Role;

import java.util.List;

public interface RoleService {

    void save(String name);

    List<Role> findAll();

    Role findById(Long id);

    Role findByName(String name);

    void delete(Long id);

}