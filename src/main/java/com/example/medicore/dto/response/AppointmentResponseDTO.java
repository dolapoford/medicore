package com.example.medicore.dto.response;

import com.example.medicore.entity.Appointment;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentResponseDTO {
    private Long id;
    private PatientResponseDTO patient;
    private DoctorResponseDTO doctor;
    private LocalDateTime scheduledAt;
    private Appointment.AppointmentStatus status;
    private String notes;
    private LocalDateTime createdAt;
}
