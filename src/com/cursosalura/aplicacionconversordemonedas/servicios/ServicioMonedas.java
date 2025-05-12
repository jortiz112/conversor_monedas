package com.cursosalura.aplicacionconversordemonedas.servicios;

import com.cursosalura.aplicacionconversordemonedas.config.Configuracion;
import com.cursosalura.aplicacionconversordemonedas.excepcion.ExcepcionConversion;
import com.cursosalura.aplicacionconversordemonedas.historial.RegistroErroresDesarrollador;
import com.cursosalura.aplicacionconversordemonedas.modelos.RespuestaCodigosMonedasIngles;
import com.cursosalura.aplicacionconversordemonedas.modelos.RespuestaListadoMonedas;
import com.cursosalura.aplicacionconversordemonedas.historial.RegistroErrores;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ServicioMonedas {

    private static final String API_KEY = Configuracion.obtener("api.key");
    private static final String BASE_URL = "https://v6.exchangerate-api.com/v6/";

    public RespuestaListadoMonedas obtenerListadoMonedas() throws ExcepcionConversion {
        String direccion = BASE_URL + API_KEY + "/latest/USD";

        try {
            //Solicitud, requerimiento, envío y respuesta del API para obtener todas la monedas
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(direccion))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            //Json que devuelve la API con todas las monedas
            String json = response.body();

            if (json == null || json.isBlank()) {
                String mensaje = "❌ \033[31mLa respuesta del servicio está vacía.";
                // Se registra en el log_errores.txt
                RegistroErrores.registrar(mensaje);
                // Se registra en log_errores_desarrollador.txt
                RegistroErroresDesarrollador.registrar(new Exception("❌ \033[31mLa respuesta del servicio RespuestaListadoMonedas está vacía."));
                throw new ExcepcionConversion(mensaje);
            }

            //Se realiza deserialización JSON (proceso de tomar una cadena de texto en formato JSON y convertirla en un objeto)
            RespuestaListadoMonedas respuesta = new Gson().fromJson(json, RespuestaListadoMonedas.class);

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

    public RespuestaCodigosMonedasIngles obtenerCodigosMonedasIngles() throws ExcepcionConversion {
        String direccion = BASE_URL + API_KEY + "/codes";

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(direccion))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            //Json que devuelve la API con todas las monedas y su descripción en Inglés
            String json = response.body();

            if (json == null || json.isBlank()) {
                String mensaje = "❌ \033[31mLa respuesta del servicio está vacía.";
                // Se registra en el log_errores.txt
                RegistroErrores.registrar(mensaje);
                // Se registra en log_errores_desarrollador.txt
                RegistroErroresDesarrollador.registrar(new Exception("❌ \033[31mLa respuesta del servicio RespuestaCodigosMonedasIngles está vacía."));
                throw new ExcepcionConversion(mensaje);
            }

            //Se realiza deserialización JSON
            RespuestaCodigosMonedasIngles respuestaIngles = new Gson().fromJson(json, RespuestaCodigosMonedasIngles.class);

            if (!"success".equalsIgnoreCase(respuestaIngles.result())) {
                String mensaje = "❌ \033[31mError al obtener los códigos de monedas desde el API.";
                // Se registra en el log_errores.txt
                RegistroErrores.registrar(mensaje);
                // Se registra en log_errores_desarrollador.txt
                RegistroErroresDesarrollador.registrar(new Exception("❌ \033[31mError al obtener los códigos de monedas en ingles desde el API."));
                throw new ExcepcionConversion(mensaje);
            }

            return respuestaIngles;

        } catch (IOException | InterruptedException e) {
            String mensaje = "❌ \033[31mError de conexión con el API: " + e.getMessage();
            // Se registra en el log_errores.txt
            RegistroErrores.registrar(mensaje);
            // Se registra en log_errores_desarrollador.txt
            RegistroErroresDesarrollador.registrar(e);
            throw new ExcepcionConversion(mensaje);
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