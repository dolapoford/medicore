package com.example.medicore.controller;

import com.example.medicore.dto.request.PatientRequestDTO;
import com.example.medicore.dto.response.PatientResponseDTO;
import com.example.medicore.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/patients")
public class PatientController {

    private final PatientService patientService;


    @PostMapping
    public ResponseEntity<PatientResponseDTO> createPatient(@RequestBody PatientRequestDTO patientRequestDTO){
        PatientResponseDTO patientResponseDTO = patientService.createPatient(patientRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(patientResponseDTO);

    }


    @GetMapping
    public ResponseEntity<Page<PatientResponseDTO>> getAllPatient(@RequestParam Pageable pageable, @RequestParam String firstName, @RequestParam String lastName){

        Page<PatientResponseDTO> patients = patientService.getAllPatient(pageable,firstName,lastName);

        return ResponseEntity.ok(patients);
    }

    @GetMapping("/public")
    public String displayPublic(){
        return "This is a public request";
    }

}
