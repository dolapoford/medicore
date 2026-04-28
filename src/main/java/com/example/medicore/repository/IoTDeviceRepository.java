package com.example.medicore.repository;

import com.example.medicore.entity.IoTDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IoTDeviceRepository extends JpaRepository<IoTDevice,Long> {
    Optional<IoTDevice> findBySerialNumber(String serialNumber);

    List<IoTDevice> findByPatientId(Long patientId);

    List<IoTDevice> findByStatus(IoTDevice.DeviceStatus status);
}