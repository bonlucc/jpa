package com.jpatest.springjpa.service;

import com.jpatest.springjpa.entity.AppUser;
import com.jpatest.springjpa.entity.VerificationToken;
import com.jpatest.springjpa.model.UserModel;
import com.jpatest.springjpa.repository.UserRepository;
import com.jpatest.springjpa.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    private final VerificationTokenRepository verificationTokenRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, VerificationTokenRepository verificationTokenRepository){
        this.verificationTokenRepository = verificationTokenRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AppUser registerUser (UserModel userModel){
        AppUser appUser = AppUser.builder()
                .firstName(userModel.getFirstName())
                .lastName(userModel.getLastName())
                .password(passwordEncoder.encode(userModel.getPassword()))
                .email(userModel.getEmail())
                .build();
        return userRepository.save(appUser);
    }

    @Override
    public void saveVerificationTokenForUser(String token, AppUser appUser) {
        VerificationToken verificationToken = new VerificationToken(appUser,token);
        verificationTokenRepository.save(verificationToken);

    }
}
