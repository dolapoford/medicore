package com.example.medicore.config;

import com.example.medicore.entity.User;
import com.example.medicore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        System.out.println("Creating the user");
        createUserIfNotExists("ahmadasiyanbola@gmail.com", "password", User.Role.ADMIN);


    }


    private void createUserIfNotExists(String email, String password, User.Role role){
        if (userRepository.existsByEmail(email)) {
            return; // already seeded, skip silently
        }


        User user = User.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .role(role)
                .build();


        userRepository.save(user);

    }
}
