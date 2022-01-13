package com.rysiki.yourshelfy.auth.repository;

import com.rysiki.yourshelfy.auth.entity.Authority;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
public class MyUserAuthorityRepositoryImpl implements MyUserAuthorityRepository {

    final @NonNull EntityManager entityManager;

    @Override
    @SuppressWarnings("unchecked")
    public List<Authority> findAuthoritiesByEmail(String email) {
        return entityManager.createQuery("SELECT a FROM Authority a WHERE a.email = :email")
                .setParameter("email", email)
                .getResultList();
    }
}
