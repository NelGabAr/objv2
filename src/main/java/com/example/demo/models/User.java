package com.example.demo.models;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long Id;

    @Column(name = "username", unique = true)
    private String username;
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Year> years;
    
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL) // Carga los roles al cargar el usuario
    @JoinTable(
        name = "user_roles", // Nombre de la tabla intermedia
        joinColumns = @JoinColumn(name = "user_id"), // Columna para la FK de User
        inverseJoinColumns = @JoinColumn(name = "role_id") // Columna para la FK de Role
    )
    private Set<Role> roles = new HashSet<>(); // Inicializar para evitar NullPointerException




    public List<Year> getYears() {
        return years;
    }

    public void setYears(List<Year> years) {
        this.years = years;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    

    
}
