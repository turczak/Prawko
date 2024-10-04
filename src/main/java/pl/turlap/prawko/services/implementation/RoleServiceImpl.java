package pl.turlap.prawko.services.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.turlap.prawko.models.Role;
import pl.turlap.prawko.repositories.RoleRepository;
import pl.turlap.prawko.services.RoleService;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    private RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public void addNewRole(Role role) {
        roleRepository.save(role);
    }
}
