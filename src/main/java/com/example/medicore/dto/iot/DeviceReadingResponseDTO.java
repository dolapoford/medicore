package com.example.medicore.dto.iot;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DeviceReadingResponseDTO {
    private Long id;
    private Long deviceId;
    private Double value;
    private String unit;
    private LocalDateTime recordedAt;
    private Boolean isAnomaly;
    private String notes;
}
