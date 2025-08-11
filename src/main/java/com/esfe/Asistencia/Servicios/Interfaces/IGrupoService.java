package com.esfe.Asistencia.Servicios.Interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

import com.esfe.Asistencia.Modelos.Grupo;
import java.util.List;

public interface IGrupoService {
    Page<Grupo> buscarTodos(Pageable pageable);

    List<Grupo> obtenerTodos();

    Optional<Grupo> buscarPorId(Integer id);

    Grupo crearOeditar(Grupo grupo);

    void eliminarPorId(Integer id);

}
