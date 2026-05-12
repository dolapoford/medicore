package com.example.medicore.controller;

import com.example.medicore.dto.request.AppointmentRequestDTO;
import com.example.medicore.dto.response.AppointmentResponseDTO;
import com.example.medicore.entity.Appointment;
import com.example.medicore.service.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appointment")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;


    @PostMapping
    public ResponseEntity<AppointmentResponseDTO> createAppointment(@RequestBody @Valid AppointmentRequestDTO appointmentRequestDTO){
        AppointmentResponseDTO appointmentResponseDTO = appointmentService.createAppointment(appointmentRequestDTO);
       return ResponseEntity.ok().body(appointmentResponseDTO);
    }


    @PutMapping("/{id}")
    public ResponseEntity<AppointmentResponseDTO> updateAppointment(@PathVariable Long id, @RequestParam Appointment.AppointmentStatus newStatus) {
        AppointmentResponseDTO appointmentResponseDTO = appointmentService.updateAppointment(id, newStatus);
        return ResponseEntity.ok().body(appointmentResponseDTO);
    }

    @PutMapping("/cancel/{id}")
    public ResponseEntity<AppointmentResponseDTO> cancelAppointment(@PathVariable Long id){
        AppointmentResponseDTO appointmentResponseDTO = appointmentService.cancelAppointment(id);
        return ResponseEntity.ok().body(appointmentResponseDTO);
    }


}
