package org.lucasf.consultorioodontologico.models.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name = "odontologos")
public class Odontologo extends Persona {

    private String matricula;
    private String especialidad;
    @OneToMany (mappedBy = "odontologo")
    private List<Turno> turnos;
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    @ManyToMany
    @JoinTable(
            name = "odontologo_horario",
            joinColumns = @JoinColumn(name = "odontologo_id"),
            inverseJoinColumns = @JoinColumn(name = "horario_id")
    )
    private Set<Horario> horarios;

    public Odontologo() {
    }

    public Odontologo(String nombre, String apellido, String dni, String telefono, String direccion, LocalDate fechaNacimiento, String matricula, String especialidad, Usuario usuario) {
        super(nombre, apellido, dni, telefono, direccion, fechaNacimiento);
        this.matricula = matricula;
        this.especialidad = especialidad;
        this.usuario = usuario;
        this.horarios = new HashSet<>();
    }


    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public List<Turno> getTurnos() {
        return turnos;
    }

    public void setTurnos(List<Turno> turnos) {
        this.turnos = turnos;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Set<Horario> getHorarios() {
        return horarios;
    }

    public void setHorarios(Set<Horario> horarios) {
        this.horarios = horarios;
    }
}
