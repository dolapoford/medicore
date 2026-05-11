package com.example.medicore.repository;

import com.example.medicore.entity.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRespository extends JpaRepository<Doctor,Long> {
    Optional<Doctor> findByEmail(String email);
    boolean existsByEmail(String email);
    List<Doctor> findBySpecialization(String specialization);
}
