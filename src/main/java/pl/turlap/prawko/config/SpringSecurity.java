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
        http
                .authorizeHttpRequests((authorize) ->
                        authorize
                                .requestMatchers("/css/**", "/js/**", "/media/**", "/scss/**").permitAll()
                                .requestMatchers("/register/**").permitAll()
                                .requestMatchers("/index").hasAuthority("ROLE_USER")
                                .requestMatchers("/admin-panel").hasAuthority("ROLE_ADMIN")
                                .requestMatchers("/admin-panel/upload").hasAuthority("ROLE_ADMIN")
                                .requestMatchers("/newExam").hasAuthority("ROLE_USER")
                                .requestMatchers("/exam").hasAuthority("ROLE_USER")
                                .requestMatchers("/submitAnswer").hasAuthority("ROLE_USER")
                                .requestMatchers("/upload").permitAll()
                                .requestMatchers("/users/*/roles").permitAll()
                                .requestMatchers("/questions/**").permitAll()
                                .requestMatchers("/questions/upload").hasAuthority("ROLE_ADMIN")
                                .requestMatchers("/questions/find/**").permitAll()
                                .requestMatchers("/users/register").permitAll()
                                .requestMatchers("/users/**").permitAll()
                                .requestMatchers("/users/delete").permitAll()
                                .requestMatchers("/users/setCategory").hasAuthority("ROLE_USER")
                                .requestMatchers("/users/setLanguage").hasAuthority("ROLE_USER")
                                .requestMatchers("/editPreferences").hasAuthority("ROLE_USER")
                                .requestMatchers("/questions/all**").permitAll()
                                .requestMatchers("/roles/add").permitAll()
                                .requestMatchers("/roles/all").permitAll()
                                .requestMatchers("/roles/delete").permitAll()
                                .requestMatchers("/roles/**").permitAll()
                                .requestMatchers("/tests/**").permitAll()
                                .requestMatchers("/tests/new").permitAll()
                                .requestMatchers("/tests/all").permitAll()
                )
                .sessionManagement((sessions) -> sessions
                        .sessionConcurrency((concurency) -> concurency
                                .maximumSessions(1)
                                .expiredUrl("/login?expired")))
                .formLogin(
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
