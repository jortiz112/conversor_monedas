package com.cursosalura.aplicacionconversordemonedas.servicios;

import com.cursosalura.aplicacionconversordemonedas.config.Configuracion;
import com.cursosalura.aplicacionconversordemonedas.excepcion.ExcepcionConversion;
import com.cursosalura.aplicacionconversordemonedas.historial.RegistroErroresDesarrollador;
import com.cursosalura.aplicacionconversordemonedas.modelos.RespuestaConversionMonto;
import com.cursosalura.aplicacionconversordemonedas.modelos.SolicitudConversionMoneda;
import com.cursosalura.aplicacionconversordemonedas.historial.RegistroErrores;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConversorMonedas {

    private static final String API_KEY = Configuracion.obtener("api.key");
    private static final String BASE_URL = "https://v6.exchangerate-api.com/v6/";

    public RespuestaConversionMonto convertirMonto (SolicitudConversionMoneda solicitud) throws ExcepcionConversion {
        String direccion = BASE_URL + API_KEY + "/pair/" +
                solicitud.codigoBase() + "/" +
                solicitud.codigoDestino() + "/" +
                solicitud.monto();
        try {
            //Solicitud, requerimiento, envío y respuesta del API de conversión de monedas
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(direccion))
                    .build();
            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
            //Json que devuelve al API
            String json = response.body();
            if (json == null || json.isBlank()) {
                String mensaje = "❌ \033[31mLa respuesta del servicio está vacía.";
                // Se registra en el log_errores.txt
                RegistroErrores.registrar(mensaje);
                // Se registra en log_errores_desarrollador.txt
                RegistroErroresDesarrollador.registrar(new Exception("❌ \033[31mRespuesta vacía desde el servicio: JSON nulo o vacío"));
                throw new ExcepcionConversion(mensaje);
            }
            //Se realiza deserialización JSON (proceso de tomar una cadena de texto en formato JSON y convertirla en un objeto)
            RespuestaConversionMonto respuesta = new Gson().fromJson(json, RespuestaConversionMonto.class);
            //Para manejar los errores que tiene la API en su documentación
            ValidadorRespuesta.validarAPIErrores(respuesta);
            return respuesta;

            //Para manejo de errores de red
        } catch (IOException | InterruptedException e) {
            String mensaje = "❌ \033[31mError de conexión con el API: " + e.getMessage();
            // Se registra en el log_errores.txt
            RegistroErrores.registrar(mensaje);
            // Se registra en log_errores_desarrollador.txt
            RegistroErroresDesarrollador.registrar(e);
            throw new ExcepcionConversion(mensaje);
            //Para saber si no hace deserialización JSON
        } catch (JsonSyntaxException e) {
            String mensaje = "❌ \033[31mError al procesar la respuesta del servicio.";
            // Se registra en el log_errores.txt
            RegistroErrores.registrar(mensaje);
            // Se registra en log_errores_desarrollador.txt
            RegistroErroresDesarrollador.registrar(e);
            throw new ExcepcionConversion(mensaje);
        }
    }
}