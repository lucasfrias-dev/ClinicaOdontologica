package org.lucasf.consultorioodontologico.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TurnoDisponibleDTO {
    private String fechaHora; // Esta propiedad es de tipo String para poder devolver una fecha legible

    public TurnoDisponibleDTO(LocalDateTime fechaHora) {
        // Formatear la fecha en el formato deseado para la vista
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        this.fechaHora = fechaHora.format(formatter);
    }

    public String getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(String fechaHora) {
        this.fechaHora = fechaHora;
    }

    @Override
    public String toString() {
        return fechaHora;
    }
}
