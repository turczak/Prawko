package pl.turlap.prawko.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.turlap.prawko.dto.RoleDto;
import pl.turlap.prawko.services.RoleService;

import java.util.List;

@Controller
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping(path = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<RoleDto> findALlRoles() {
        return roleService.findAll();
    }

    @PostMapping(path = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addNewRole(@RequestBody RoleDto roleDto) {
        return roleService.addNewRole(roleDto);
    }

}
