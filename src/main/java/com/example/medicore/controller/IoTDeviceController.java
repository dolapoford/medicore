package com.example.medicore.controller;

import com.example.medicore.dto.iot.DeviceReadingRequestDTO;
import com.example.medicore.dto.iot.DeviceReadingResponseDTO;
import com.example.medicore.dto.iot.IoTDeviceRequestDTO;
import com.example.medicore.dto.iot.IoTDeviceResponseDTO;
import com.example.medicore.service.IotDeviceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/iot")
@RequiredArgsConstructor
public class IoTDeviceController {

    private final IotDeviceService iotDeviceService;


    @PostMapping("/register/{patientId}")
    public ResponseEntity<IoTDeviceResponseDTO> registerDevice(@RequestBody @Valid IoTDeviceRequestDTO ioTDeviceRequestDTO, @PathVariable Long patientId){
        IoTDeviceResponseDTO  ioTDeviceResponseDTO = iotDeviceService.registerDevice(patientId,ioTDeviceRequestDTO);

        return ResponseEntity.ok(ioTDeviceResponseDTO);
    }


    @PutMapping("/assign/{deviceId}/patient/{patientId}")
    public ResponseEntity<IoTDeviceResponseDTO> assignDeviceToPatient(@PathVariable Long deviceId, @PathVariable Long patientId){
        IoTDeviceResponseDTO ioTDeviceResponseDTO = iotDeviceService.assignDeviceToPatient(deviceId, patientId);

        return ResponseEntity.ok(ioTDeviceResponseDTO);
    }

    @PostMapping("/ingest/{deviceId}")
    public ResponseEntity<DeviceReadingResponseDTO> ingestDevice(@PathVariable  Long deviceId, @RequestBody @Valid DeviceReadingRequestDTO deviceReadingRequestDTO){
        DeviceReadingResponseDTO deviceReadingResponseDTO = iotDeviceService.ingestReading(deviceId,deviceReadingRequestDTO);

        return ResponseEntity.ok(deviceReadingResponseDTO);
    }

}
