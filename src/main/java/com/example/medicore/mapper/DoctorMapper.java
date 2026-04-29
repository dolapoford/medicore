package com.example.medicore.mapper;

import com.example.medicore.dto.request.DoctorRequestDTO;
import com.example.medicore.dto.response.DoctorResponseDTO;
import com.example.medicore.entity.Doctor;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "string")
public interface DoctorMapper {
    Doctor toEntity(DoctorRequestDTO doctorRequestDTO);
    DoctorResponseDTO toDTO(Doctor doctor);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDTO(DoctorRequestDTO doctorRequestDTO, @MappingTarget Doctor doctor);
}
