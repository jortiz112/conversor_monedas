package com.cursosalura.aplicacionconversordemonedas.utilidades;

import com.cursosalura.aplicacionconversordemonedas.historial.RegistroErrores;
import com.cursosalura.aplicacionconversordemonedas.historial.RegistroErroresDesarrollador;

import java.util.Scanner;

public class ValidadacionEntrada {

    public static String leerCodigoMoneda(Scanner teclado, String tipo) {
        while (true) {
            System.out.println("\033[33mPor favor ingrese el código de moneda " + tipo + " (ej: USD, eur):");
            System.out.print("\033[91m►►► ");
            //Elimino los espacios en blanco y convierto a mayúsculas
            String entrada = teclado.nextLine().trim().toUpperCase();
            if (entrada.matches("^[A-Z]{3}$")) {
                return entrada;
            } else {
                String mensaje = "❌ \033[31mCódigo inválido. Debe contener exactamente 3 letras (ej: USD, eur).";
                // Se registra en el log_errores.txt
                RegistroErrores.registrar(mensaje);
                // Se registra en log_errores_desarrollador.txt
                RegistroErroresDesarrollador.registrar(new Exception("❌ \033[31mCódigo inválido. Debe contener exactamente 3 letras (ej: USD, eur)."));
                System.out.println(mensaje);
            }
        }
    }

    public static double leerMonto(Scanner teclado) {
        while (true) {
            System.out.println("\033[33mPor favor ingrese el monto a convertir: ");
            System.out.print("\033[91m►►► ");
            //Elimino los espacios en blanco
            String entrada = teclado.nextLine().trim();
            if (entrada.matches("^[0-9]+(\\.[0-9]+)?$")) {
                return Double.parseDouble(entrada);
            } else {
                String mensaje = "❌ \033[31mMonto inválido. Debe ingresar un número válido (ej: 100 o 99.99).";
                // Se registra en el log_errores.txt
                RegistroErrores.registrar(mensaje);
                // Se registra en log_errores_desarrollador.txt
                RegistroErroresDesarrollador.registrar(new Exception("❌ \033[31mMonto inválido. Debe ingresar un número válido (ej: 100 o 99.99)."));
                System.out.println(mensaje);
            }
        }
    }
}