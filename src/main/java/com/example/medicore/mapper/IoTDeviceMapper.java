package com.example.medicore.mapper;

import com.example.medicore.dto.iot.IoTDeviceRequestDTO;
import com.example.medicore.dto.iot.IoTDeviceResponseDTO;
import com.example.medicore.entity.IoTDevice;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface IoTDeviceMapper {

    @Mapping(target = "patient", ignore = true)
    IoTDevice toEntity(IoTDeviceRequestDTO ioTDeviceRequestDTO);

    @Mapping(source = "patient.id", target = "patientId")
    IoTDeviceResponseDTO toDTO(IoTDevice ioTDevice);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "patient", ignore = true)
    void updateEntityFromDTO(IoTDeviceRequestDTO ioTDeviceRequestDTO, @MappingTarget IoTDevice ioTDevice);

}
