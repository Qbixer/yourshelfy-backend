package com.rysiki.yourshelfy.auth.repository;

import com.rysiki.yourshelfy.auth.entity.Authority;

import java.util.List;

public interface MyUserAuthorityRepository {

    public List<Authority> findAuthoritiesByEmail(String email);
}
