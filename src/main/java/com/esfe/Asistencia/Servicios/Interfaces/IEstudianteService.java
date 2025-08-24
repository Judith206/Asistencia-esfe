package com.esfe.Asistencia.Servicios.Interfaces;

import com.esfe.Asistencia.Modelos.Estudiante;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.*;

public interface IEstudianteService {
    Page<Estudiante> buscarTodosPaginados(Pageable pageable);
    List<Estudiante> obtenerTodos();
    Optional<Estudiante> buscarPorId(Integer id);
    Estudiante crearOeditar(Estudiante estudiante);
    void eliminarPorId(Integer id);
}
