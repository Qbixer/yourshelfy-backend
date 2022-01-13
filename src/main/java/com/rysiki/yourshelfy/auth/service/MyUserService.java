package com.rysiki.yourshelfy.auth.service;

import com.rysiki.yourshelfy.auth.dto.UserCredentialsDTO;
import com.rysiki.yourshelfy.auth.entity.MyUser;
import com.rysiki.yourshelfy.auth.repository.MyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserService {

    public static class UserAlreadyExistException extends Exception {
        UserAlreadyExistException(String message) {
            super(message);
        }
    }

    public static class WrongCredentialsException extends Exception {
        WrongCredentialsException(String message) {
            super(message);
        }
    }

    @Autowired
    MyUserRepository myUserRepository;

    @Autowired
    PasswordEncoder passwordEncoder;


    public void register(UserCredentialsDTO userCredentialsDTO) throws UserAlreadyExistException, WrongCredentialsException {
        if(userCredentialsDTO.getEmail() == null || userCredentialsDTO.getEmail().isBlank() || userCredentialsDTO.getPassword() == null || userCredentialsDTO.getPassword().isBlank()) {
            throw new WrongCredentialsException("Email and/or password are not set");
        }
        if(checkIfUserExist(userCredentialsDTO.getEmail())){
            throw new UserAlreadyExistException("User already exists for this email");
        }
        MyUser user = MyUser.builder()
                .email(userCredentialsDTO.getEmail())
                .firstname(userCredentialsDTO.getFirstname())
                .surname(userCredentialsDTO.getSurname())
                .phone(userCredentialsDTO.getPhone())
                .enabled(true)
                .build();
        user.setPassword(passwordEncoder.encode(userCredentialsDTO.getPassword()));
        myUserRepository.save(user);
    }

    public boolean checkIfUserExist(String email) {
        return getUserByEmail(email).isPresent();
    }

    public Optional<MyUser> getUserByEmail(String email) {
        return myUserRepository.findByEmail(email);
    }
}