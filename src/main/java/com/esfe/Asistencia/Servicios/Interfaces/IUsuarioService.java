package com.esfe.Asistencia.Servicios.Interfaces;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.esfe.Asistencia.Modelos.Usuario;

public interface IUsuarioService {
    Page<Usuario> obtenerTodosPaginados(Pageable pageable);

    List<Usuario> obtenerTodos();

    Usuario obtenerPorId(Integer id);

    Usuario crearOeditar(Usuario usuario);

    void eliminarPorId(Integer id);
}
