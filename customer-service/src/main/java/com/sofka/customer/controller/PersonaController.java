package com.sofka.customer.controller;

import com.sofka.customer.dto.PersonaDTO;
import com.sofka.customer.service.PersonaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/personas")
@RequiredArgsConstructor
@Tag(name = "Personas", description = "API para gestión de personas")
public class PersonaController {

    private final PersonaService personaService;

    @PostMapping
    @Operation(summary = "Crear una nueva persona", description = "Crea una nueva persona en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Persona creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "409", description = "Conflicto: identificación ya existe")
    })
    public ResponseEntity<PersonaDTO> crearPersona(@Valid @RequestBody PersonaDTO personaDTO) {
        PersonaDTO personaCreada = personaService.crearPersona(personaDTO);
        return new ResponseEntity<>(personaCreada, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Obtener todas las personas", description = "Retorna una lista de todas las personas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de personas obtenida exitosamente")
    })
    public ResponseEntity<List<PersonaDTO>> obtenerTodasLasPersonas() {
        List<PersonaDTO> personas = personaService.obtenerTodasLasPersonas();
        return ResponseEntity.ok(personas);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener persona por ID", description = "Retorna una persona específica por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Persona encontrada"),
            @ApiResponse(responseCode = "404", description = "Persona no encontrada")
    })
    public ResponseEntity<PersonaDTO> obtenerPersonaPorId(
            @Parameter(description = "ID de la persona") @PathVariable Long id) {
        PersonaDTO persona = personaService.obtenerPersonaPorId(id);
        return ResponseEntity.ok(persona);
    }

    @GetMapping("/identificacion/{identificacion}")
    @Operation(summary = "Obtener persona por identificación", description = "Retorna una persona específica por su identificación")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Persona encontrada"),
            @ApiResponse(responseCode = "404", description = "Persona no encontrada")
    })
    public ResponseEntity<PersonaDTO> obtenerPersonaPorIdentificacion(
            @Parameter(description = "Identificación de la persona") @PathVariable String identificacion) {
        PersonaDTO persona = personaService.obtenerPersonaPorIdentificacion(identificacion);
        return ResponseEntity.ok(persona);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar persona", description = "Actualiza los datos de una persona existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Persona actualizada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "Persona no encontrada"),
            @ApiResponse(responseCode = "409", description = "Conflicto: identificación ya existe")
    })
    public ResponseEntity<PersonaDTO> actualizarPersona(
            @Parameter(description = "ID de la persona") @PathVariable Long id,
            @Valid @RequestBody PersonaDTO personaDTO) {
        PersonaDTO personaActualizada = personaService.actualizarPersona(id, personaDTO);
        return ResponseEntity.ok(personaActualizada);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar persona", description = "Elimina una persona del sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Persona eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Persona no encontrada")
    })
    public ResponseEntity<Void> eliminarPersona(
            @Parameter(description = "ID de la persona") @PathVariable Long id) {
        personaService.eliminarPersona(id);
        return ResponseEntity.noContent().build();
    }
}
