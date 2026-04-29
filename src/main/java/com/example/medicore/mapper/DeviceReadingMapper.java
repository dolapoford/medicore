package com.example.medicore.mapper;

import com.example.medicore.dto.iot.DeviceReadingRequestDTO;
import com.example.medicore.dto.iot.DeviceReadingResponseDTO;
import com.example.medicore.entity.DeviceReading;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DeviceReadingMapper {

    @Mapping(target = "device", ignore = true)
    DeviceReading toEntity(DeviceReadingRequestDTO deviceReadingRequestDTO);

    @Mapping(source = "device.id", target = "deviceId")
    DeviceReadingResponseDTO toDTO(DeviceReading deviceReading);
}
