package com.example.medicore.dto.iot;

import com.example.medicore.entity.IoTDevice;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class IoTDeviceResponseDTO {
    private Long id;
    private String serialNumber;
    private String name;
    private IoTDevice.DeviceType deviceType;
    private IoTDevice.DeviceStatus deviceStatus;
    private Long patientId;
    private LocalDateTime registeredAt;
}
