package com.example.medicore.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "device_readings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeviceReading {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many readings belong to one device
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id", nullable = false)
    @NotNull
    private IoTDevice device;

    @NotNull(message = "Reading value is required")
    @Column(nullable = false)
    private Double value;

    @NotBlank(message = "Unit is required (e.g. bpm, °C, %SpO2)")
    @Column(nullable = false)
    private String unit;

    @Column(name = "recorded_at", nullable = false)
    @Builder.Default
    private LocalDateTime recordedAt = LocalDateTime.now();

    // true = anomaly flagged by scheduled job
    @Column(name = "is_anomaly")
    @Builder.Default
    private Boolean isAnomaly = false;

    @Column(columnDefinition = "TEXT")
    private String notes;
}