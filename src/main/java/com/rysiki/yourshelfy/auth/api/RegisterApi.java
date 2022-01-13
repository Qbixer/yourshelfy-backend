package com.rysiki.yourshelfy.auth.api;

import com.rysiki.yourshelfy.auth.dto.UserCredentialsDTO;
import com.rysiki.yourshelfy.auth.service.MyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/register")
@RestController
public class RegisterApi {

    @Autowired
    private MyUserService myUserService;

    @Autowired
    DaoAuthenticationProvider daoAuthenticationProvider;

    @PostMapping("")
    public ResponseEntity userRegistration(@RequestBody UserCredentialsDTO userCredentialsDTO) {
        try {
            myUserService.register(userCredentialsDTO);
            return ResponseEntity.ok(ResponseEntity.EMPTY);
        } catch (MyUserService.UserAlreadyExistException | MyUserService.WrongCredentialsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
