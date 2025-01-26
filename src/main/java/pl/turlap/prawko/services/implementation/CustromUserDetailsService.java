package pl.turlap.prawko.services.implementation;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.turlap.prawko.models.Role;
import pl.turlap.prawko.models.User;
import pl.turlap.prawko.repositories.UserRepository;

import java.util.Collection;

@Service
public class CustromUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustromUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(login);

        if (user == null) {
            user = userRepository.findByEmail(login);
        }

        if (user != null) {
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
