package org.lucasf.consultorioodontologico.repositories;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.lucasf.consultorioodontologico.configs.Repository;
import org.lucasf.consultorioodontologico.models.entities.Usuario;

import java.util.List;

@Repository
public class UsuarioRepositoryImpl implements UsuarioRepository{

    @Inject
    private EntityManager em;

    @Override
    public Usuario porNombreUsuario(String nombreUsuario) throws Exception {
        try {
            return em.createQuery("select u from Usuario u where u.nombreUsuario = :username", Usuario.class)
                    .setParameter("username", nombreUsuario)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }

    }

    @Override
    public Usuario porPersonal(Long idPersonal) throws Exception {
        return em.createQuery("select u from Persona u where u.id = :idPersonal", Usuario.class)
                .setParameter("idPersonal", idPersonal)
                .getSingleResult();
    }


    @Override
    public List<Usuario> listar() throws Exception {
        return em.createQuery("from Usuario", Usuario.class).getResultList();
    }

    @Override
    public Usuario porId(Long id) throws Exception {
        return em.find(Usuario.class, id);
    }

    @Override
    public void guardar(Usuario usuario) throws Exception {
        if (usuario.getIdUsuario() != null && usuario.getIdUsuario() > 0) {
            em.merge(usuario);
        } else {
            em.persist(usuario);
        }
    }

    @Override
    public void eliminar(Long id) throws Exception {
        em.remove(porId(id));
    }
}
