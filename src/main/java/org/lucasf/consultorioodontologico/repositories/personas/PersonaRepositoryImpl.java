package org.lucasf.consultorioodontologico.repositories.personas;

import jakarta.inject.Inject;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import org.lucasf.consultorioodontologico.models.entities.Persona;

import java.util.List;

public abstract class PersonaRepositoryImpl<T extends Persona> implements PersonaRepository<T> {

    protected EntityManager em;
    private final Class<T> entityClass;

    @Inject
    public PersonaRepositoryImpl(EntityManager em, Class<T> entityClass) {
        this.em = em;
        this.entityClass = entityClass;
    }

    @Override
    public T porDni(String dni) throws Exception {
        return em.createQuery("select p from " + getEntityName() + " p where p.dni = :dni", entityClass)
                .setParameter("dni", dni)
                .getSingleResult();
    }

    @Override
    public List<T> porNombre(String nombre) {
        return em.createQuery("select p from " + getEntityName() + " p where p.nombre = :nombre", entityClass)
                .setParameter("nombre", nombre)
                .getResultList();
    }

    @Override
    public List<T> porApellido(String apellido) throws Exception {
        return em.createQuery("select p from " + getEntityName() + " p where p.apellido = :apellido", entityClass)
                .setParameter("apellido", apellido)
                .getResultList();
    }

    @Override
    public List<T> listar() throws Exception {
        return em.createQuery("select p from " + getEntityName() + " p", entityClass)
                .getResultList();
    }

    @Override
    public T porId(Long id) throws Exception {
        return em.find(entityClass, id);
    }

    @Override
    public void guardar(T persona) throws Exception {
        if (persona.getId() != null && persona.getId() > 0){
            em.merge(persona);
        } else {
            em.persist(persona);
        }
    }

    @Override
    public void eliminar(Long id) throws Exception {
        em.remove(porId(id));
    }

    // Método auxiliar para obtener el nombre de la entidad JPA
    private String getEntityName() {
        Entity entityAnnotation = entityClass.getAnnotation(Entity.class);
        // Si tiene @Entity(name = "..."), usamos ese nombre; si no, usamos getSimpleName()
        return (entityAnnotation != null && !entityAnnotation.name().isEmpty())
                ? entityAnnotation.name()
                : entityClass.getSimpleName();
    }
}
