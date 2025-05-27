package org.lucasf.consultorioodontologico.models;

import java.time.LocalDate;

public enum DiaSemana {
    LUNES, MARTES, MIERCOLES, JUEVES, VIERNES, SABADO, DOMINGO;

    public static DiaSemana desdeFecha(LocalDate fecha) {
        int dia = fecha.getDayOfWeek().getValue(); // 1 = LUNES, 7 = DOMINGO

        switch (dia) {
            case 1: return LUNES;
            case 2: return MARTES;
            case 3: return MIERCOLES;
            case 4: return JUEVES;
            case 5: return VIERNES;
            case 6: return SABADO;
            case 7: return DOMINGO;
            default: throw new IllegalArgumentException("Día inválido: " + dia);
        }
    }
}
