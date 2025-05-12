package com.cursosalura.aplicacionconversordemonedas.config;

import com.cursosalura.aplicacionconversordemonedas.historial.RegistroErrores;
import com.cursosalura.aplicacionconversordemonedas.historial.RegistroErroresDesarrollador;

import java.io.InputStream;
import java.util.Properties;

public class Configuracion {

    private static final Properties properties = new Properties();

    //Bloque de inicialización estatico en java, éste
    // intenta cargar el archivo config.properties desde el classpath
    // cuando la clase Configuracion se carga
    static {
        try (InputStream input = Configuracion.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                String mensaje = "❌ No se encontró el archivo config.properties en el classpath.";
                // Se registra en el log_errores.txt
                RegistroErrores.registrar(mensaje);
                // Se registra en log_errores_desarrollador.txt
                RegistroErroresDesarrollador.registrar(new Exception("❌ No se encontró el archivo config.properties en el classpath."));
                throw new RuntimeException(mensaje);
            }
            properties.load(input);
        } catch (Exception e) {
            String mensaje = "❌ Error al cargar el archivo de configuración: ";
            // Se registra en el log_errores.txt
            RegistroErrores.registrar(mensaje);
            // Se registra en log_errores_desarrollador.txt
            RegistroErroresDesarrollador.registrar(new Exception(mensaje));
            throw new RuntimeException(mensaje);
        }
    }

    public static String obtener(String clave) {
        return properties.getProperty(clave);
    }
}