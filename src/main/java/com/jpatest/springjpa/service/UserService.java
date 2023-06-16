package com.jpatest.springjpa.service;

import com.jpatest.springjpa.entity.AppUser;
import com.jpatest.springjpa.entity.VerificationToken;
import com.jpatest.springjpa.model.UserModel;

import java.util.Optional;

public interface UserService {
    AppUser registerUser(UserModel userModel);

    void saveVerificationTokenForUser(String token, AppUser appUser);

    boolean validVerificationToken(String token);

    VerificationToken generateNewVerificationToken(String oldToken);

    void createPasswordResetTokenForUser(AppUser appUser, String token);

    AppUser findAppUserByEmail(String email);

    boolean validPasswordResetToken(String token);

    Optional<AppUser> getAppUserByPasswordResetToken(String token);

    void changePassword(AppUser appUser, String newPassword);

    boolean passwordsMatch(String userPassword, String inputPassword);
  
}
