package com.cursosalura.aplicacionconversordemonedas.modelos;

import com.google.gson.annotations.SerializedName;

public record RespuestaConversionMonto(String result,
                                       String documentation,
                                       String terms_of_use,
                                       long time_last_update_unix,
                                       String time_last_update_utc,
                                       long time_next_update_unix,
                                       String time_next_update_utc,
                                       String base_code,
                                       String target_code,
                                       double conversion_rate,
                                       double conversion_result,
                                       //java no maneja guiones(-) y la API manda el campo
                                       //error-type de esa manera, entonces lo convierto a errortype
                                       @SerializedName("error-type") String errortype) implements RespuestaAPIErr {

}