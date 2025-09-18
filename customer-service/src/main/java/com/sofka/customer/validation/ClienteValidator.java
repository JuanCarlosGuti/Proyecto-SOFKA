package com.sofka.customer.validation;

import com.sofka.customer.dto.ClienteDTO;
import com.sofka.customer.exception.BusinessException;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class ClienteValidator {
    
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+\\.[A-Za-z]{2,})$");
    private static final Pattern TELEFONO_PATTERN = Pattern.compile("^[0-9]{10}$");
    private static final Pattern IDENTIFICACION_PATTERN = Pattern.compile("^[0-9]{10,13}$");
    private static final Pattern CONTRASENA_PATTERN = Pattern.compile("^.{4,20}$");
    
    public void validarCreacionCliente(ClienteDTO clienteDTO) {
        validarDatosPersonales(clienteDTO);
        validarDatosCliente(clienteDTO);
    }
    
    public void validarDatosPersonales(ClienteDTO clienteDTO) {
        validarNombre(clienteDTO.getNombre());
        validarGenero(clienteDTO.getGenero());
        validarEdad(clienteDTO.getEdad());
        validarIdentificacion(clienteDTO.getIdentificacion());
        validarDireccion(clienteDTO.getDireccion());
        validarTelefono(clienteDTO.getTelefono());
    }
    
    public void validarDatosCliente(ClienteDTO clienteDTO) {
        validarClienteId(clienteDTO.getClienteId());
        validarContrasena(clienteDTO.getContrasena());
        validarEstado(clienteDTO.getEstado());
    }
    
    public void validarNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new BusinessException("El nombre es obligatorio");
        }
        
        if (nombre.length() < 2 || nombre.length() > 100) {
            throw new BusinessException("El nombre debe tener entre 2 y 100 caracteres");
        }
        
        if (!nombre.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$")) {
            throw new BusinessException("El nombre solo puede contener letras y espacios");
        }
    }
    
    public void validarGenero(String genero) {
        if (genero == null || genero.trim().isEmpty()) {
            throw new BusinessException("El género es obligatorio");
        }
        
        if (!genero.equals("M") && !genero.equals("F")) {
            throw new BusinessException("El género debe ser 'M' (Masculino) o 'F' (Femenino)");
        }
    }
    
    public void validarEdad(Integer edad) {
        if (edad == null) {
            throw new BusinessException("La edad es obligatoria");
        }
        
        if (edad < 18 || edad > 120) {
            throw new BusinessException("La edad debe estar entre 18 y 120 años");
        }
    }
    
    public void validarIdentificacion(String identificacion) {
        if (identificacion == null || identificacion.trim().isEmpty()) {
            throw new BusinessException("La identificación es obligatoria");
        }
        
        if (!IDENTIFICACION_PATTERN.matcher(identificacion).matches()) {
            throw new BusinessException("La identificación debe tener entre 10 y 13 dígitos");
        }
    }
    
    public void validarDireccion(String direccion) {
        if (direccion == null || direccion.trim().isEmpty()) {
            throw new BusinessException("La dirección es obligatoria");
        }
        
        if (direccion.length() < 10 || direccion.length() > 200) {
            throw new BusinessException("La dirección debe tener entre 10 y 200 caracteres");
        }
    }
    
    public void validarTelefono(String telefono) {
        if (telefono == null || telefono.trim().isEmpty()) {
            throw new BusinessException("El teléfono es obligatorio");
        }
        
        if (!TELEFONO_PATTERN.matcher(telefono).matches()) {
            throw new BusinessException("El teléfono debe tener exactamente 10 dígitos");
        }
    }
    
    public void validarClienteId(String clienteId) {
        if (clienteId == null || clienteId.trim().isEmpty()) {
            throw new BusinessException("El ID del cliente es obligatorio");
        }
        
        if (clienteId.length() < 3 || clienteId.length() > 20) {
            throw new BusinessException("El ID del cliente debe tener entre 3 y 20 caracteres");
        }
        
        if (!clienteId.matches("^[A-Z0-9]+$")) {
            throw new BusinessException("El ID del cliente solo puede contener letras mayúsculas y números");
        }
    }
    
    public void validarContrasena(String contrasena) {
        if (contrasena == null || contrasena.trim().isEmpty()) {
            throw new BusinessException("La contraseña es obligatoria");
        }
        
        if (!CONTRASENA_PATTERN.matcher(contrasena).matches()) {
            throw new BusinessException("La contraseña debe tener entre 4 y 20 caracteres");
        }
    }
    
    public void validarEstado(Boolean estado) {
        if (estado == null) {
            throw new BusinessException("El estado es obligatorio");
        }
    }
}

