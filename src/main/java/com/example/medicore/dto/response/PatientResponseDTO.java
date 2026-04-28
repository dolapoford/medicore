package com.example.medicore.dto.response;

import com.example.medicore.entity.Patient;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PatientResponseDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private Patient.Gender gender;
    private String address;
}
