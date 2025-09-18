package com.sofka.account.mapper;

import com.sofka.account.dto.MovimientoDTO;
import com.sofka.account.model.Movimiento;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface MovimientoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Movimiento toEntity(MovimientoDTO movimientoDTO);

    MovimientoDTO toDTO(Movimiento movimiento);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Movimiento updateEntity(MovimientoDTO movimientoDTO, @MappingTarget Movimiento movimiento);
}
