package pl.turlap.prawko.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.turlap.prawko.models.Role;
import pl.turlap.prawko.repositories.RoleRepository;
import pl.turlap.prawko.services.RoleService;

import java.util.List;

@RestController
@RequestMapping(path = "/roles")
public class RoleController {
    private RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/all")
    public List<Role> findALlRoles() {
        return roleService.findAll();
    }

    @PostMapping(path = "/add")
    public void addRole(@RequestBody Role role){
        roleService.addNewRole(role);
    }
}
