package com.example.medicore.repository;

import com.example.medicore.entity.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment,Long> {
    Page<Appointment> findByPatientId(Long patientId, Pageable pageable);
    Page<Appointment> findByDoctorId(Long doctorId, Pageable pageable);
    Page<Appointment> findByStatus(Appointment.AppointmentStatus status, Pageable pageable);

    //Check for scheduling conflicts: same doctor, overlapping 1-hour window

    @Query("SELECT COUNT(a) > 0 FROM Appointment a WHERE a.doctor.id = :doctorId AND a.status != 'CANCALLED' AND a.scheduledAt BETWEEN :start AND :end")
    boolean existsConflict(
            @Param("doctorId") Long doctorId,
            @Param("start")LocalDateTime start,
            @Param("end") LocalDateTime end
            );


}
