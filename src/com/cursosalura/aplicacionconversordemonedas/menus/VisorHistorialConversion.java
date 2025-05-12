package com.cursosalura.aplicacionconversordemonedas.menus;

import com.cursosalura.aplicacionconversordemonedas.excepcion.ExcepcionConversion;
import com.cursosalura.aplicacionconversordemonedas.historial.RegistroErrores;
import com.cursosalura.aplicacionconversordemonedas.historial.RegistroErroresDesarrollador;
import com.cursosalura.aplicacionconversordemonedas.utilidades.UtilidadesConsola;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class VisorHistorialConversion {

    private final Scanner teclado;
    private static final String RUTA_ARCHIVO = "historial_conversiones.txt";

    public VisorHistorialConversion(Scanner teclado) {
        this.teclado = teclado;
    }

    public void mostrarHistorialConversion() {
        System.out.println("""
                \n\033[96m█▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬█
                ▬▬▬▬▬▬▬▬\033[32m=====\033[95mHISTORIAL DE CONVERSIONES\033[32m=====\033[96m▬▬▬▬▬▬▬▬
                █▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬█
                """);

        try (BufferedReader lectura = new BufferedReader(new FileReader(RUTA_ARCHIVO))) {
            String linea;
            boolean hayDatos = false;

            while ((linea = lectura.readLine()) != null) {
                System.out.println(linea);
                hayDatos = true;
            }

            if (!hayDatos) {
                System.out.println("⚠️ \033[34mNo hay conversiones registradas aún.");
            }

        } catch (IOException e) {
            String mensaje = "❌ \033[31mError al leer el historial: " + e.getMessage();
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
