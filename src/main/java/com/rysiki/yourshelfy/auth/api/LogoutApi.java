package com.rysiki.yourshelfy.auth.api;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/logout")
@RestController
public class LogoutApi {


    @GetMapping("")
    ResponseEntity logout() {
        SecurityContextHolder.getContext().setAuthentication(null);
        return ResponseEntity.ok(HttpEntity.EMPTY);
    }
}
