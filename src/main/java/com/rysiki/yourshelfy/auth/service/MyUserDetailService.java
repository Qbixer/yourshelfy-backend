package com.rysiki.yourshelfy.auth.service;

import com.rysiki.yourshelfy.auth.entity.Authority;
import com.rysiki.yourshelfy.auth.entity.MyUser;
import com.rysiki.yourshelfy.auth.repository.MyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    MyUserRepository myUserRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        final Optional<MyUser> optionalUser = myUserRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException(email);
        }
        MyUser user = optionalUser.get();
        List<Authority> authoritiesByEmail = myUserRepository.findAuthoritiesByEmail(email);

        return User.withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities(authoritiesByEmail.stream().map(Authority::getAuthority).toArray(String[]::new))
                .build();
    }
}