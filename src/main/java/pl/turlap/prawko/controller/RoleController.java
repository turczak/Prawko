package pl.turlap.prawko.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pl.turlap.prawko.model.Role;
import pl.turlap.prawko.service.RoleService;

import java.util.List;
import java.util.Map;

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
    public ResponseEntity<Map<String, String>> addRole(@RequestParam(name = "roleName") String roleName) {
        roleService.save(roleName);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "Role '" + roleName + "' successfully added."));
    }

    @GetMapping(path = "/{roleId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Role findRole(@PathVariable(name = "roleId") Long roleId) {
        return roleService.findById(roleId);
    }

    @DeleteMapping(path = "/delete")
    public ResponseEntity<Map<String, String>> deleteRole(@RequestParam(name = "roleId") Long roleId) {
        roleService.delete(roleId);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Role with id '" + roleId + "' successfully deleted."));
    }

}
