package com.example.demo.config;
// src/main/java/com/example/demo/config/SecurityConfiguration.java

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    // Define tu PasswordEncoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            //.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authorize -> authorize
                // Permitir acceso público a las rutas de registro y login
                .requestMatchers("/","/register", "/login", "/css/**", "/js/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/register").permitAll()
                // Cualquier otra petición requiere autenticación
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login") // Especifica tu página de login personalizada
                .loginProcessingUrl("/loginProccess")
                .defaultSuccessUrl("/home", true)// URL a la que ir después de un login exitoso
                .failureUrl("/")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // Ruta para cerrar sesión
                .logoutSuccessUrl("/") // URL a la que ir después de cerrar sesión
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .permitAll()
            );
        return http.build();
    }
}
