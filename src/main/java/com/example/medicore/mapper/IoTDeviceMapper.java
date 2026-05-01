package com.example.medicore.mapper;

import com.example.medicore.dto.iot.IoTDeviceRequestDTO;
import com.example.medicore.dto.iot.IoTDeviceResponseDTO;
import com.example.medicore.entity.IoTDevice;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface IoTDeviceMapper {

    @Mapping(target = "patient", ignore = true)
    IoTDevice toEntity(IoTDeviceRequestDTO dto);

    @Mapping(target = "patientId",
            expression = "java(device.getPatient() != null ? device.getPatient().getId() : null)")
    IoTDeviceResponseDTO toDTO(IoTDevice device);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "patient", ignore = true)
    void updateEntityFromDTO(IoTDeviceRequestDTO dto, @MappingTarget IoTDevice device);
}