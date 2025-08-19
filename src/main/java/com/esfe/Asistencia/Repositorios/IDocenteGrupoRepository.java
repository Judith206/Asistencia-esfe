package com.esfe.Asistencia.Repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.esfe.Asistencia.Modelos.DocenteGrupo;

public interface IDocenteGrupoRepository extends JpaRepository<DocenteGrupo, Integer> {

    Page<DocenteGrupo> findByOrderByGrupoId(Pageable pageable);

    Page<DocenteGrupo> findByOrderByDocenteDesc(Pageable pageable);

}
