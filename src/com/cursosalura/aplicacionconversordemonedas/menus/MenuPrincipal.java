package com.cursosalura.aplicacionconversordemonedas.menus;

import com.cursosalura.aplicacionconversordemonedas.historial.RegistroErrores;
import com.cursosalura.aplicacionconversordemonedas.historial.RegistroErroresDesarrollador;
import com.cursosalura.aplicacionconversordemonedas.utilidades.UtilidadesConsola;

import java.util.Scanner;

public class MenuPrincipal {

    private final Scanner teclado;

    public MenuPrincipal(Scanner teclado) {
        this.teclado = teclado;
    }

    public void mostrarMenuPrincipal() {
        int opcion;
        do {
            System.out.println("""
                    \033[96m█▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬█
                    ▬▬▬▬▬▬▬▬▬\033[32m=====\033[95mMENÚ PRINCIPAL DEL CONVERSOR DE MONEDAS\033[32m====\033[96m▬▬▬▬▬▬▬▬▬
                    █▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬█
                        \033[32m1.- \033[93mRealizar conversión de monedas
                        \033[32m2.- \033[93mVer el historial de conversión de monedas
                        \033[32m3.- \033[93mMostrar el listado total de monedas
                        \033[32m4.- \033[93mConversión seleccionando las monedas de un listado
                        \033[32m5.- \033[93mVer el log de errores del conversor de monedas
                        \033[32m6.- \033[93mMostrar listado total de monedas en ingles
                        \033[32m7.- \033[93mVer el log de errores para el desarrollador del conversor de monedas
                    
                        \033[32m0.- \033[31mSalir
                    \033[96m█▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬█
                    █▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬█
                    """);

            System.out.println("\033[95mPor favor ingrese el número de la opción:");
            System.out.print("\033[91m►►► ");

            opcion = UtilidadesConsola.leerOpcion(teclado);

            switch (opcion) {
                case 1 -> new MenuConversion().mostrarMenuConversion();
                case 2 -> new VisorHistorialConversion(teclado).mostrarHistorialConversion();
                case 3 -> new MenuListadoMonedas(teclado).mostrarListadoMonedas();
                case 4 -> new MenuConversionDesdeListado(teclado).mostrarConversionDesdeListado();
                case 5 -> new VisorLogErrores(teclado).mostrarLogErrores();
                case 6 -> new MenuListadoCodigosMonedasIngles(teclado).mostrarListadoIngles();
                case 7 -> new VisorLogErroresDesarrollador(teclado).mostrarLogErroresDesarrollador();
                case 0 ->
                        System.out.println("\033[32m☻☻ \033[34mSaliendo de la aplicación de conversor de monedas. ¡Hasta pronto, disfrute su día!\033[32m ☻☻");
                default -> {
                    String mensaje = "⚠️ \033[34mOpción inválida. Intente nuevamente.\n";
                    // Se registra en el log_errores.txt
                    RegistroErrores.registrar(mensaje);
                    // Se registra en log_errores_desarrollador.txt
                    RegistroErroresDesarrollador.registrar(new Exception("⚠️ \033[34mOpción inválida. Intente nuevamente.\n"));
                    System.out.println(mensaje);
                }
            }
        } while (opcion != 0);
    }
}