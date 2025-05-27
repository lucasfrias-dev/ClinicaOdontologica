package org.lucasf.consultorioodontologico.models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

@Entity(name = "responsables")
public class Responsable extends Persona {

    @OneToMany(mappedBy = "responsable")
    private Set<Paciente> pacientes;
    private String tipoResponsabilidad;

    public Responsable() {
    }

    public Responsable(String nombre, String apellido, String dni, String telefono, String direccion, LocalDate fechaNacimiento, Set<Paciente> pacientes, String tipoResponsabilidad) {
        super(nombre, apellido, dni, telefono, direccion, fechaNacimiento);
        this.pacientes = pacientes;
        this.tipoResponsabilidad = tipoResponsabilidad;
    }

    public String getTipoResponsabilidad() {
        return tipoResponsabilidad;
    }

    public void setTipoResponsabilidad(String tipoResponsabilidad) {
        this.tipoResponsabilidad = tipoResponsabilidad;
    }

    public Set<Paciente> getPacientes() {
        return pacientes;
    }

    public void setPacientes(Set<Paciente> pacientes) {
        this.pacientes = pacientes;
    }
}
