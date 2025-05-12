package com.cursosalura.aplicacionconversordemonedas.modelos;

import java.util.List;

public record RespuestaCodigosMonedasIngles(String result,
                                            List<List<String>> supported_codes) {
}
