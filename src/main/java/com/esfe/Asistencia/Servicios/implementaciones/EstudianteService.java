package com.esfe.Asistencia.Servicios.implementaciones;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import com.esfe.Asistencia.Modelos.Estudiante;
import com.esfe.Asistencia.Servicios.Interfaces.IEstudianteService;
import com.esfe.Asistencia.Repositorios.IEstudianteRepository;

@Service
public class EstudianteService implements IEstudianteService {
    @Autowired
    private IEstudianteRepository estudianteRepository;

    @Override
    public Page<Estudiante> buscarTodosPaginados(Pageable pageable) {
        return estudianteRepository.findAll(pageable);
    }

    @Override
    public List<Estudiante> obtenerTodos() {
        return estudianteRepository.findAll();
    }

    @Override
    public Optional<Estudiante> buscarPorId(Integer id) {
        return estudianteRepository.findById(id);
    }

    @Override
    public Estudiante crearOeditar(Estudiante estudiante) {
        return estudianteRepository.save(estudiante);
    }

    @Override
    public void eliminarPorId(Integer id) {
        estudianteRepository.deleteById(id);
    }
}