package com.jpatest.springjpa.service;

import com.jpatest.springjpa.entity.AppUser;
import com.jpatest.springjpa.entity.VerificationToken;
import com.jpatest.springjpa.model.UserModel;

public interface UserService {
    AppUser registerUser(UserModel userModel);

    void saveVerificationTokenForUser(String token, AppUser appUser);

    boolean validVerificationToken(String token);

    VerificationToken generateNewVerificationToken(String oldToken);
  
    AppUser getUserByEmail(String email);

    void createPasswordResetTokenForUser(AppUser appUser, String token);

  
}
