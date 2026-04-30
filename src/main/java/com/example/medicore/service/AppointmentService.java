package com.example.medicore.service;

import com.example.medicore.dto.request.AppointmentRequestDTO;
import com.example.medicore.dto.response.AppointmentResponseDTO;
import com.example.medicore.entity.Appointment;
import com.example.medicore.entity.Doctor;
import com.example.medicore.entity.Patient;
import com.example.medicore.mapper.AppointmentMapper;
import com.example.medicore.repository.AppointmentRepository;
import com.example.medicore.repository.DoctorRespository;
import com.example.medicore.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final DoctorRespository doctorRespository;
    private final PatientRepository patientRepository;
    private final AppointmentMapper appointmentMapper;


    public AppointmentResponseDTO createAppointment(AppointmentRequestDTO appointmentRequestDTO){

        Patient patient = patientRepository.findById(appointmentRequestDTO.getPatientId()).orElseThrow(()->new RuntimeException("Patient not found"));
        Doctor doctor = doctorRespository.findById(appointmentRequestDTO.getDoctorId()).orElseThrow(()-> new RuntimeException("Doctor not found"));

        LocalDateTime start = appointmentRequestDTO.getScheduledAt().minusHours(1);
        LocalDateTime end = appointmentRequestDTO.getScheduledAt().plusHours(1);

        if(appointmentRepository.existsConflict(doctor.getId(), start,end)){
            throw new RuntimeException("The doctor has an appointment at that specific time");
        }

        Appointment appointment = Appointment.builder()
                .patient(patient)
                .doctor(doctor)
                .status(Appointment.AppointmentStatus.SCHEDULED)
                .notes(appointmentRequestDTO.getNotes())
                .scheduledAt(appointmentRequestDTO.getScheduledAt())
                .build();
        appointmentRepository.save(appointment);

        return appointmentMapper.toDTO(appointment);
    }


    public AppointmentResponseDTO updateAppointment(Long id, AppointmentRequestDTO appointmentRequestDTO){

        Appointment appointment = appointmentRepository.findById(id).orElseThrow(()-> new RuntimeException("Appointment not found"));

        appointment.setStatus(Appointment.AppointmentStatus.COMPLETED);
        appointmentRepository.save(appointment);

        return appointmentMapper.toDTO(appointment);
    }


    public AppointmentResponseDTO cancelAppointment(Long id, AppointmentRequestDTO appointmentRequestDTO){
        Appointment appointment = appointmentRepository.findById(id).orElseThrow(()-> new RuntimeException("Appointment not found"));

        appointment.setStatus(Appointment.AppointmentStatus.CANCELLED);

        appointmentRepository.save(appointment);

        return appointmentMapper.toDTO(appointment);
    }


}
