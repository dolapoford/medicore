package com.example.medicore.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "First Name is required")
    @Size(max = 100)
    @Column(name = "first_name", nullable = false)
    private String  firstName;

    @NotBlank(message = "Last Name is required")
    @Size(max = 100)
    @Column(name = "last_name", nullable = false)
    private String  lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Column(nullable = false, unique = true)
    private String email;

    @Pattern(regexp = "^\\+?[0-9]{7,15}$", message = "Invalid Phone number")
    @Column(name = "phone_number")
    private String phoneNumber;

    @NotBlank(message = "Specialization is required")
    @Column(nullable = false)
    private String specialization;

    @Column(name = "license_number", unique = true)
    private String licenseNumber;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, fetch = FetchType.LAZY,orphanRemoval = true)
    @Builder.Default
    private List<Appointment> appointments = new ArrayList<>();

    @OneToOne(mappedBy = "doctor",fetch = FetchType.LAZY)
    private User user;
}
