package com.esfe.Asistencia.Servicios.implementaciones;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.esfe.Asistencia.Servicios.Interfaces.IEstudianteGrupoService;
import com.esfe.Asistencia.Repositorios.IEstudianteGrupoRepository;
import com.esfe.Asistencia.Modelos.EstudianteGrupo;

@Service
public class EstudianteGrupoService implements IEstudianteGrupoService {

    @Autowired
    private IEstudianteGrupoRepository estudianteGrupoRepository;

    @Override
    public List<EstudianteGrupo> obtenerTodos() {
        return estudianteGrupoRepository.findAll();
    }

    @Override
    public Page<EstudianteGrupo> buscarTodosPaginados(Pageable pageable) {
        return estudianteGrupoRepository.findByOrderByEstudianteDesc(pageable);
    }

    @Override
    public EstudianteGrupo buscarPorId(Integer id) {
        return estudianteGrupoRepository.findById(id).get();
    }

    @Override
    public EstudianteGrupo crearOeditar(EstudianteGrupo estudianteGrupo) {
        return estudianteGrupoRepository.save(estudianteGrupo);
    }

    @Override
    public void eliminarPorId(Integer id) {
        estudianteGrupoRepository.deleteById(id);
    }
}