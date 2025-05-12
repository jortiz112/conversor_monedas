package com.cursosalura.aplicacionconversordemonedas.utilidades;

import com.cursosalura.aplicacionconversordemonedas.historial.RegistroErrores;
import com.cursosalura.aplicacionconversordemonedas.historial.RegistroErroresDesarrollador;

import java.util.Scanner;

public class UtilidadesConsola {
    public static void esperarEnter(Scanner teclado) {
        while (true) {
            System.out.println("\n\033[35mEscribe 0 y presiona Enter para regresar al menú principal: \033[0m");
            System.out.print("\033[91m►►► ");
            String entrada = teclado.nextLine().trim();
            if ("0".equals(entrada)) {
                // Salto de línea después del Enter
                System.out.println();
                break;
            } else {
                String mensaje = "❌ \033[31mEntrada inválida. Intenta nuevamente.";
                // Se registra en el log_errores.txt
                RegistroErrores.registrar(mensaje);
                // Se registra en log_errores_desarrollador.txt
                RegistroErroresDesarrollador.registrar(new Exception("❌ \033[31mEntrada inválida. Intenta nuevamente."));
                System.out.println(mensaje);
            }
        }

    }

    public static int leerOpcion(Scanner teclado) {
        //En la entrada del usuario se elimina los espacios en blanco
        String entrada = teclado.nextLine().trim();
        if (entrada.matches("^\\d+$")) {
            return Integer.parseInt(entrada);
        } else {
            // opción inválida entra en default en menu
            return -1;
        }
    }
}
