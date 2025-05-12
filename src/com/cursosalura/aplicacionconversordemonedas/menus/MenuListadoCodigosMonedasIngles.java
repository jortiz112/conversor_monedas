package com.cursosalura.aplicacionconversordemonedas.menus;

import com.cursosalura.aplicacionconversordemonedas.excepcion.ExcepcionConversion;
import com.cursosalura.aplicacionconversordemonedas.historial.RegistroErrores;
import com.cursosalura.aplicacionconversordemonedas.historial.RegistroErroresDesarrollador;
import com.cursosalura.aplicacionconversordemonedas.modelos.RespuestaCodigosMonedasIngles;
import com.cursosalura.aplicacionconversordemonedas.servicios.ServicioMonedas;
import com.cursosalura.aplicacionconversordemonedas.utilidades.UtilidadesConsola;

import java.util.Scanner;

public class MenuListadoCodigosMonedasIngles {

    private final Scanner teclado;

    public MenuListadoCodigosMonedasIngles(Scanner teclado) {
        this.teclado = teclado;
    }

    public void mostrarListadoIngles() {
        try {
            ServicioMonedas servicio = new ServicioMonedas();
            RespuestaCodigosMonedasIngles respuesta = servicio.obtenerCodigosMonedasIngles();

            System.out.println("""
                \n\033[96m█▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬█
                ▬▬▬▬\033[32m===\033[95m🔍 LISTADO DE MONEDAS DISPONIBLES EN INGLES\033[32m==\033[96m▬▬▬▬
                █▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬█
                """);

            //Recorro la lista que devuelve la API
            for (var par : respuesta.supported_codes()) {
                System.out.printf("🔸 \033[92m%-6s \033[33m%s%n", par.get(0), par.get(1));
            }

        } catch (ExcepcionConversion e) {
            String mensaje = "❌ \033[31mError: " + e.getMessage();
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

