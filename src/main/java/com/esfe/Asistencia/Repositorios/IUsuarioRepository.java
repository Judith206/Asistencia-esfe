package com.esfe.Asistencia.Repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import com.esfe.Asistencia.Modelos.Usuario;

public interface IUsuarioRepository extends JpaRepository<Usuario, Integer> {

}
