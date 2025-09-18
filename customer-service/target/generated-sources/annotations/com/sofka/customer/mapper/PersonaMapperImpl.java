package com.sofka.customer.mapper;

import com.sofka.customer.dto.PersonaDTO;
import com.sofka.customer.model.Persona;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-18T13:16:58-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.16 (Homebrew)"
)
@Component
public class PersonaMapperImpl implements PersonaMapper {

    @Override
    public Persona toEntity(PersonaDTO personaDTO) {
        if ( personaDTO == null ) {
            return null;
        }

        Persona persona = new Persona();

        persona.setNombre( personaDTO.getNombre() );
        persona.setGenero( personaDTO.getGenero() );
        persona.setEdad( personaDTO.getEdad() );
        persona.setIdentificacion( personaDTO.getIdentificacion() );
        persona.setDireccion( personaDTO.getDireccion() );
        persona.setTelefono( personaDTO.getTelefono() );

        return persona;
    }

    @Override
    public PersonaDTO toDTO(Persona persona) {
        if ( persona == null ) {
            return null;
        }

        PersonaDTO personaDTO = new PersonaDTO();

        personaDTO.setId( persona.getId() );
        personaDTO.setNombre( persona.getNombre() );
        personaDTO.setGenero( persona.getGenero() );
        personaDTO.setEdad( persona.getEdad() );
        personaDTO.setIdentificacion( persona.getIdentificacion() );
        personaDTO.setDireccion( persona.getDireccion() );
        personaDTO.setTelefono( persona.getTelefono() );
        personaDTO.setCreatedAt( persona.getCreatedAt() );
        personaDTO.setUpdatedAt( persona.getUpdatedAt() );

        return personaDTO;
    }

    @Override
    public Persona updateEntity(PersonaDTO personaDTO, Persona persona) {
        if ( personaDTO == null ) {
            return persona;
        }

        persona.setNombre( personaDTO.getNombre() );
        persona.setGenero( personaDTO.getGenero() );
        persona.setEdad( personaDTO.getEdad() );
        persona.setIdentificacion( personaDTO.getIdentificacion() );
        persona.setDireccion( personaDTO.getDireccion() );
        persona.setTelefono( personaDTO.getTelefono() );

        return persona;
    }
}
