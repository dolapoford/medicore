package com.example.medicore.mapper;

import com.example.medicore.dto.request.PatientRequestDTO;
import com.example.medicore.dto.response.PatientResponseDTO;
import com.example.medicore.entity.Patient;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "string")
public interface PatientMapper {

    Patient toEntity(PatientRequestDTO patientRequestDTO);
    PatientResponseDTO toDTO(Patient patient);

    //For Put: only update non-null fields from the DTO
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDTO(PatientRequestDTO patientRequestDTO , @MappingTarget Patient patient);
}
