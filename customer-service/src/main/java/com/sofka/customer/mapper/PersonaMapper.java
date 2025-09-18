package com.sofka.customer.mapper;

import com.sofka.customer.dto.PersonaDTO;
import com.sofka.customer.model.Persona;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PersonaMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Persona toEntity(PersonaDTO personaDTO);

    PersonaDTO toDTO(Persona persona);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Persona updateEntity(PersonaDTO personaDTO, @MappingTarget Persona persona);
}
