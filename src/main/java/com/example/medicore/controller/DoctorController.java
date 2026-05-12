package com.example.medicore.controller;

import com.example.medicore.dto.request.DoctorRequestDTO;
import com.example.medicore.dto.response.DoctorResponseDTO;
import com.example.medicore.service.DoctorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctor")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;


    @PostMapping
    public ResponseEntity<DoctorResponseDTO> createDoctor(@RequestBody @Valid DoctorRequestDTO doctorRequestDTO){
        DoctorResponseDTO doctorResponseDTO = doctorService.createDoctor(doctorRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(doctorResponseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorResponseDTO> getDoctorById(@PathVariable  Long id){
        DoctorResponseDTO doctorResponseDTO = doctorService.getDoctorById(id);

        return ResponseEntity.ok(doctorResponseDTO);
    }

    @GetMapping
    public ResponseEntity<List<DoctorResponseDTO>> getAllPatient(@RequestParam(required = false) String specialization){

        List<DoctorResponseDTO> doctors = doctorService.getAllDoctor(specialization);

        return ResponseEntity.ok(doctors);

    }

    @PutMapping("/{doctorId}")
    public ResponseEntity<DoctorResponseDTO> updatePatient(@PathVariable Long doctorId, @RequestBody @Valid DoctorRequestDTO doctorRequestDTO){

        DoctorResponseDTO doctorResponseDTO = doctorService.updateDoctor(doctorId, doctorRequestDTO);

        return ResponseEntity.ok(doctorResponseDTO);
    }

    @DeleteMapping("/{doctorId}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long doctorId){
        doctorService.deleteDoctor(doctorId);

        return ResponseEntity.noContent().build();
    }


}
