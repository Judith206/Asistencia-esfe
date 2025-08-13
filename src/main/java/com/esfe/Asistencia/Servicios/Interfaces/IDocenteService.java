package com.esfe.Asistencia.Servicios.Interfaces;

import com.esfe.Asistencia.Modelos.Docente;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.*;

public interface IDocenteService {
    Page<Docente> buscarTodosPaginados(Pageable pageable);
    List<Docente> obtenerTodos();
    Optional<Docente> buscarPorId(Integer id);
    Docente crearOeditar(Docente docente);
    void eliminarPorId(Integer id);

}
