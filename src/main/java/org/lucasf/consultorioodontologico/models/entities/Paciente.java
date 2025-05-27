package org.lucasf.consultorioodontologico.models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity(name = "pacientes")
public class Paciente extends Persona {

    private boolean obraSocial;
    private String tipoSangre;
    @ManyToOne
    private Responsable responsable;
    @OneToMany (mappedBy = "paciente")
    private List<Turno> turnos;

    public Paciente() {
    }

    public Paciente(String nombre, String apellido, String dni, String telefono, String direccion, LocalDate fechaNacimiento, boolean obraSocial, String tipoSangre, Responsable responsable, List<Turno> turnos) {
        super(nombre, apellido, dni, telefono, direccion, fechaNacimiento);
        this.obraSocial = obraSocial;
        this.tipoSangre = tipoSangre;
        this.responsable = responsable;
        this.turnos = turnos;
    }

    public boolean isObraSocial() {
        return obraSocial;
    }

    public void setObraSocial(boolean obraSocial) {
        this.obraSocial = obraSocial;
    }

    public String getTipoSangre() {
        return tipoSangre;
    }

    public void setTipoSangre(String tipoSangre) {
        this.tipoSangre = tipoSangre;
    }

    public Responsable getResponsable() {
        return responsable;
    }

    public void setResponsable(Responsable responsable) {
        this.responsable = responsable;
    }

    public List<Turno> getTurnos() {
        return turnos;
    }

    public void setTurnos(List<Turno> turnos) {
        this.turnos = turnos;
    }
}
