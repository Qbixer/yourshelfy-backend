package com.rysiki.yourshelfy.auth.repository;

import com.rysiki.yourshelfy.auth.entity.MyUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MyUserRepository extends CrudRepository<MyUser, String>, MyUserAuthorityRepository {

    @Query("select u from MyUser u where u.email = :email")
    Optional<MyUser> findByEmail(String email);
}
