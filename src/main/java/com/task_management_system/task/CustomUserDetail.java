package com.task_management_system.task;

import com.task_management_system.task.model.User;
import lombok.Data;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@ToString
@Data
public class CustomUserDetail implements UserDetails {

    private final User user;
    private Long id;
    private String name;
    private String email;
    private String role;
    private String token;

    public CustomUserDetail(User user) {
        this.user = user;
        this.id=user.getId();
        this.name=user.getName();
        this.email=user.getEmail();
        this.role=user.getRole();

    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("USER"));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserName();
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
