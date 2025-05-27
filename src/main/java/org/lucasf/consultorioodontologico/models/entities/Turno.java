package org.lucasf.consultorioodontologico.models.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity(name = "turnos")
public class Turno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_turno")
    private Long idTurno;
    @Column(name = "fecha_turno")
    @Temporal(TemporalType.DATE)
    private LocalDate fechaTurno;
    @Column(name = "hora_turno")
    @Temporal(TemporalType.TIME)
    private LocalTime horaTurno;
    private String afeccion;
    @ManyToOne
    @JoinColumn(name="odontologo_id")
    private Odontologo odontologo;
    @ManyToOne
    @JoinColumn(name="paciente_id")
    private Paciente paciente;

    public Turno() {
    }

    public Turno(Long idTurno, LocalDate fechaTurno, LocalTime horaTurno, String afeccion) {
        this.idTurno = idTurno;
        this.fechaTurno = fechaTurno;
        this.horaTurno = horaTurno;
        this.afeccion = afeccion;
    }

    public Long getIdTurno() {
        return idTurno;
    }

    public void setIdTurno(Long idTurno) {
        this.idTurno = idTurno;
    }

    public LocalDate getFechaTurno() {
        return fechaTurno;
    }

    public void setFechaTurno(LocalDate fechaTurno) {
        this.fechaTurno = fechaTurno;
    }

    public LocalTime getHoraTurno() {
        return horaTurno;
    }

    public void setHoraTurno(LocalTime horaTurno) {
        this.horaTurno = horaTurno;
    }

    public String getAfeccion() {
        return afeccion;
    }

    public void setAfeccion(String afeccion) {
        this.afeccion = afeccion;
    }

    public Odontologo getOdontologo() {
        return odontologo;
    }

    public void setOdontologo(Odontologo odontologo) {
        this.odontologo = odontologo;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }
}
