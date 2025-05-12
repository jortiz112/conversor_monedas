package com.cursosalura.aplicacionconversordemonedas.excepcion;

public class ExcepcionConversion extends RuntimeException {
    private String mensaje;

    public ExcepcionConversion(String mensaje) {
        this.mensaje = mensaje;
    }

    @Override
    public String getMessage() {
        return this.mensaje;
    }
}