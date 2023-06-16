//article
/*
package com.jpatest.springjpa.service;

import com.jpatest.springjpa.entity.AppUser;
import com.jpatest.springjpa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AppUserDetailsService implements UserDetailsService {
  private final UserRepository appUserRepository;
  @Autowired
  public AppUserDetailsService(UserRepository appUserRepository){
    this.appUserRepository = appUserRepository;
  }

  public UserDetails loadUserByUsername(String email) throws Exception{
    AppUser appUser = appUserRepository.findByEmail(email);
    if(appUser == null){
      throw new UsernameNotFoundException("Not found");
    }
    boolean enabled = true;
    boolean accountNotExpired = true;
    boolean credentialsNonExpired = true;
    boolean accountNonLocked = true;

    return new org.springframework.security.core.userdetails.User(
          appUser.getEmail(), appUser.getPassword(), enabled, accountNotExpired,
          credentialsNonExpired, accountNonLocked, getAuthorities(appUser.getRoles()));
  }

  private static List<GrantedAuthority> getAuthorities (List<String> roles){
    List<GrantedAuthority> authorities = newArrayList<>();
    for (String role : roles){
      authorities.add(new SimpleGrantedAuthority(role));
    }
    return authorities;
  }
  //JAVA CONFIGURATION

  @Autowired
private MyUserDetailsService userDetailsService;

@Override
protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService);
}

  
}*/