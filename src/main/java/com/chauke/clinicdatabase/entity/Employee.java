package com.chauke.clinicdatabase.entity;

import com.chauke.clinicdatabase.enums.Roles;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Setter
@Entity(name = "Employee")
@Table(name = "employee")
@NoArgsConstructor
public class Employee implements UserDetails {

    @Getter
    @Id
    @SequenceGenerator(name = "employee_sequence", sequenceName = "employee_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_sequence")
    @Column(name = "id", updatable = false)
    private Integer id;

    @Getter
    @NotNull(message = "First name cannot be null")
    @Column(name = "first_name", nullable = false, columnDefinition = "TEXT")
    private String firstName;

    @Getter
    @NotNull(message = "Last name cannot be null")
    @Column(name = "last_name", nullable = false, columnDefinition = "TEXT")
    private String lastName;

    @Getter
    @Email(message = "Invalid email address")
    @NotNull
    @Column(name = "email", columnDefinition = "TEXT", nullable = false, unique = true)
    private String email;

    @NotNull(message = "Password cannot be null")
    @Column(name = "password", nullable = false)
    private String password;

    @Getter
    @NotNull
    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Roles role;

    public Employee(String firstName, String lastName, String email, String password, Roles role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
