package com.example.medicore.mapper;

import com.example.medicore.dto.request.AppointmentRequestDTO;
import com.example.medicore.dto.response.AppointmentResponseDTO;
import com.example.medicore.entity.Appointment;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {

    @Mapping(target = "patient", ignore = true)
    @Mapping(target = "doctor" , ignore = true)
    Appointment toEntity(AppointmentRequestDTO appointmentRequestDTO);

    AppointmentResponseDTO toDTO(Appointment appointment);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "patient", ignore = true)
    @Mapping(target = "doctor" , ignore = true)
    void updateEntityFromDTO(AppointmentRequestDTO appointmentRequestDTO ,@MappingTarget Appointment appointment );


}
