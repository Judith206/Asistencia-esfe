package com.esfe.Asistencia.Repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.esfe.Asistencia.Modelos.EstudianteGrupo;

public interface IEstudianteGrupoRepository extends JpaRepository<EstudianteGrupo, Integer> {
    Page<EstudianteGrupo> findByOrderByGrupoId(Pageable pageable);
    Page<EstudianteGrupo> findByOrderByEstudianteDesc(Pageable pageable);
}