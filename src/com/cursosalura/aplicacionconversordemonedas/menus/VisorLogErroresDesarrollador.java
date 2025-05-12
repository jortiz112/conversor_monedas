package com.cursosalura.aplicacionconversordemonedas.menus;

import com.cursosalura.aplicacionconversordemonedas.excepcion.ExcepcionConversion;
import com.cursosalura.aplicacionconversordemonedas.historial.RegistroErrores;
import com.cursosalura.aplicacionconversordemonedas.historial.RegistroErroresDesarrollador;
import com.cursosalura.aplicacionconversordemonedas.utilidades.UtilidadesConsola;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class VisorLogErroresDesarrollador {

    private final Scanner teclado;

    public VisorLogErroresDesarrollador(Scanner teclado) {
        this.teclado = teclado;
    }

    public void mostrarLogErroresDesarrollador() {
        System.out.println("""
                \n\033[96m█▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬█
                ▬▬▬▬▬▬▬▬▬▬▬\033[32m=====\033[95mLOG DE ERRORES PARA DESARROLLADOR\033[32m=====\033[96m▬▬▬▬▬▬▬▬▬▬▬
                █▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬█
                """);

        try (BufferedReader reader = new BufferedReader(new FileReader("log_errores_desarrollador.txt"))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                System.out.println("\033[31m" + linea);
            }
        } catch (IOException e) {
            String mensaje = "❌ \033[31mNo se pudo leer el archivo de errores del desarrollador.";
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

