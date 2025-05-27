package org.lucasf.consultorioodontologico.repositories.personas;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.lucasf.consultorioodontologico.configs.Repository;
import org.lucasf.consultorioodontologico.models.entities.Paciente;

import java.util.List;

@Repository
public class PacienteRepositoryImpl extends PersonaRepositoryImpl<Paciente> implements PacienteRepository {

    @Inject
    public PacienteRepositoryImpl(EntityManager em) {
        super(em, Paciente.class);
    }

    @Override
    public List<Paciente> tieneObraSocial(boolean obraSocial) {
        return em.createQuery("select p from pacientes p where p.obraSocial = :obraSocial", Paciente.class)
                .setParameter("obraSocial", obraSocial)
                .getResultList();
    }

    @Override
    public List<Paciente> porTipoSangre(String tipoSangre) {
        return em.createQuery("select p from pacientes p where p.tipoSangre = :tipoSangre", Paciente.class)
                .setParameter("tipoSangre", tipoSangre)
                .getResultList();
    }

    @Override
    public List<Paciente> porResponsable(Long responsableId) throws Exception {
        return em.createQuery("select p from pacientes p where p.responsable.id = :responsableId", Paciente.class)
                .setParameter("responsableId", responsableId)
                .getResultList();
    }

    @Override
    public List<Paciente> buscarPorOdontologo(Long odontologoId) {
        return em.createQuery("""
        SELECT DISTINCT t.paciente
        FROM turnos t
        WHERE t.odontologo.id = :odontologoId
        """, Paciente.class)
                .setParameter("odontologoId", odontologoId)
                .getResultList();
    }

}
