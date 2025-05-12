package com.cursosalura.aplicacionconversordemonedas.menus;

import com.cursosalura.aplicacionconversordemonedas.excepcion.ExcepcionConversion;
import com.cursosalura.aplicacionconversordemonedas.historial.RegistroErrores;
import com.cursosalura.aplicacionconversordemonedas.historial.RegistroErroresDesarrollador;
import com.cursosalura.aplicacionconversordemonedas.utilidades.UtilidadesConsola;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class VisorLogErrores {
    private final Scanner teclado;
    private static final String ARCHIVO_LOG_ERRORES = "log_errores.txt";

    public VisorLogErrores(Scanner teclado) {
        this.teclado = teclado;
    }

    public void mostrarLogErrores() {
        System.out.println("""
                \n\033[96m█▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬█
                ▬▬▬▬▬▬▬▬▬▬▬\033[32m======\033[95mLOG DE ERRORES\033[32m=====\033[96m▬▬▬▬▬▬▬▬▬▬▬
                █▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬█
                """);

        try (BufferedReader lectura = new BufferedReader(new FileReader(ARCHIVO_LOG_ERRORES))) {
            String linea;
            boolean hayContenido = false;

            while ((linea = lectura.readLine()) != null) {
                System.out.println(linea);
                hayContenido = true;
            }

            if (!hayContenido) {
                System.out.println("⚠️ \033[34mNo se encontraron errores registrados.");
            }

        } catch (IOException e) {
            String mensaje = "❌ \033[31mError al leer el archivo de log: " + e.getMessage();
            // Se registra en el log_errores.txt
            RegistroErrores.registrar(mensaje);
            // Se registra en log_errores_desarrollador.txt
            RegistroErroresDesarrollador.registrar(e);
            throw new ExcepcionConversion(mensaje);
        }
        //Para volver al menú principal
        UtilidadesConsola.esperarEnter(teclado);
    }
}