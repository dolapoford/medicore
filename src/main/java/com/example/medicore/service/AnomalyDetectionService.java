package com.example.medicore.service;

import com.example.medicore.dto.iot.DeviceReadingRequestDTO;
import com.example.medicore.dto.iot.DeviceReadingResponseDTO;
import com.example.medicore.entity.DeviceReading;
import com.example.medicore.entity.IoTDevice;
import com.example.medicore.repository.DeviceReadingRepository;
import com.example.medicore.repository.IoTDeviceRepository;
import com.example.medicore.utility.AlertEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AnomalyDetectionService {

    private final DeviceReadingRepository deviceReadingRepository;
    private final IoTDeviceRepository ioTDeviceRepository;
    private final ApplicationEventPublisher applicationEventPublisher;


    @Async
    public void checkReading(DeviceReading reading){
        IoTDevice.DeviceType deviceType = reading.getDevice().getDeviceType();
        double value = reading.getValue();
        boolean isAnomaly = false;

        switch (deviceType){
            case PULSE_OXIMETER -> {
                if (value < 95){
                    isAnomaly = true;
                }
            }

            case THERMOMETER -> {
                if (value <36.1 || value > 37.2){
                    isAnomaly = true;
                }
            }
            case BLOOD_PRESSURE_MONITOR -> {
                if (value>140){
                    isAnomaly =true;
                }
            }

            case GLUCOSE_METER -> {
                if(value < 70 || value > 140){
                    isAnomaly = true;
                }
            }

            default -> log.info("No Threshold defined for device type {}", deviceType);
        }

        if (isAnomaly){
            reading.setAnomaly(true);
            deviceReadingRepository.save(reading);
            log.warn("Anomaly detected for device {} with value {}", reading.getId(), value);
        }

        applicationEventPublisher.publishEvent(new AlertEvent(this, reading));
    }
}
