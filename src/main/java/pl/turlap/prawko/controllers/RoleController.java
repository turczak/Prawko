package pl.turlap.prawko.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.turlap.prawko.models.Role;
import pl.turlap.prawko.services.RoleService;

import java.util.List;

@RestController
@RequestMapping("/roles")
@AllArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping(path = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Role> findALlRoles() {
        return roleService.findAll();
    }

    @PostMapping(path = "/add")
    public ResponseEntity<String> addRole(@RequestParam(name = "roleName") String roleName) {
        roleService.save(roleName);
        return ResponseEntity.status(HttpStatus.CREATED).body("Role '" + roleName + "' successfully added.");
    }

    @DeleteMapping(path = "/delete")
    public ResponseEntity<String> deleteRole(@RequestParam(name = "roleId") Long roleId) {
        roleService.delete(roleId);
        return ResponseEntity.status(HttpStatus.OK).body("Role with id '" + roleId + "' successfully deleted.");
    }

}
