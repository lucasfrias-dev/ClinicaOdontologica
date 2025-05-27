package org.lucasf.consultorioodontologico.models.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

import java.time.LocalDate;
import java.util.Date;

@Entity(name = "secretarios")
public class Secretario extends Persona {

    private String sector;
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    public Secretario() {
    }

    public Secretario(String nombre, String apellido, String dni, String telefono, String direccion, LocalDate fechaNacimiento, String sector, Usuario usuario) {
        super(nombre, apellido, dni, telefono, direccion, fechaNacimiento);
        this.sector = sector;
        this.usuario = usuario;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
