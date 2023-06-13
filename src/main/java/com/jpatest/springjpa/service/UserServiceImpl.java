package com.jpatest.springjpa.service;

import java.util.Calendar;
import java.util.UUID;

import com.jpatest.springjpa.entity.AppUser;
import com.jpatest.springjpa.entity.VerificationToken;
import com.jpatest.springjpa.entity.PasswordResetToken;
import com.jpatest.springjpa.model.UserModel;
import com.jpatest.springjpa.repository.UserRepository;
import com.jpatest.springjpa.repository.VerificationTokenRepository;
import com.jpatest.springjpa.repository.PasswordResetTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    private final VerificationTokenRepository verificationTokenRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PasswordResetTokenRepository passwordResetTokenRepository; 
    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, VerificationTokenRepository verificationTokenRepository, PasswordResetTokenRepository passwordResetTokenRepository){
        this.verificationTokenRepository = verificationTokenRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
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
    @Override
    public boolean validVerificationToken(String token){
      VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
      if(verificationToken == null) {
        return false;
      }
      AppUser appUser = verificationToken.getAppUser();
      Calendar cal = Calendar.getInstance();
      if((verificationToken.getExpirationTime().getTime() - cal.getTime().getTime()) <= 0){
        //verificationTokenRepository.delete(verificationToken);
        return false;
      }

      appUser.setEnabled(true);
      userRepository.save(appUser);
      return true;
    
    }

    @Override
    public VerificationToken generateNewVerificationToken(String oldToken){
      VerificationToken verificationToken = verificationTokenRepository.findByToken(oldToken);
      verificationToken.setToken(UUID.randomUUID().toString());
  verificationToken.setExpirationTime(VerificationToken.calculateExpirationDate(VerificationToken.EXPIRATION_TIME));
      verificationTokenRepository.save(verificationToken);
      return verificationToken;
      
    }


  @Override
  public AppUser findUserByEmail(String email){
    return userRepository.findByEmail(email);
  }

  @Override
  public void createPasswordResetTokenForUser(AppUser appUser, String token){
    PasswordResetToken passwordResetToken = new PasswordResetToken(appUser,token);
    passwordResetTokenRepository.save(passwordResetToken);
  }


  @Override
  public boolean validPasswordResetToken(String token){
      PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);
      if(passwordResetToken == null) {
        return false;
      }
      AppUser appUser = passwordResetToken.getAppUser();
      Calendar cal = Calendar.getInstance();
      if((passwordResetToken.getExpirationTime().getTime() - cal.getTime().getTime()) <= 0){
        //verificationTokenRepository.delete(verificationToken);
        return false;
      }
      //TO DO
      //appUser.setEnabled(true);
      //userRepository.save(appUser);
      return true;
    
    }
}
