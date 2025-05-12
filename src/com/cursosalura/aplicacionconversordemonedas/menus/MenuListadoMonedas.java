package com.cursosalura.aplicacionconversordemonedas.menus;

import com.cursosalura.aplicacionconversordemonedas.excepcion.ExcepcionConversion;
import com.cursosalura.aplicacionconversordemonedas.historial.RegistroErrores;
import com.cursosalura.aplicacionconversordemonedas.historial.RegistroErroresDesarrollador;
import com.cursosalura.aplicacionconversordemonedas.modelos.RespuestaListadoMonedas;
import com.cursosalura.aplicacionconversordemonedas.servicios.ServicioMonedas;
import com.cursosalura.aplicacionconversordemonedas.utilidades.DescripcionesMonedas;
import com.cursosalura.aplicacionconversordemonedas.utilidades.UtilidadesConsola;

import java.util.Map;
import java.util.Scanner;

public class MenuListadoMonedas {

    private final Scanner teclado;

    public MenuListadoMonedas(Scanner teclado) {
        this.teclado = teclado;
    }

    public void mostrarListadoMonedas() {
        ServicioMonedas servicio = new ServicioMonedas();
        try {
            RespuestaListadoMonedas respuesta = servicio.obtenerListadoMonedas();

            System.out.println("""
                \n\033[96m█▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬█
                ▬▬▬▬\033[32m===\033[95m🔍 LISTADO DE MONEDAS Y TASA RESPECTO A USD\033[32m==\033[96m▬▬▬▬
                █▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬█
                """);
            for (Map.Entry<String, Double> entry : respuesta.conversionRates().entrySet()) {
                String codigo = entry.getKey();
                double tasa = entry.getValue();
                String descripcion = DescripcionesMonedas.obtenerDescripcion(codigo);
                System.out.printf("🔸 \033[32m%-7s \033[33m%-36s \033[35m%10.4f\n", codigo, descripcion, tasa);
            }
        } catch (ExcepcionConversion e) {
            String mensaje = "❌ Error al mostrar listado de monedas: " + e.getMessage();
            // Se registra en el log_errores.txt
            RegistroErrores.registrar(mensaje);
            // Se registra en log_errores_desarrollador.txt
            RegistroErroresDesarrollador.registrar(e);
            System.out.println(mensaje);
        }
        //Para volver al menú principal
        UtilidadesConsola.esperarEnter(teclado);
    }
}