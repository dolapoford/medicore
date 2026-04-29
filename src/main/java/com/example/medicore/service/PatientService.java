package com.example.medicore.service;

import com.example.medicore.dto.request.PatientRequestDTO;
import com.example.medicore.dto.response.PatientResponseDTO;
import com.example.medicore.entity.Patient;
import com.example.medicore.mapper.PatientMapper;
import com.example.medicore.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;
  private final PatientMapper patientMapper;

    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO){
        if(patientRepository.existsByEmail(patientRequestDTO.getEmail())){
            throw new RuntimeException("Patient with this email already exist");
        }
        Patient patient = Patient.builder()
                .firstName(patientRequestDTO.getFirstName())
                .lastName(patientRequestDTO.getLastName())
                .email(patientRequestDTO.getEmail())
                .address(patientRequestDTO.getAddress())
                .gender(patientRequestDTO.getGender())
                .dateOfBirth(patientRequestDTO.getDateOfBirth())
                .phoneNumber(patientRequestDTO.getPhoneNumber())
                .build();

        patientRepository.save(patient);

        return patientMapper.toDTO(patient);
    }

    public PatientResponseDTO getPatientById(Long id){

    Patient patient = patientRepository.findById(id).orElseThrow(()-> new RuntimeException("Patient Not Found"));

    return patientMapper.toDTO(patient);

    }

    public Page<PatientResponseDTO> getAllPatient(Pageable page, String firstName, String lastName) {

        Page<Patient> patients;

        if ((firstName != null && !firstName.isBlank()) || (lastName != null && !lastName.isBlank())) {
            patients = patientRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(firstName, lastName, page);
        } else {
            patients = patientRepository.findAll(page);
        }

        return patients.map(patientMapper::toDTO);
    }


    public PatientResponseDTO updatePatient(Long id , PatientRequestDTO patientRequestDTO){

        Patient patient = patientRepository.findById(id).orElseThrow(()-> new RuntimeException("Patient not found"));

        if (patientRequestDTO.getEmail() != null || !patientRequestDTO.getEmail().equals(patient.getEmail()) ){
            if (patientRepository.existsByEmail(patientRequestDTO.getEmail())){
                throw new RuntimeException("Email Already exist");
            }
            patient.setEmail(patientRequestDTO.getEmail());
        }

        patient.setGender(patientRequestDTO.getGender());
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setFirstName(patientRequestDTO.getFirstName());
        patient.setLastName(patientRequestDTO.getLastName());
        patient.setDateOfBirth(patientRequestDTO.getDateOfBirth());
        patient.setPhoneNumber(patientRequestDTO.getPhoneNumber());

        patientRepository.save(patient);

        return patientMapper.toDTO(patient);

    }

    public void deletePatient(Long id){
       if (!patientRepository.existsById(id)){
           throw new RuntimeException("Patient not found");
       }
        patientRepository.deleteById(id);
    }
}
