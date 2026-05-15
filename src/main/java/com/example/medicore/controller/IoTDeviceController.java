package com.example.medicore.controller;

import com.example.medicore.dto.iot.DeviceReadingRequestDTO;
import com.example.medicore.dto.iot.DeviceReadingResponseDTO;
import com.example.medicore.dto.iot.IoTDeviceRequestDTO;
import com.example.medicore.dto.iot.IoTDeviceResponseDTO;
import com.example.medicore.service.IotDeviceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/iot")
@RequiredArgsConstructor
@Tag(name = "IoT Devices", description = "Endpoints for managing IoT devices and telemetry data")
public class IoTDeviceController {

    private final IotDeviceService iotDeviceService;

    @Operation(summary = "Register Device", description = "Register a new IoT device and assign it to a patient")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Device registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Patient not found")
    })
    @PostMapping("/register/{patientId}")
    public ResponseEntity<IoTDeviceResponseDTO> registerDevice(@RequestBody @Valid IoTDeviceRequestDTO ioTDeviceRequestDTO, @PathVariable Long patientId){
        IoTDeviceResponseDTO  ioTDeviceResponseDTO = iotDeviceService.registerDevice(patientId,ioTDeviceRequestDTO);

        return ResponseEntity.ok(ioTDeviceResponseDTO);
    }


    @Operation(summary = "Assign Device to Patient", description = "Assign an existing IoT device to a specific patient")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Device assigned successfully"),
            @ApiResponse(responseCode = "404", description = "Device or Patient not found")
    })
    @PutMapping("/assign/{deviceId}/patient/{patientId}")
    public ResponseEntity<IoTDeviceResponseDTO> assignDeviceToPatient(@PathVariable Long deviceId, @PathVariable Long patientId){
        IoTDeviceResponseDTO ioTDeviceResponseDTO = iotDeviceService.assignDeviceToPatient(patientId, deviceId);

        return ResponseEntity.ok(ioTDeviceResponseDTO);
    }

    @Operation(summary = "Ingest Device Reading", description = "Submit a new reading from an IoT device")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reading ingested successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Device not found")
    })
    @PostMapping("/ingest/{deviceId}")
    public ResponseEntity<DeviceReadingResponseDTO> ingestDevice(@PathVariable  Long deviceId, @RequestBody @Valid DeviceReadingRequestDTO deviceReadingRequestDTO){
        DeviceReadingResponseDTO deviceReadingResponseDTO = iotDeviceService.ingestReading(deviceId,deviceReadingRequestDTO);

        return ResponseEntity.ok(deviceReadingResponseDTO);
    }

}
