package com.sofka.account.mapper;

import com.sofka.account.dto.CuentaDTO;
import com.sofka.account.model.Cuenta;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CuentaMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Cuenta toEntity(CuentaDTO cuentaDTO);

    CuentaDTO toDTO(Cuenta cuenta);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Cuenta updateEntity(CuentaDTO cuentaDTO, @MappingTarget Cuenta cuenta);
}
