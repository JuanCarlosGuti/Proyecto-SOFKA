package com.sofka.account.model;

public enum TipoMovimiento {
    DEBITO("Débito"),
    CREDITO("Crédito");

    private final String descripcion;

    TipoMovimiento(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
