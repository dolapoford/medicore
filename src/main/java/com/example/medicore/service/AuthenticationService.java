package com.example.medicore.service;

import com.example.medicore.dto.auth.AuthResponseDTO;
import com.example.medicore.dto.auth.LoginRequestDTO;
import com.example.medicore.dto.auth.RegisterRequestDTO;
import com.example.medicore.entity.Doctor;
import com.example.medicore.entity.User;
import com.example.medicore.repository.DoctorRespository;
import com.example.medicore.repository.UserRepository;
import com.example.medicore.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final DoctorRespository doctorRespository;
    private final PasswordEncoder passwordEncoder;


    public AuthResponseDTO register(RegisterRequestDTO registerRequestDTO){
       if (userRepository.existsByEmail(registerRequestDTO.getEmail())){
           throw new RuntimeException("User already exist");
       }

       Doctor doctor = null;
       if (registerRequestDTO.getRole() == User.Role.DOCTOR){
           if (registerRequestDTO.getDoctorId() == null){
               throw new RuntimeException("Doctors Id is required when registering a doctor account");
           }

           doctor = doctorRespository.findById(registerRequestDTO.getDoctorId()).orElseThrow(()-> new RuntimeException("Doctor not found with ID: " + registerRequestDTO.getDoctorId()));

           if (userRepository.existsByDoctorId(registerRequestDTO.getDoctorId())){
               throw new RuntimeException("This doctor profile already has an account");
           }
       }

       User user = User.builder()
               .email(registerRequestDTO.getEmail())
               .password(passwordEncoder.encode(registerRequestDTO.getPassword()))
               .role(registerRequestDTO.getRole())
               .doctor(doctor)
               .build();
       userRepository.save(user);

        List <String> roles = List.of(user.getRole().name());
        String accessToken = jwtService.generateAccessToken(user,roles);
        String refreshToken = jwtService.generateRefreshToken(user);


        return new AuthResponseDTO(accessToken,refreshToken,"Bearer");
    }

    public AuthResponseDTO login(LoginRequestDTO loginRequestDTO){

       User user = userRepository.findByEmail(loginRequestDTO.getEmail()).orElseThrow(()-> new RuntimeException("Invalid user or password"));


        if (!passwordEncoder.matches(loginRequestDTO.getPassword(), user.getPassword() )){
            throw new RuntimeException("Invalid User or Password");
        }

        List<String> roles = List.of(user.getRole().name());
        String accessToken = jwtService.generateAccessToken(user,roles);
        String refreshToken = jwtService.generateRefreshToken(user);

        user.setRefreshToken(refreshToken);
        userRepository.save(user);

        return new AuthResponseDTO(accessToken,refreshToken,"Bearer");
    }



}
