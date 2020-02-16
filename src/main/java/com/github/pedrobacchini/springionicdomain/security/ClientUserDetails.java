package com.github.pedrobacchini.springionicdomain.security;

import com.github.pedrobacchini.springionicdomain.enums.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class ClientUserDetails implements UserDetails {

    private static final long serialVersionUID = -6945807835904127707L;

    private int id;
    private String email;
    private String senha;
    private Collection<? extends GrantedAuthority> authorities;

    public ClientUserDetails(int id, String email, String senha, Set<Role> roles) {
        this.id = id;
        this.email = email;
        this.senha = senha;
        this.authorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getDescription()))
                .collect(Collectors.toSet());
    }

    public int getId() { return id; }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { return authorities; }

    @Override
    public String getPassword() { return senha; }

    @Override
    public String getUsername() { return email; }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }

    public boolean hasRole(Role admin) {
        return authorities.contains(new SimpleGrantedAuthority(admin.getDescription()));
    }
}
