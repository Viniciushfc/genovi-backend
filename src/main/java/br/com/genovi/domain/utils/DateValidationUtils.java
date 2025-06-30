package br.com.genovi.domain.utils;

import java.time.LocalDateTime;

public class DateValidationUtils {

    public static void validarPeriodo(LocalDateTime dataInicio, LocalDateTime dataFim) {
        if (dataInicio == null || dataFim == null) {
            throw new IllegalArgumentException("Datas não podem ser nulas.");
        }

        if (dataInicio.isAfter(dataFim)) {
            throw new IllegalArgumentException("Data de início não pode ser após a data de fim.");
        }

        if (dataFim.isBefore(dataInicio)) {
            throw new IllegalArgumentException("Data de fim não pode ser antes da data de início.");
        }
    }
}
