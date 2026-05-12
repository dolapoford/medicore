package com.example.medicore.controller;

import com.example.medicore.dto.auth.AuthResponseDTO;
import com.example.medicore.dto.auth.LoginRequestDTO;
import com.example.medicore.dto.auth.RegisterRequestDTO;
import com.example.medicore.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> registerUser(@RequestBody @Valid RegisterRequestDTO registerRequestDTO){
        AuthResponseDTO authResponseDTO = authenticationService.register(registerRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(authResponseDTO);
    }


    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> loginUser(@RequestBody @Valid LoginRequestDTO loginRequestDTO){
        AuthResponseDTO authResponseDTO = authenticationService.login(loginRequestDTO);

        return ResponseEntity.ok(authResponseDTO);
    }
}
