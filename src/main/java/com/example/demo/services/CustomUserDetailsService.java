package com.example.demo.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.models.User;
import com.example.demo.repositories.UserRepo;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepo userRepo; // Asume que tienes un UserRepository
    // private final RoleRepository roleRepository; // Si necesitas buscar roles directamente

    public CustomUserDetailsService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Cargar tu entidad User desde la base de datos
        User userEntity = userRepo.findByUsername(username);

        // Convertir tus Roles a una colección de GrantedAuthority
        // Como tu entidad Role ya implementa GrantedAuthority, es directo
        return new org.springframework.security.core.userdetails.User(
                userEntity.getUsername(),
                userEntity.getPassword(),
                userEntity.getRoles()
                
                 // Aquí pasas la colección de objetos Role
                                      // que Spring Security reconocerá como GrantedAuthority
        );
    }
}
