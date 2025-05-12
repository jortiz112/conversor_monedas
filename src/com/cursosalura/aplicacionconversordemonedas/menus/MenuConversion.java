package com.cursosalura.aplicacionconversordemonedas.menus;

import com.cursosalura.aplicacionconversordemonedas.excepcion.ExcepcionConversion;
import com.cursosalura.aplicacionconversordemonedas.historial.RegistroErroresDesarrollador;
import com.cursosalura.aplicacionconversordemonedas.historial.RegistroHistorial;
import com.cursosalura.aplicacionconversordemonedas.modelos.RespuestaConversionMonto;
import com.cursosalura.aplicacionconversordemonedas.modelos.SolicitudConversionMoneda;
import com.cursosalura.aplicacionconversordemonedas.servicios.ConversorMonedas;
import com.cursosalura.aplicacionconversordemonedas.historial.RegistroErrores;
import com.cursosalura.aplicacionconversordemonedas.utilidades.UtilidadesConsola;
import com.cursosalura.aplicacionconversordemonedas.utilidades.ValidadacionEntrada;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class MenuConversion {

    private final Scanner teclado = new Scanner(System.in);
    private final ConversorMonedas conversor = new ConversorMonedas();

    public void mostrarMenuConversion() {
        boolean continuar = true;

        while (continuar) {
            mostrarOpcionesConversion();

            int opcion = UtilidadesConsola.leerOpcion(teclado);

            String codigoBase;
            String codigoDestino;

            switch (opcion) {
                case 1 -> { codigoBase = "USD"; codigoDestino = "COP"; }
                case 2 -> { codigoBase = "COP"; codigoDestino = "USD"; }
                case 3 -> { codigoBase = "USD"; codigoDestino = "CLP"; }
                case 4 -> { codigoBase = "CLP"; codigoDestino = "USD"; }
                case 5 -> { codigoBase = "USD"; codigoDestino = "BRL"; }
                case 6 -> { codigoBase = "BRL"; codigoDestino = "USD"; }
                case 7 -> { codigoBase = "USD"; codigoDestino = "BOB"; }
                case 8 -> { codigoBase = "BOB"; codigoDestino = "USD"; }
                case 9 -> { codigoBase = "USD"; codigoDestino = "ARS"; }
                case 10 -> { codigoBase = "ARS"; codigoDestino = "USD"; }
                default -> {
                    String mensaje = "⚠️ \033[34mOpción no válida. Intente nuevamente.";
                    // Se registra en el log_errores.txt
                    RegistroErrores.registrar(mensaje);
                    // Se registra en log_errores_desarrollador.txt
                    RegistroErroresDesarrollador.registrar(new Exception("⚠️ \033[34mOpción no válida. Intente nuevamente."));
                    System.out.println(mensaje);
                    //Permite continuar sacando nuevamente el menú cuando el usuario ingreso una opción no válida
                    continue;
                }
            }

            //Se recibe el monto para la conversión
            double monto = ValidadacionEntrada.leerMonto(teclado);

            SolicitudConversionMoneda solicitud = new SolicitudConversionMoneda(codigoBase, codigoDestino, monto);

            try {
                //Se usa un record para mapear (asignar) los campos del JSON respuesta del API a un objeto Java inmutable.
                RespuestaConversionMonto respuesta = conversor.convertirMonto(solicitud);

                String fechaHoraConversion = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

                System.out.printf("\n✅ \033[32m%s \033[32m- \033[33mEl valor de \033[31m%.2f \033[37m[\033[32m%s\033[37m] \033[33mcorresponde al valor final de \033[31m=>>> \033[35m%.2f \033[37m[\033[32m%s\033[37m]%n",
                        fechaHoraConversion, monto, codigoBase, respuesta.conversion_result(), codigoDestino);

                // Se registra el historial en el archivo historial_conversiones.txt
                RegistroHistorial.registrarConversionMoneda(codigoBase, codigoDestino, monto, respuesta.conversion_result());

            } catch (ExcepcionConversion e) {
                String mensaje = "❌ \033[31mError en la conversión: " + e.getMessage();
                // Se registra en el log_errores.txt
                RegistroErrores.registrar(mensaje);
                // Se registra en log_errores_desarrollador.txt
                RegistroErroresDesarrollador.registrar(e);
                throw new ExcepcionConversion(mensaje);
            }

            System.out.println("\n\033[34m¿Deseas realizar otra conversión? ('s' para continuar / ENTER o cualquier tecla para volver al menú principal):");
            System.out.print("\033[31m►►► ");
            String continuarInput = teclado.nextLine().trim().toLowerCase();
            continuar = continuarInput.equals("s");
        }
    }

    private void mostrarOpcionesConversion() {
        System.out.println("""
                \n\033[96m█▬▬▬▬\033[32m===\033[95mSeleccione una opción de conversión:\033[32m===\033[96m▬▬▬▬█
                █▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬█
                \t\033[32m1 -  \033[93mDólar estadounidense \033[31m►►► \033[93mPeso colombiano
                \t\033[32m2 -  \033[93mPeso colombiano \033[31m►►► \033[93mDólar estadounidense
                \t\033[32m3 -  \033[93mDólar estadounidense \033[31m►►► \033[93mPeso chileno
                \t\033[32m4 -  \033[93mPeso chileno \033[31m►►► \033[93mDólar estadounidense
                \t\033[32m5 -  \033[93mDólar estadounidense \033[31m►►► \033[93mReal brasileño
                \t\033[32m6 -  \033[93mReal brasileño \033[31m►►► \033[93mDólar estadounidense
                \t\033[32m7 -  \033[93mDólar estadounidense \033[31m►►► \033[93mBoliviano boliviano
                \t\033[32m8 -  \033[93mBoliviano boliviano \033[31m►►► \033[93mDólar estadounidense
                \t\033[32m9 -  \033[93mDólar estadounidense \033[31m►►► \033[93mPeso argentino
                \t\033[32m10 - \033[93mPeso argentino \033[31m►►► \033[93mDólar estadounidense
                \033[96m█▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬█
                """);

        System.out.println("\033[95mIngrese el número de la opción:");
        System.out.print("\033[91m►►► ");
    }
}
