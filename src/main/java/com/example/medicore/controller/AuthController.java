package com.example.medicore.controller;

import com.example.medicore.dto.auth.AuthResponseDTO;
import com.example.medicore.dto.auth.LoginRequestDTO;
import com.example.medicore.dto.auth.RegisterRequestDTO;
import com.example.medicore.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Authentication", description = "Endpoints for user registration and authentication")
public class AuthController {

    private final AuthenticationService authenticationService;

    @Operation(summary = "User Registration", description = "Register a new user in the system")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data or validation error")
    })
    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> registerUser(@RequestBody @Valid RegisterRequestDTO registerRequestDTO){
        AuthResponseDTO authResponseDTO = authenticationService.register(registerRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(authResponseDTO);
    }



    @Operation(summary = "User Login", description = "Authenticate a user and generate a JWT token")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login successful"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials - Unauthorized")
    })
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> loginUser(@RequestBody @Valid LoginRequestDTO loginRequestDTO){
        AuthResponseDTO authResponseDTO = authenticationService.login(loginRequestDTO);

        return ResponseEntity.ok(authResponseDTO);
    }


}
