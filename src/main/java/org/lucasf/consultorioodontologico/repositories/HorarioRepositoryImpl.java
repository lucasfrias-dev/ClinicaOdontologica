package org.lucasf.consultorioodontologico.repositories;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.lucasf.consultorioodontologico.configs.Repository;
import org.lucasf.consultorioodontologico.models.DiaSemana;
import org.lucasf.consultorioodontologico.models.entities.Horario;

import java.time.LocalDate;
import java.util.List;

@Repository
public class HorarioRepositoryImpl implements HorarioRepository{

    @Inject
    private EntityManager em;

    @Override
    public List<Horario> porDia(DiaSemana dia) {
        return em.createQuery("SELECT h FROM horarios h WHERE h.dia = :dia", Horario.class)
                .setParameter("dia", dia)
                .getResultList();
    }

    @Override
    public List<Horario> porOdontologo(Long odontologoId) throws Exception {
        return em.createQuery("SELECT h FROM horarios h join odontologos o WHERE o.id = :odontologoId", Horario.class)
                .setParameter("odontologoId", odontologoId)
                .getResultList();
    }

    @Override
    public List<Horario> listar() throws Exception {
        return em.createQuery("SELECT h FROM horarios h", Horario.class)
                .getResultList();
    }

    @Override
    public Horario porId(Long id) throws Exception {
        return em.find(Horario.class, id);
    }

    @Override
    public void guardar(Horario horario) throws Exception {
        if (horario.getIdHorario() != null && horario.getIdHorario() > 0) {
            em.merge(horario);
        } else {
            em.persist(horario);
        }
    }

    @Override
    public void eliminar(Long id) throws Exception {
        em.remove(porId(id));
    }
}
