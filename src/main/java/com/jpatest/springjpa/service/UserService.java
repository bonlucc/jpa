package com.jpatest.springjpa.service;

import com.jpatest.springjpa.entity.AppUser;
import com.jpatest.springjpa.model.UserModel;

public interface UserService {
    AppUser registerUser(UserModel userModel);

    void saveVerificationTokenForUser(String token, AppUser appUser);
}
