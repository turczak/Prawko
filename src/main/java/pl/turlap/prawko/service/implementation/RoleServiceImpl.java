package pl.turlap.prawko.service.implementation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.turlap.prawko.exception.CustomAlreadyExistsException;
import pl.turlap.prawko.exception.CustomNotFoundException;
import pl.turlap.prawko.mapper.RoleMapper;
import pl.turlap.prawko.model.Role;
import pl.turlap.prawko.model.User;
import pl.turlap.prawko.repository.RoleRepository;
import pl.turlap.prawko.service.RoleService;

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
    public Role findById(Long roleId) {
        return roleRepository.findById(roleId).orElseThrow(() -> new CustomNotFoundException("roleId", "Role with id '" + roleId + "' not found."));
    }

    @Override
    public void save(String roleName) {
        if (roleRepository.findByName(roleName).isPresent()) {
            throw new CustomAlreadyExistsException("roleName", "Role '" + roleName + "' already exists.");
        }
        roleRepository.save(roleMapper.fromDto(roleName));
    }

    @Override
    public void delete(Long roleId) {
        Role role = findById(roleId);
        for (User user : role.getUsers()) {
            user.getRoles().remove(role);
        }
        roleRepository.delete(role);
    }

    @Override
    public Role findByName(String roleName) {
        return roleRepository.findByName(roleName).orElseThrow(() -> new CustomNotFoundException(roleName, "Role with name '" + roleName + "' not found."));
    }

}
