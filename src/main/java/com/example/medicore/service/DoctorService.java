package com.example.medicore.service;

import com.example.medicore.dto.request.DoctorRequestDTO;
import com.example.medicore.dto.response.DoctorResponseDTO;
import com.example.medicore.entity.Doctor;
import com.example.medicore.mapper.DoctorMapper;
import com.example.medicore.repository.AppointmentRepository;
import com.example.medicore.repository.DoctorRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.data.autoconfigure.web.DataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRespository doctorRespository;
    private final AppointmentRepository appointmentRepository;
    private final DoctorMapper doctorMapper;


    public DoctorResponseDTO createDoctor(DoctorRequestDTO doctorRequestDTO){
        if (doctorRespository.existsByEmail(doctorRequestDTO.getEmail())){
            throw new RuntimeException("Doctor already exist");
        }

        Doctor doctor = Doctor.builder()
                .firstName(doctorRequestDTO.getFirstName())
                .lastName(doctorRequestDTO.getLastName())
                .licenseNumber(doctorRequestDTO.getLicenseNumber())
                .specialization(doctorRequestDTO.getSpecialization())
                .phoneNumber(doctorRequestDTO.getPhoneNumber())
                .email(doctorRequestDTO.getEmail())
                .build();

        doctorRespository.save(doctor);

        return doctorMapper.toDTO(doctor);

    }

    public DoctorResponseDTO getDoctorById(Long id){
        Doctor doctor = doctorRespository.findById(id).orElseThrow(()-> new RuntimeException("Doctor not found"));

        return doctorMapper.toDTO(doctor);
    }

    public List<DoctorResponseDTO> getAllDoctor(String specialization){
        List<Doctor> doctors;

        if (specialization != null && !specialization.isBlank()){
            doctors = doctorRespository.findBySpecialization(specialization);
        }else{
            doctors = doctorRespository.findAll();
        }

       return doctors.stream().map(doctorMapper::toDTO).toList();


    }

    public DoctorResponseDTO updateDoctor(Long id, DoctorRequestDTO doctorRequestDTO){

        Doctor doctor = doctorRespository.findById(id).orElseThrow(()-> new RuntimeException("Doctor not found"));

        if (doctorRequestDTO.getEmail() != null && !doctorRequestDTO.getEmail().equals(doctor.getEmail())){
            if (doctorRespository.existsByEmail(doctorRequestDTO.getEmail())){
                throw  new RuntimeException("Email already exist");
            }

            doctor.setEmail(doctorRequestDTO.getEmail());
        }

        doctor.setFirstName(doctorRequestDTO.getFirstName());
        doctor.setLastName(doctorRequestDTO.getLastName());
        doctor.setLicenseNumber(doctorRequestDTO.getLicenseNumber());
        doctor.setPhoneNumber(doctorRequestDTO.getPhoneNumber());
        doctor.setSpecialization(doctorRequestDTO.getSpecialization());

        doctorRespository.save(doctor);

        return doctorMapper.toDTO(doctor);
    }

    public void deleteDoctor(Long id){
        Doctor doctor = doctorRespository.findById(id).orElseThrow(()-> new RuntimeException("Doctor not found"));

        doctorRespository.deleteById(doctor.getId());
    }
}
