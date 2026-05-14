package com.example.medicore.controller;

import com.example.medicore.dto.request.AppointmentRequestDTO;
import com.example.medicore.dto.response.AppointmentResponseDTO;
import com.example.medicore.entity.Appointment;
import com.example.medicore.service.AppointmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appointment")
@RequiredArgsConstructor
@Tag(name = "Appointment Management", description = "Endpoints for managing medical appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @Operation(summary = "Create Appointment", description = "Schedule a new appointment")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Appointment created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input or validation error"),
            @ApiResponse(responseCode = "404", description = "Patient or Doctor not found")
    })
    @PostMapping
    public ResponseEntity<AppointmentResponseDTO> createAppointment(@RequestBody @Valid AppointmentRequestDTO appointmentRequestDTO){
        AppointmentResponseDTO appointmentResponseDTO = appointmentService.createAppointment(appointmentRequestDTO);
       return ResponseEntity.ok().body(appointmentResponseDTO);
    }


    @Operation(summary = "Update Appointment Status", description = "Change the status of an existing appointment")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Appointment status updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid status"),
            @ApiResponse(responseCode = "404", description = "Appointment not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<AppointmentResponseDTO> updateAppointment(@PathVariable Long id, @RequestParam Appointment.AppointmentStatus newStatus) {
        AppointmentResponseDTO appointmentResponseDTO = appointmentService.updateAppointment(id, newStatus);
        return ResponseEntity.ok().body(appointmentResponseDTO);
    }

    @Operation(summary = "Cancel Appointment", description = "Cancel an existing appointment by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Appointment cancelled successfully"),
            @ApiResponse(responseCode = "404", description = "Appointment not found")
    })
    @PutMapping("/cancel/{id}")
    public ResponseEntity<AppointmentResponseDTO> cancelAppointment(@PathVariable Long id){
        AppointmentResponseDTO appointmentResponseDTO = appointmentService.cancelAppointment(id);
        return ResponseEntity.ok().body(appointmentResponseDTO);
    }


}
