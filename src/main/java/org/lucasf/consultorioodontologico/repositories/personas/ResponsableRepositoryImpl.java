package org.lucasf.consultorioodontologico.repositories.personas;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.lucasf.consultorioodontologico.configs.Repository;
import org.lucasf.consultorioodontologico.models.entities.Responsable;

import java.util.List;

@Repository
public class ResponsableRepositoryImpl extends PersonaRepositoryImpl<Responsable> implements ResponsableRepository {

    @Inject
    public ResponsableRepositoryImpl(EntityManager em) {
        super(em, Responsable.class);
    }

    @Override
    public List<Responsable> porResponsabilidad(String tipo) {
        return em.createQuery("select r from responsables r where r.tipoResponsabilidad = :tipo", Responsable.class)
                .setParameter("tipo", tipo)
                .getResultList();
    }
}
