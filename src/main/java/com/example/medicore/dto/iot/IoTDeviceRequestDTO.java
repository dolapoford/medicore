package com.example.medicore.dto.iot;

import com.example.medicore.entity.IoTDevice;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class IoTDeviceRequestDTO {

    @NotBlank(message = "Serial number is required")
    private String serialNumber;

    @NotBlank
    private String name;

    @NotNull
    private IoTDevice.DeviceType deviceType;

    private Long patientId;

}
