package com.cursosalura.aplicacionconversordemonedas.servicios;

import com.cursosalura.aplicacionconversordemonedas.excepcion.ExcepcionConversion;
import com.cursosalura.aplicacionconversordemonedas.modelos.RespuestaAPIErr;

public class ValidadorRespuesta {

    public static void validarAPIErrores(RespuestaAPIErr respuesta) throws ExcepcionConversion {
        if (!"success".equalsIgnoreCase(respuesta.result())) {
            // ya viene del JSON, gracias al @SerializedName que se puso en el
            // record RespuestaConversionMonto y RespuestaListadoMonedas
            String tipoError = respuesta.errortype();
            String mensajeUsuario;

            switch (tipoError) {
                case "unsupported-code" -> mensajeUsuario = "❌ \033[31mCódigo de moneda no soportado. Verifique que ingresó correctamente las siglas de las monedas y el monto";
                case "malformed-request" -> mensajeUsuario = "❌ \033[31mLa solicitud está mal formada. Revise los datos ingresados.";
                case "invalid-key" -> mensajeUsuario = "❌ \033[31mLa clave de API es inválida. Contacte al administrador.";
                case "inactive-account" -> mensajeUsuario = "❌ \033[31mLa cuenta está inactiva. Confirme su email o contacte soporte.";
                case "quota-reached" -> mensajeUsuario = "❌ \033[31mSe ha alcanzado el límite de solicitudes permitidas.";
                default -> mensajeUsuario = "❌ \033[31mSe produjo un error inesperado al consultar el servicio de conversión.";
            }

            // No aplico ninguno de los registros de errores aquí, porque sé
            // dúplica en los archivos de log el mismo error 2 veces
            throw new ExcepcionConversion(mensajeUsuario);
        }
    }
}