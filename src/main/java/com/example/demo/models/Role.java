package com.example.demo.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import org.springframework.security.core.GrantedAuthority; // Importante para Spring Security

import java.util.Set; // Usaremos Set para almacenar roles en User y evitar duplicados

@Entity
@Table(name = "roles") // Nombre de la tabla en la base de datos
public class Role implements GrantedAuthority { // ¡Implementa GrantedAuthority!

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true, nullable = false, length = 30)
    private String name; // Ejemplo: "ROLE_USER", "ROLE_ADMIN", "ROLE_EDITOR"

    // Relación ManyToMany con User ( mappedBy indica que User es el dueño de la relación)
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    // Constructores
    public Role() {}

    public Role(String name) {
        this.name = name;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    // Método requerido por GrantedAuthority
    @Override
    public String getAuthority() {
        return name; // Aquí es donde Spring Security obtiene el nombre del rol
    }

    // Opcional: Implementar equals() y hashCode() para Set
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return name.equals(role.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
