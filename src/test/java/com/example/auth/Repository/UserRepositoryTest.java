package com.example.auth.Repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    public void existsByUsername() {
        boolean actual = userRepository.existsByUsername("sandeep4");
        Assertions.assertTrue(actual);
    }
}
