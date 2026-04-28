package com.example.medicore.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class DoctorRequestDTO {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Pattern(regexp = "^\\+?[0-9]{7,15}$", message = "Invalid Phone number")
    private String phoneNumber;

    @NotBlank(message = "Specialization is required")
    private  String specialization;

    private String licenseNumber;
}
