package com.example.medicore.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank( message = "First Name is required")
    @Size(max = 100)
    @Column( name = "first_name" , nullable = false)
    private String firstName;

    @NotBlank( message = "Last Name is required")
    @Size(max = 100)
    @Column( name = "last_name" , nullable = false)
    private String lastName;


    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Column(unique = true,  nullable = false)
    private String email;

    @Pattern(regexp = "^\\+?[0-9]{7,15}$", message = "Invalid Phone number")
    @Column(name = "phone_number")
    private String phoneNumber;


    @NotNull(message = "Date of birth is required")
    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @Column(columnDefinition = "TEXT")
    private String address;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL , fetch = FetchType.LAZY, orphanRemoval = true)
    @Builder.Default
    private List<Appointment> appointments = new ArrayList<>();

    public enum Gender{
        MALE,FEMALE,OTHER
    }

}
