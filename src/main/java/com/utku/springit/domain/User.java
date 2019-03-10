package com.utku.springit.domain;

import com.utku.springit.domain.validator.PasswordsMatch;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Data
@PasswordsMatch
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    @Size(min = 8, max = 20)
    @Column(nullable = false, unique = true)
    private String email;

    @NonNull
    @Column(length = 100)
    private String password;

    @NonNull
    @Column(nullable = false)
    private boolean enabled;

    @ToString.Exclude
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles = new HashSet<>();

    @NonNull
    @NotEmpty(message = "need firstname")
    private String firstName;

    @NonNull
    @NotEmpty(message = "need lastname")
    private String lastName;

    @Transient
    @Setter(AccessLevel.NONE)
    private String fullName;

    @NonNull
    @NotEmpty(message = "enter alias")
    @Column(nullable = false, unique = true)
    private String alias;

    @Transient
    @NotEmpty(message = "enter password confirmation")
    private String confirmPassword;

    private String activationCode;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
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

    public void addRole(Role role) {
        roles.add(role);
    }

    public void addRoles(Set<Role> roleSet) {
        roles.addAll(roleSet);
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
