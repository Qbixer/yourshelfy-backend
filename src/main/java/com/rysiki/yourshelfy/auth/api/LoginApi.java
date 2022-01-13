package com.rysiki.yourshelfy.auth.api;

import com.rysiki.yourshelfy.auth.dto.EmailAndPasswordCredentialDTO;
import com.rysiki.yourshelfy.auth.dto.UserProfileDTO;
import com.rysiki.yourshelfy.auth.entity.MyUser;
import com.rysiki.yourshelfy.auth.service.MyUserDetailService;
import com.rysiki.yourshelfy.auth.service.MyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;


@RequestMapping("/login")
@RestController
public class LoginApi {

    @Autowired
    DaoAuthenticationProvider daoAuthenticationProvider;

    @Autowired
    MyUserDetailService myUserDetailService;

    @Autowired
    MyUserService myUserService;

    @PostMapping("")
    ResponseEntity login(@RequestBody EmailAndPasswordCredentialDTO emailAndPasswordCredentialDTO) {
        Authentication authentication = daoAuthenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(emailAndPasswordCredentialDTO.getEmail(), emailAndPasswordCredentialDTO.getPassword()));
        boolean isAuthenticated = isAuthenticated(authentication);
        if (isAuthenticated) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            Optional<MyUser> myUserOptional = myUserService.getUserByEmail(emailAndPasswordCredentialDTO.getEmail());
            if(myUserOptional.isPresent()) {
                MyUser myUser = myUserOptional.get();
                return ResponseEntity.ok(UserProfileDTO.builder()
                        .email(myUser.getEmail())
                        .firstname(myUser.getFirstname())
                        .surname(myUser.getSurname())
                        .phone(myUser.getPhone())
                        .build());
            }
        }
        return ResponseEntity.notFound().build();
    }

    private boolean isAuthenticated(Authentication authentication) {
        return authentication != null && !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
    }

}
