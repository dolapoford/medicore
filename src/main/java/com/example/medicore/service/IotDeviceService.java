package com.example.medicore.service;

import com.example.medicore.dto.iot.DeviceReadingRequestDTO;
import com.example.medicore.dto.iot.DeviceReadingResponseDTO;
import com.example.medicore.dto.iot.IoTDeviceRequestDTO;
import com.example.medicore.dto.iot.IoTDeviceResponseDTO;
import com.example.medicore.entity.DeviceReading;
import com.example.medicore.entity.IoTDevice;
import com.example.medicore.entity.Patient;
import com.example.medicore.mapper.DeviceReadingMapper;
import com.example.medicore.mapper.IoTDeviceMapper;
import com.example.medicore.repository.DeviceReadingRepository;
import com.example.medicore.repository.IoTDeviceRepository;
import com.example.medicore.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IotDeviceService {

    private final IoTDeviceRepository ioTDeviceRepository;
    private final PatientRepository patientRepository;
    private final IoTDeviceMapper ioTDeviceMapper;
    private final AnomalyDetectionService anomalyDetectionService;
    private final DeviceReadingRepository deviceReadingRepository;
    private final DeviceReadingMapper deviceReadingMapper;

    public IoTDeviceResponseDTO registerDevice(Long patientId, IoTDeviceRequestDTO ioTDeviceRequestDTO){
        if (ioTDeviceRepository.existsBySerialNumber(ioTDeviceRequestDTO.getSerialNumber())) {
            throw new RuntimeException("Device with this serial number already exists");
        }

        Patient patient = patientRepository.findById(patientId).orElseThrow(() -> new RuntimeException("Patient not found"));

        IoTDevice ioTDevice = IoTDevice.builder()
                .name(ioTDeviceRequestDTO.getName())
                .serialNumber(ioTDeviceRequestDTO.getSerialNumber())
                .patient(patient)
                .deviceType(ioTDeviceRequestDTO.getDeviceType())
                .status(IoTDevice.DeviceStatus.ACTIVE)
                .build();
        ioTDeviceRepository.save(ioTDevice);

        return ioTDeviceMapper.toDTO(ioTDevice);




    }


    public IoTDeviceResponseDTO assignDeviceToPatient(Long patientId, Long deviceId){
        Patient patient = patientRepository.findById(patientId).orElseThrow(()-> new RuntimeException("Patient not found"));

        IoTDevice ioTDevice = ioTDeviceRepository.findById(deviceId).orElseThrow(()-> new RuntimeException("Device not found"));

        ioTDevice.setPatient(patient);

        ioTDeviceRepository.save(ioTDevice);

        return ioTDeviceMapper.toDTO(ioTDevice);
    }

    public DeviceReadingResponseDTO ingestReading(Long deviceId, DeviceReadingRequestDTO deviceReadingRequestDTO){

        IoTDevice ioTDevice = ioTDeviceRepository.findById(deviceId).orElseThrow(()-> new RuntimeException("Device not found"));

        if (ioTDevice.getStatus() != IoTDevice.DeviceStatus.ACTIVE) {
            throw new RuntimeException("Device is not active");
        }

        DeviceReading reading = DeviceReading.builder()
                .device(ioTDevice)
                .value(deviceReadingRequestDTO.getValue())
                .unit(deviceReadingRequestDTO.getUnit())
                .notes(deviceReadingRequestDTO.getNotes())
                .build();

        DeviceReading savedReading = deviceReadingRepository.save(reading);


        anomalyDetectionService.checkReading(savedReading);

        return deviceReadingMapper.toDTO(savedReading);
    }






}
