package com.example.medicore.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class DoctorResponseDTO {
    private Long id;

    private String firstName;


    private String lastName;

      private String phoneNumber;
    private  String specialization;

    private String licenseNumber;
}
