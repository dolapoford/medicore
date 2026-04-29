package com.example.medicore.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "iot_devices")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IoTDevice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Device serial number is required")
    @Column(name = "serial_number", unique = true, nullable = false)
    private String serialNumber;

    @NotBlank(message = "Device name is required")
    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "device_type", nullable = false)
    private DeviceType deviceType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private DeviceStatus status = DeviceStatus.ACTIVE;

    // Many devices can belong to one patient
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @Column(name = "registered_at", updatable = false)
    private LocalDateTime registeredAt;

    // One device has many readings
    @OneToMany(mappedBy = "device", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @Builder.Default
    private List<DeviceReading> readings = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        this.registeredAt = LocalDateTime.now();
    }

    public enum DeviceType {
        THERMOMETER,
        BLOOD_PRESSURE_MONITOR,
        PULSE_OXIMETER,
        GLUCOSE_METER,
        ECG_MONITOR
    }

    public enum DeviceStatus {
        ACTIVE,
        INACTIVE,
        MAINTENANCE
    }
}
