package com.cursosalura.aplicacionconversordemonedas.historial;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RegistroErroresDesarrollador {

    private static final String ARCHIVO_ERRORES_DESARROLLADOR = "log_errores_desarrollador.txt";

    public static void registrar(Exception e) {
        try (FileWriter fileWriter = new FileWriter(ARCHIVO_ERRORES_DESARROLLADOR, true);
             PrintWriter printWriter = new PrintWriter(fileWriter)) {

            String fechaHora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            printWriter.println("\033[32m==== Error registrado en: " + fechaHora + " ====\n");
            e.printStackTrace(printWriter);
            printWriter.println();

        } catch (IOException ex) {
            System.err.println("❌ \033[31mNo se pudo registrar el error del desarrollador.");
        }
    }
}