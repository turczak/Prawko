package pl.turlap.prawko.services.implementation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.turlap.prawko.exceptions.RoleAlreadyExistsException;
import pl.turlap.prawko.exceptions.RoleNotFoundException;
import pl.turlap.prawko.mappers.RoleMapper;
import pl.turlap.prawko.models.Role;
import pl.turlap.prawko.models.User;
import pl.turlap.prawko.repositories.RoleRepository;
import pl.turlap.prawko.services.RoleService;

import java.util.List;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role findById(Long id) {
        return roleRepository.findById(id).orElseThrow(() -> new RoleNotFoundException("Role with id '" + id + "' not found."));
    }

    @Override
    public void save(String roleName) {
        if (roleRepository.findByName(roleName).isPresent()) {
            throw new RoleAlreadyExistsException("Role '" + roleName + "' already exists.");
        }
        roleRepository.save(roleMapper.fromDtoToRole(roleName));
    }

    @Override
    public void delete(Long id) {
        Role role = findById(id);
        for (User user : role.getUsers()) {
            user.getRoles().remove(role);
        }
        roleRepository.delete(role);
    }

    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name).orElseThrow(() -> new RoleNotFoundException("Role with name '" + name + "' not found."));
    }

}
