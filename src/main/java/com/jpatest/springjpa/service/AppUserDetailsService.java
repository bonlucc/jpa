//article

package com.jpatest.springjpa.service;

@Service
@Transactional
public class AppUserDetailsService implements UserDetailsService{
  private final AppUserRepository appUserRepository;
  @Autowired
  public AppUserDetailsService(AppUserRepository appUserRepository){
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
          user.getEmail(), user.getPassword(), enabled, accountNonExpired,
          credentialsNonExpired, accountNonLocked, getAuthorities(user.getRoles()));
  }

  private static List<GrantedAuthority> getAuthorities (List<String> roles){
    List<GrantedAuthority> authorities = newArrayList<>();
    for (String role : roles){
      authorities.add(new SimpleGrantedAuthority(role));
    }
    return authorities;
  }
  //JAVA CONFIGURATION
  /*
  @Autowired
private MyUserDetailsService userDetailsService;

@Override
protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService);
}
  */
  
}