package com.cursosalura.aplicacionconversordemonedas.menus;

import com.cursosalura.aplicacionconversordemonedas.excepcion.ExcepcionConversion;
import com.cursosalura.aplicacionconversordemonedas.historial.RegistroErroresDesarrollador;
import com.cursosalura.aplicacionconversordemonedas.historial.RegistroHistorial;
import com.cursosalura.aplicacionconversordemonedas.modelos.RespuestaConversionMonto;
import com.cursosalura.aplicacionconversordemonedas.modelos.RespuestaListadoMonedas;
import com.cursosalura.aplicacionconversordemonedas.modelos.SolicitudConversionMoneda;
import com.cursosalura.aplicacionconversordemonedas.servicios.ConversorMonedas;
import com.cursosalura.aplicacionconversordemonedas.servicios.ServicioMonedas;
import com.cursosalura.aplicacionconversordemonedas.utilidades.DescripcionesMonedas;
import com.cursosalura.aplicacionconversordemonedas.historial.RegistroErrores;
import com.cursosalura.aplicacionconversordemonedas.utilidades.ValidadacionEntrada;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Scanner;

public class MenuConversionDesdeListado {

    private final Scanner teclado;

    public MenuConversionDesdeListado(Scanner teclado) {
        this.teclado = teclado;
    }

    public void mostrarConversionDesdeListado() {
        ServicioMonedas servicioMonedas = new ServicioMonedas();

        try {
            RespuestaListadoMonedas respuesta = servicioMonedas.obtenerListadoMonedas();
            Map<String, Double> tasas = respuesta.conversionRates();

            System.out.println("""
                    \n\033[96m█▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬█
                    ▬▬▬▬▬▬\033[32m====\033[95m🌍 LISTADO DE MONEDAS DISPONIBLES\033[32m====\033[96m▬▬▬▬▬▬
                    █▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬█
                    """);

            //Muestra el listado de monedas para realizar el ingreso manual para la conversión
            for (Map.Entry<String, Double> entry : tasas.entrySet()) {
                String codigo = entry.getKey();
                double tasa = entry.getValue();
                String descripcion = DescripcionesMonedas.obtenerDescripcion(codigo);
                System.out.printf("🔸 \033[32m%-7s \033[33m%-36s \033[35m%10.4f\n", codigo, descripcion, tasa);
            }

            boolean continuar = true;

            while (continuar) {
                try {
                    // Lee el código de moneda origen, destino y el monto del usuario para convertir
                    // Adicional las entradas son validadas
                    String codigoBase = ValidadacionEntrada.leerCodigoMoneda(teclado, "origen");
                    String codigoDestino = ValidadacionEntrada.leerCodigoMoneda(teclado, "destino");
                    double monto = ValidadacionEntrada.leerMonto(teclado);

                    SolicitudConversionMoneda solicitud = new SolicitudConversionMoneda(codigoBase, codigoDestino, monto);
                    ConversorMonedas conversor = new ConversorMonedas();
                    RespuestaConversionMonto conversionMonto = conversor.convertirMonto(solicitud);

                    String fechaHoraConversion = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

                    System.out.printf("\n✅ \033[32m%s \033[32m- \033[33mEl valor de \033[31m%.2f \033[37m[\033[32m%s\033[37m] \033[33mcorresponde al valor final de \033[31m=>>> \033[35m%.2f \033[37m[\033[32m%s\033[37m]%n",
                            fechaHoraConversion, monto, codigoBase, conversionMonto.conversion_result(), codigoDestino);

                    // Se registra el historial en el archivo historial_conversiones.txt
                    RegistroHistorial.registrarConversionMoneda(codigoBase, codigoDestino, monto, conversionMonto.conversion_result());

                } catch (ExcepcionConversion e) {
                    String mensaje = "❌ \033[31mError al realizar la conversión con ingreso manual de monedas y monto: " + e.getMessage();
                    // Se registra en el log_errores.txt
                    RegistroErrores.registrar(mensaje);
                    // Se registra en log_errores_desarrollador.txt
                    RegistroErroresDesarrollador.registrar(e);
                    System.out.println(mensaje);
                }

                System.out.println("\n\033[34m¿Deseas realizar otra conversión? ('s' para continuar / ENTER o cualquier tecla para volver al menú principal):");
                System.out.print("\033[31m►►► ");
                String continuarInput = teclado.nextLine().trim().toLowerCase();
                continuar = continuarInput.equals("s");
            }

        } catch (ExcepcionConversion e) {
            String mensaje = "❌ \033[31mError al obtener el listado de monedas: " + e.getMessage();
            // Se registra en el log_errores.txt
            RegistroErrores.registrar(mensaje);
            // Se registra en log_errores_desarrollador.txt
            RegistroErroresDesarrollador.registrar(e);
            throw new ExcepcionConversion(mensaje);
        }
    }
}