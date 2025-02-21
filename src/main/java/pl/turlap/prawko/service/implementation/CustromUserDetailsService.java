package pl.turlap.prawko.service.implementation;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.turlap.prawko.model.Role;
import pl.turlap.prawko.model.User;
import pl.turlap.prawko.service.UserService;

import java.util.Collection;

@Service
public class CustromUserDetailsService implements UserDetailsService {
    private final UserService userService;

    public CustromUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        if (userService.checkIfExist(userName)) {
            User user = userService.findByUserName(userName);
            return new org.springframework.security.core.userdetails.User(user.getUserName(),
                    user.getPassword(),
                    mapRolesToAuthorities(user.getRoles()));
        } else {
            throw new UsernameNotFoundException("Invalid login or password.");
        }
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                .toList();
    }
}
