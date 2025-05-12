package com.cursosalura.aplicacionconversordemonedas.principal;

import com.cursosalura.aplicacionconversordemonedas.excepcion.ExcepcionConversion;
import com.cursosalura.aplicacionconversordemonedas.historial.RegistroErrores;
import com.cursosalura.aplicacionconversordemonedas.menus.MenuPrincipal;
import com.cursosalura.aplicacionconversordemonedas.presentacion.PresentacionConversorDeMonedas;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws ExcepcionConversion {
        PresentacionConversorDeMonedas.mostrarPresentacion();
        Scanner teclado = new Scanner(System.in);
        try {
            MenuPrincipal menu = new MenuPrincipal(teclado);
            menu.mostrarMenuPrincipal();
            teclado.close();

        }catch (ExcepcionConversion e) {
            String mensaje = "❌ Error: " + e.getMessage();
            // Se registra en el log_errores.txt
            RegistroErrores.registrar(mensaje);
            System.out.println(mensaje);
        }
    }
}




