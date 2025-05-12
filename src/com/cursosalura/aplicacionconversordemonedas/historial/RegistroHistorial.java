package com.cursosalura.aplicacionconversordemonedas.historial;

import com.cursosalura.aplicacionconversordemonedas.excepcion.ExcepcionConversion;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RegistroHistorial {

    private static final String NOMBRE_ARCHIVO = "historial_conversiones.txt";
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void registrarConversionMoneda(String origen, String destino, double monto, double resultado) {
        String fechaHoraConversion = LocalDateTime.now().format(FORMATO_FECHA);
        String linea = String.format("🔸 \033[32m[%s] - \033[31m%.2f \033[37m[\033[32m%s\033[37m] \033[31m=>>> \033[35m%.2f \033[37m[\033[32m%s\033[37m]", fechaHoraConversion, monto, origen, resultado, destino);

        try (FileWriter escritor = new FileWriter(NOMBRE_ARCHIVO, true)) {
            escritor.write(linea + System.lineSeparator());
        } catch (IOException e) {
            String mensaje = "❌ \033[31mError al registrar historial: " + e.getMessage();
            // Se registra en el log_errores.txt
            RegistroErrores.registrar(mensaje);
            // Se registra en log_errores_desarrollador.txt
            RegistroErroresDesarrollador.registrar(e);
            throw new ExcepcionConversion(mensaje);
        }
    }
}