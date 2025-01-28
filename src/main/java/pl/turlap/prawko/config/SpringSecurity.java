package pl.turlap.prawko.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SpringSecurity {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests((authorize) ->
                        authorize
                                .requestMatchers("/css/**", "/js/**", "/media/**", "/scss/**").permitAll()
                                .requestMatchers("/register/**").permitAll()
                                .requestMatchers("/index").hasAuthority("ROLE_USER")
                                .requestMatchers("/tests/newExam").hasAuthority("ROLE_USER")
                                .requestMatchers("/tests/exam").hasAuthority("ROLE_USER")
                                .requestMatchers("/admin-panel").hasAuthority("ROLE_ADMIN")
                                .requestMatchers("/upload").permitAll()
                                .requestMatchers("/users/**").permitAll()
                                .requestMatchers("/questions/**").permitAll()
                                .requestMatchers("/questions/upload").hasAuthority("ROLE_ADMIN")
                                .requestMatchers("/questions/find/**").permitAll()
                                .requestMatchers("/users/getUserLanguage").hasAuthority("ROLE_USER")
                                .requestMatchers("/tests/submitAnswer").hasAuthority("ROLE_USER")
                                .requestMatchers("/questions/all/**").permitAll()
                                .requestMatchers("/tests/new/**").permitAll()
                                .requestMatchers("/roles/add").permitAll()
                                .requestMatchers("/roles/all").permitAll()
                ).formLogin(
                        form -> form
                                .loginPage("/login")
                                .loginProcessingUrl("/login")
                                .defaultSuccessUrl("/index", true)
                                .permitAll()
                ).logout(
                        logout -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .logoutSuccessUrl("/register")
                                .permitAll()
                );
        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }
}
