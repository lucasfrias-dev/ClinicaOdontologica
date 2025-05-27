package org.lucasf.consultorioodontologico.services;

import jakarta.inject.Inject;
import org.lucasf.consultorioodontologico.configs.Service;
import org.lucasf.consultorioodontologico.interceptors.TransactionalJpa;
import org.lucasf.consultorioodontologico.models.entities.Responsable;
import org.lucasf.consultorioodontologico.repositories.personas.ResponsableRepository;

import java.util.List;
import java.util.Optional;

@Service
@TransactionalJpa
public class ResponsableServiceImpl implements ResponsableService {

    @Inject
    private ResponsableRepository responsableRepository;

    @Override
    public List<Responsable> buscarPorResponsabilidad(String responsabilidad) throws Exception {
        return responsableRepository.porResponsabilidad(responsabilidad);
    }

    @Override
    public Optional<Responsable> porId(Long id) throws Exception {
        return Optional.ofNullable(responsableRepository.porId(id));
    }

    @Override
    public List<Responsable> listar() throws Exception {
        return responsableRepository.listar();
    }

    @Override
    public void guardar(Responsable responsable) throws Exception {
        responsableRepository.guardar(responsable);
    }

    @Override
    public void eliminar(Long id) throws Exception {
        responsableRepository.eliminar(id);
    }
}
