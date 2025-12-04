package com.provectus.kafka.ui.config.auth;

import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class BasicUser implements UserDetails, RbacUser {

  private final String username;
  private final String password;
  private final List<String> roles;
  private final List<SimpleGrantedAuthority> authorities;

  public BasicUser(String username, String password, List<String> roles) {
    this.username = username;
    this.password = password;
    this.roles = roles;
    this.authorities = roles.stream()
        .map(SimpleGrantedAuthority::new)
        .toList();
  }

  @Override
  public String name() {
    return username;
  }

  @Override
  public Collection<String> groups() {
    return roles;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
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
