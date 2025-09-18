package com.sofka.customer.service;

import com.sofka.customer.dto.PersonaDTO;
import com.sofka.customer.exception.ResourceNotFoundException;
import com.sofka.customer.exception.ResourceConflictException;
import com.sofka.customer.mapper.PersonaMapper;
import com.sofka.customer.model.Persona;
import com.sofka.customer.repository.PersonaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PersonaService {

    private final PersonaRepository personaRepository;
    private final PersonaMapper personaMapper;

    public PersonaDTO crearPersona(PersonaDTO personaDTO) {
        if (personaRepository.existsByIdentificacion(personaDTO.getIdentificacion())) {
            throw new ResourceConflictException("Ya existe una persona con la identificación: " + personaDTO.getIdentificacion());
        }

        Persona persona = personaMapper.toEntity(personaDTO);
        Persona personaGuardada = personaRepository.save(persona);
        return personaMapper.toDTO(personaGuardada);
    }

    @Transactional(readOnly = true)
    public List<PersonaDTO> obtenerTodasLasPersonas() {
        return personaRepository.findAll()
                .stream()
                .map(personaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PersonaDTO obtenerPersonaPorId(Long id) {
        Persona persona = personaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Persona no encontrada con ID: " + id));
        return personaMapper.toDTO(persona);
    }

    @Transactional(readOnly = true)
    public PersonaDTO obtenerPersonaPorIdentificacion(String identificacion) {
        Persona persona = personaRepository.findByIdentificacion(identificacion)
                .orElseThrow(() -> new ResourceNotFoundException("Persona no encontrada con identificación: " + identificacion));
        return personaMapper.toDTO(persona);
    }

    public PersonaDTO actualizarPersona(Long id, PersonaDTO personaDTO) {
        Persona personaExistente = personaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Persona no encontrada con ID: " + id));

        // Verificar si la nueva identificación ya existe en otra persona
        if (!personaExistente.getIdentificacion().equals(personaDTO.getIdentificacion()) &&
            personaRepository.existsByIdentificacion(personaDTO.getIdentificacion())) {
            throw new ResourceConflictException("Ya existe una persona con la identificación: " + personaDTO.getIdentificacion());
        }

        personaMapper.updateEntity(personaDTO, personaExistente);
        Persona personaActualizada = personaRepository.save(personaExistente);
        return personaMapper.toDTO(personaActualizada);
    }

    public void eliminarPersona(Long id) {
        if (!personaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Persona no encontrada con ID: " + id);
        }
        personaRepository.deleteById(id);
    }
}
