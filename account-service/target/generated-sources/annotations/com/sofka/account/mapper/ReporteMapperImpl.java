package com.sofka.account.mapper;

import com.sofka.account.dto.MovimientoReporteDTO;
import com.sofka.account.model.Movimiento;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-18T13:48:04-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.16 (Homebrew)"
)
@Component
public class ReporteMapperImpl implements ReporteMapper {

    @Override
    public MovimientoReporteDTO toMovimientoReporteDTO(Movimiento movimiento) {
        if ( movimiento == null ) {
            return null;
        }

        MovimientoReporteDTO movimientoReporteDTO = new MovimientoReporteDTO();

        movimientoReporteDTO.setFecha( movimiento.getFecha() );
        if ( movimiento.getTipoMovimiento() != null ) {
            movimientoReporteDTO.setTipoMovimiento( movimiento.getTipoMovimiento().name() );
        }
        movimientoReporteDTO.setValor( movimiento.getValor() );
        movimientoReporteDTO.setSaldo( movimiento.getSaldo() );

        movimientoReporteDTO.setDescripcion( generarDescripcion(movimiento) );

        return movimientoReporteDTO;
    }

    @Override
    public List<MovimientoReporteDTO> toMovimientoReporteDTOList(List<Movimiento> movimientos) {
        if ( movimientos == null ) {
            return null;
        }

        List<MovimientoReporteDTO> list = new ArrayList<MovimientoReporteDTO>( movimientos.size() );
        for ( Movimiento movimiento : movimientos ) {
            list.add( toMovimientoReporteDTO( movimiento ) );
        }

        return list;
    }
}
