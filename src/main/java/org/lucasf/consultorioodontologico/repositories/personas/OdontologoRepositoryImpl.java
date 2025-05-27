package org.lucasf.consultorioodontologico.repositories.personas;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.lucasf.consultorioodontologico.configs.Repository;
import org.lucasf.consultorioodontologico.models.entities.Odontologo;

import java.util.List;

@Repository
public class OdontologoRepositoryImpl extends PersonaRepositoryImpl<Odontologo> implements OdontologoRepository{

    @Inject
    public OdontologoRepositoryImpl(EntityManager em) {
        super(em, Odontologo.class);
    }

    @Override
    public List<Odontologo> porMatricula(String matricula) throws Exception {
        return em.createQuery("select o from odontologos o where o.matricula = :matricula", Odontologo.class)
                .setParameter("matricula", matricula)
                .getResultList();
    }

    @Override
    public List<Odontologo> porEspecialidad(String especialidad) throws Exception {
        return em.createQuery("select o from odontologos o where o.especialidad = :especialidad", Odontologo.class)
                .setParameter("especialidad", especialidad)
                .getResultList();
    }
}
