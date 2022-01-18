package com.rysiki.yourshelfy.auth.service;

import com.rysiki.yourshelfy.auth.dto.UserCredentialsDTO;
import com.rysiki.yourshelfy.auth.entity.MyUser;
import com.rysiki.yourshelfy.auth.repository.MyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserService {

    public static class UserAlreadyExistException extends RuntimeException {
        public UserAlreadyExistException(String message) {
            super(message);
        }
    }

    public static class WrongCredentialsException extends RuntimeException {
        public WrongCredentialsException(String message) {
            super(message);
        }
    }

    public static class UserNotFound extends RuntimeException {
        public UserNotFound(String message) {
            super(message);
        }
    }

    public static class UserNotAuthenticated extends RuntimeException {
        public UserNotAuthenticated(String message) {
            super(message);
        }
    }

    public static class UserLackPermission extends RuntimeException {
        public UserLackPermission(String message) {
            super(message);
        }
    }

    @Autowired
    MyUserRepository myUserRepository;

    @Autowired
    PasswordEncoder passwordEncoder;


    public MyUser register(UserCredentialsDTO userCredentialsDTO) throws UserAlreadyExistException, WrongCredentialsException {
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
        return myUserRepository.save(user);
    }

    public boolean checkIfUserExist(String email) {
        return getUserByEmail(email).isPresent();
    }

    public Optional<MyUser> getUserByEmail(String email) {
        return myUserRepository.findByEmail(email);
    }

    public MyUser getCurrentUser() throws UserNotFound, UserNotAuthenticated {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.isAuthenticated()) {
            try {
                String email = ((User) authentication.getPrincipal()).getUsername();
                Optional<MyUser> optionalMyUser = myUserRepository.findByEmail(email);
                if (optionalMyUser.isPresent()) {
                    return optionalMyUser.get();
                } else {
                    throw new UserNotFound("User not found");
                }
            } catch (Exception e) {
                throw new UserNotFound("User not found");
            }
        }
        throw new UserNotAuthenticated("User not authenticated");
    }
}