package com.rysiki.yourshelfy.auth.api;

import com.rysiki.yourshelfy.auth.dto.UserCredentialsDTO;
import com.rysiki.yourshelfy.auth.entity.MyUser;
import com.rysiki.yourshelfy.auth.service.MyUserService;
import com.rysiki.yourshelfy.shelf.service.ShelfService;
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

    @Autowired
    ShelfService shelfService;

    @PostMapping("")
    public ResponseEntity userRegistration(@RequestBody UserCredentialsDTO userCredentialsDTO) {
        try {
            MyUser myUser = myUserService.register(userCredentialsDTO);
            shelfService.createNullAndCartShelfForNewUser(myUser);
            return ResponseEntity.ok().build();
        } catch (MyUserService.UserAlreadyExistException | MyUserService.WrongCredentialsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
