package org.lucasf.consultorioodontologico.models.entities;

import jakarta.persistence.*;
import org.lucasf.consultorioodontologico.models.DiaSemana;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

@Entity(name = "horarios")
public class Horario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_horario")
    private Long idHorario;
    @Enumerated(EnumType.STRING)
    private DiaSemana dia;
    @Column(name = "hora_inicio")
    @Temporal(TemporalType.TIME)
    private LocalTime horaInicio;
    @Column(name = "hora_fin")
    @Temporal(TemporalType.TIME)
    private LocalTime horaFin;
    @ManyToMany(mappedBy = "horarios")
    private Set<Odontologo> odontologos;

    public Horario() {
    }

    public Horario(Long idHorario, LocalTime horaInicio, DiaSemana dia, LocalTime horaFin, Set<Odontologo> odontologos) {
        this.idHorario = idHorario;
        this.horaInicio = horaInicio;
        this.dia = dia;
        this.horaFin = horaFin;
        this.odontologos = odontologos;
    }

    public Long getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(Long idHorario) {
        this.idHorario = idHorario;
    }

    public DiaSemana getDia() {
        return dia;
    }

    public void setDia(DiaSemana dia) {
        this.dia = dia;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }

    public Set<Odontologo> getOdontologos() {
        return odontologos;
    }

    public void setOdontologos(Set<Odontologo> odontologos) {
        this.odontologos = odontologos;
    }
}
