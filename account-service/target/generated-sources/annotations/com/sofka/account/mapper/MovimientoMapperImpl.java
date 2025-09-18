package com.sofka.account.mapper;

import com.sofka.account.dto.MovimientoDTO;
import com.sofka.account.model.Movimiento;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-18T13:17:05-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.16 (Homebrew)"
)
@Component
public class MovimientoMapperImpl implements MovimientoMapper {

    @Override
    public Movimiento toEntity(MovimientoDTO movimientoDTO) {
        if ( movimientoDTO == null ) {
            return null;
        }

        Movimiento movimiento = new Movimiento();

        movimiento.setFecha( movimientoDTO.getFecha() );
        movimiento.setTipoMovimiento( movimientoDTO.getTipoMovimiento() );
        movimiento.setValor( movimientoDTO.getValor() );
        movimiento.setSaldo( movimientoDTO.getSaldo() );
        movimiento.setNumeroCuenta( movimientoDTO.getNumeroCuenta() );

        return movimiento;
    }

    @Override
    public MovimientoDTO toDTO(Movimiento movimiento) {
        if ( movimiento == null ) {
            return null;
        }

        MovimientoDTO movimientoDTO = new MovimientoDTO();

        movimientoDTO.setId( movimiento.getId() );
        movimientoDTO.setFecha( movimiento.getFecha() );
        movimientoDTO.setTipoMovimiento( movimiento.getTipoMovimiento() );
        movimientoDTO.setValor( movimiento.getValor() );
        movimientoDTO.setSaldo( movimiento.getSaldo() );
        movimientoDTO.setNumeroCuenta( movimiento.getNumeroCuenta() );
        movimientoDTO.setCreatedAt( movimiento.getCreatedAt() );

        return movimientoDTO;
    }

    @Override
    public Movimiento updateEntity(MovimientoDTO movimientoDTO, Movimiento movimiento) {
        if ( movimientoDTO == null ) {
            return movimiento;
        }

        movimiento.setFecha( movimientoDTO.getFecha() );
        movimiento.setTipoMovimiento( movimientoDTO.getTipoMovimiento() );
        movimiento.setValor( movimientoDTO.getValor() );
        movimiento.setSaldo( movimientoDTO.getSaldo() );
        movimiento.setNumeroCuenta( movimientoDTO.getNumeroCuenta() );

        return movimiento;
    }
}
