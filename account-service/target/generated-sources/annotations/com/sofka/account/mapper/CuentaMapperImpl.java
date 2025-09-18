package com.sofka.account.mapper;

import com.sofka.account.dto.CuentaDTO;
import com.sofka.account.model.Cuenta;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-18T13:48:04-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.16 (Homebrew)"
)
@Component
public class CuentaMapperImpl implements CuentaMapper {

    @Override
    public Cuenta toEntity(CuentaDTO cuentaDTO) {
        if ( cuentaDTO == null ) {
            return null;
        }

        Cuenta cuenta = new Cuenta();

        cuenta.setNumeroCuenta( cuentaDTO.getNumeroCuenta() );
        cuenta.setTipoCuenta( cuentaDTO.getTipoCuenta() );
        cuenta.setSaldoInicial( cuentaDTO.getSaldoInicial() );
        cuenta.setEstado( cuentaDTO.getEstado() );
        cuenta.setClienteId( cuentaDTO.getClienteId() );

        return cuenta;
    }

    @Override
    public CuentaDTO toDTO(Cuenta cuenta) {
        if ( cuenta == null ) {
            return null;
        }

        CuentaDTO cuentaDTO = new CuentaDTO();

        cuentaDTO.setId( cuenta.getId() );
        cuentaDTO.setNumeroCuenta( cuenta.getNumeroCuenta() );
        cuentaDTO.setTipoCuenta( cuenta.getTipoCuenta() );
        cuentaDTO.setSaldoInicial( cuenta.getSaldoInicial() );
        cuentaDTO.setEstado( cuenta.getEstado() );
        cuentaDTO.setClienteId( cuenta.getClienteId() );
        cuentaDTO.setCreatedAt( cuenta.getCreatedAt() );
        cuentaDTO.setUpdatedAt( cuenta.getUpdatedAt() );

        return cuentaDTO;
    }

    @Override
    public Cuenta updateEntity(CuentaDTO cuentaDTO, Cuenta cuenta) {
        if ( cuentaDTO == null ) {
            return cuenta;
        }

        cuenta.setNumeroCuenta( cuentaDTO.getNumeroCuenta() );
        cuenta.setTipoCuenta( cuentaDTO.getTipoCuenta() );
        cuenta.setSaldoInicial( cuentaDTO.getSaldoInicial() );
        cuenta.setEstado( cuentaDTO.getEstado() );
        cuenta.setClienteId( cuentaDTO.getClienteId() );

        return cuenta;
    }
}
