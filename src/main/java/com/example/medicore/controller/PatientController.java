package com.example.medicore.controller;

import com.example.medicore.dto.request.PatientRequestDTO;
import com.example.medicore.dto.response.PatientResponseDTO;
import com.example.medicore.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/patients")
@Tag(name = "Patient", description = "Manage clinic patients")
public class PatientController {

    private final PatientService patientService;

    @Operation(summary = "Create a new patient")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Patient Created Successfully"),
            @ApiResponse(responseCode = "400", description = "Validation error"),
            @ApiResponse(responseCode = "409", description = "Email already in use")
    })
    @PostMapping
    public ResponseEntity<PatientResponseDTO> createPatient(@RequestBody @Valid PatientRequestDTO patientRequestDTO){
        PatientResponseDTO patientResponseDTO = patientService.createPatient(patientRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(patientResponseDTO);

    }

    @Operation(summary = "Get All Patient with optional search and pagination")
    @GetMapping
    public ResponseEntity<Page<PatientResponseDTO>> getAllPatient( Pageable pageable, @RequestParam(required = false) String firstName, @RequestParam(required = false) String lastName){

        Page<PatientResponseDTO> patients = patientService.getAllPatient(pageable,firstName,lastName);

        return ResponseEntity.ok(patients);
    }

    @Operation(summary = "Get Patient by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patient found"),
            @ApiResponse(responseCode = "404", description = "Patient not found")
    })
    @GetMapping("/{patientId}")
   public ResponseEntity<PatientResponseDTO> getPatientById(@PathVariable Long patientId){
        PatientResponseDTO patientResponseDTO = patientService.getPatientById(patientId);

        return ResponseEntity.ok(patientResponseDTO);
   }


   @Operation(summary = "Update Patient by id")
   @ApiResponses({
           @ApiResponse(responseCode = "200", description = "Patient Updated"),
           @ApiResponse(responseCode = "404",description = "Patient Not found")
   })
   @PutMapping("/{patientId}")
   public ResponseEntity<PatientResponseDTO> updatePatient(@PathVariable Long patientId, @RequestBody @Valid PatientRequestDTO patientRequestDTO) {
       PatientResponseDTO patientResponseDTO = patientService.updatePatient(patientId, patientRequestDTO);

       return ResponseEntity.ok(patientResponseDTO);

   }



    @GetMapping("/public")
    public String displayPublic(){
        return "This is a public request";
    }

}
