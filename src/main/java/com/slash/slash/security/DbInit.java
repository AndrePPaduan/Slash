package com.slash.slash.security;

import com.slash.slash.models.Users;
import com.slash.slash.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class DbInit implements CommandLineRunner {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public DbInit(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        this.userRepository.deleteAll();

        Users john = new Users("John", passwordEncoder.encode("pass12"), "john@lennon", "ADMIN");

        List<Users> users = Arrays.asList(john);

        this.userRepository.saveAll(users);
    }
}
