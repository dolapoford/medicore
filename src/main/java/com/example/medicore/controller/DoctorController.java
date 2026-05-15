package com.example.medicore.controller;

import com.example.medicore.dto.request.DoctorRequestDTO;
import com.example.medicore.dto.response.DoctorResponseDTO;
import com.example.medicore.service.DoctorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctor")
@RequiredArgsConstructor
@Tag(name = "Doctor Management", description = "Endpoints for managing doctors")
public class DoctorController {

    private final DoctorService doctorService;

    @Operation(summary = "Create Doctor", description = "Register a new doctor in the system")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Doctor created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input or validation error")
    })
    @PostMapping
    public ResponseEntity<DoctorResponseDTO> createDoctor(@RequestBody @Valid DoctorRequestDTO doctorRequestDTO){
        DoctorResponseDTO doctorResponseDTO = doctorService.createDoctor(doctorRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(doctorResponseDTO);
    }

    @Operation(summary = "Get Doctor by ID", description = "Retrieve a doctor's details by their ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Doctor retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Doctor not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<DoctorResponseDTO> getDoctorById(@PathVariable  Long id){
        DoctorResponseDTO doctorResponseDTO = doctorService.getDoctorById(id);

        return ResponseEntity.ok(doctorResponseDTO);
    }

    @Operation(summary = "Get All Doctors", description = "Retrieve a list of all doctors, optionally filtered by specialization")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Doctors retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<List<DoctorResponseDTO>> getAllDoctors (@RequestParam(required = false) String specialization){

        List<DoctorResponseDTO> doctors = doctorService.getAllDoctor(specialization);

        return ResponseEntity.ok(doctors);

    }

    @Operation(summary = "Update Doctor", description = "Update the details of an existing doctor")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Doctor updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input or validation error"),
            @ApiResponse(responseCode = "404", description = "Doctor not found")
    })
    @PutMapping("/{doctorId}")
    public ResponseEntity<DoctorResponseDTO> updateDoctor (@PathVariable Long doctorId, @RequestBody @Valid DoctorRequestDTO doctorRequestDTO){

        DoctorResponseDTO doctorResponseDTO = doctorService.updateDoctor(doctorId, doctorRequestDTO);

        return ResponseEntity.ok(doctorResponseDTO);
    }

    @Operation(summary = "Delete Doctor", description = "Remove a doctor from the system")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Doctor deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Doctor not found")
    })
    @DeleteMapping("/{doctorId}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long doctorId){
        doctorService.deleteDoctor(doctorId);

        return ResponseEntity.noContent().build();
    }


}
