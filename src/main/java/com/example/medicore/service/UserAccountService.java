package com.example.medicore.service;

import com.example.medicore.entity.User;
import com.example.medicore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserAccountService {

    private final UserRepository userRepository;

    public List<User> findAllUser(){
        return userRepository.findAll();
    }

}
