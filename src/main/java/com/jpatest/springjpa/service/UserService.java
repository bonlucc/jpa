package com.jpatest.springjpa.service;

import com.jpatest.springjpa.entity.User;
import com.jpatest.springjpa.model.UserModel;

public interface UserService {
    User registerUser(UserModel userModel);

    void saveVerificationTokenForUser(String token, User user);
}
