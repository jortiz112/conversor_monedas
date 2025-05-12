package com.cursosalura.aplicacionconversordemonedas.modelos;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public record RespuestaListadoMonedas(String result,
                                      String base_code,
                                      @SerializedName("conversion_rates") Map<String, Double> conversionRates,
                                      @SerializedName("error-type") String errortype) implements RespuestaAPIErr {
}
