package com.esfe.Asistencia.Servicios.Interfaces;

import com.esfe.Asistencia.Modelos.EstudianteGrupo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.*;

public interface IEstudianteGrupoService {
    List<EstudianteGrupo> obtenerTodos();
    Page<EstudianteGrupo> buscarTodosPaginados(Pageable pageable);
    EstudianteGrupo buscarPorId(Integer id);
    EstudianteGrupo crearOeditar(EstudianteGrupo estudianteGrupo);
    void eliminarPorId(Integer id);
}