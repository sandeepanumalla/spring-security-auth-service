package com.example.auth.Repository;


import com.example.auth.Model.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<AuthUser, Long> {
    boolean existsByUsername(String username);
    Optional<AuthUser> findByUsername(String username);
}
