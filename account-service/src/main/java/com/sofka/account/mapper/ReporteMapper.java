package com.sofka.account.mapper;

import com.sofka.account.dto.MovimientoReporteDTO;
import com.sofka.account.model.Movimiento;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReporteMapper {
    
    @Mapping(target = "descripcion", expression = "java(generarDescripcion(movimiento))")
    MovimientoReporteDTO toMovimientoReporteDTO(Movimiento movimiento);
    
    List<MovimientoReporteDTO> toMovimientoReporteDTOList(List<Movimiento> movimientos);
    
    default String generarDescripcion(Movimiento movimiento) {
        return String.format("%s de $%.2f", 
            movimiento.getTipoMovimiento().name(), 
            movimiento.getValor());
    }
}

