
package com.cursosalura.aplicacionconversordemonedas.historial;

import com.cursosalura.aplicacionconversordemonedas.excepcion.ExcepcionConversion;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RegistroErrores {

    private static final String ARCHIVO_ERRORES = "log_errores.txt";

    public static void registrar(String mensaje) {
        try (FileWriter writer = new FileWriter(ARCHIVO_ERRORES, true)) {
            String fechaHora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            writer.write(String.format("🔸 \033[37m[\033[32m%s\033[37m] - \033[31m%s%n", fechaHora, mensaje));
        } catch (IOException e) {
            String mensajeErr = "❌ \033[31mNo se pudo guardar el error en el log.";
            // Se registra en el log_errores.txt
            RegistroErrores.registrar(mensajeErr);
            // Se registra en log_errores_desarrollador.txt
            RegistroErroresDesarrollador.registrar(e);
            throw new ExcepcionConversion(mensajeErr);
        }
    }
}
