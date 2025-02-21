package pl.turlap.prawko.service;

import pl.turlap.prawko.model.Role;

import java.util.List;

public interface RoleService {

    void save(String name);

    List<Role> findAll();

    Role findById(Long id);

    Role findByName(String name);

    void delete(Long id);

}
