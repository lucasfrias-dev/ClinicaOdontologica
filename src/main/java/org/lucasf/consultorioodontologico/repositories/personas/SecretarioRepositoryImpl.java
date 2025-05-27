package org.lucasf.consultorioodontologico.repositories.personas;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.lucasf.consultorioodontologico.configs.Repository;
import org.lucasf.consultorioodontologico.models.entities.Secretario;

import java.util.List;

@Repository
public class SecretarioRepositoryImpl extends PersonaRepositoryImpl<Secretario> implements SecretarioRepository {

    @Inject
    public SecretarioRepositoryImpl(EntityManager em) {
        super(em, Secretario.class);
    }

    @Override
    public List<Secretario> porSector(String sector) {
        return em.createQuery("select s from secretarios s where s.sector = :sector", Secretario.class)
                .setParameter("sector", sector)
                .getResultList();
    }
}
