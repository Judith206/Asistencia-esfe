package com.esfe.Asistencia.Servicios.implementaciones;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.esfe. Asistencia.Servicios.Interfaces.IDocenteGrupoService;
import com.esfe.Asistencia.Repositorios.IDocenteGrupoRepository;
import com.esfe.Asistencia.Modelos.DocenteGrupo;

@Service
public class DocentesGrupoService implements IDocenteGrupoService {

    @Autowired
    private  IDocenteGrupoRepository docenteGrupoRepository;

    @Override
    public List<DocenteGrupo> obtenerTodos() {
        return docenteGrupoRepository.findAll();
    }

    @Override
    public Page<DocenteGrupo> buscarTodosPaginados(Pageable pageable) {
        return docenteGrupoRepository.findByOrderByDocenteDesc(pageable);
    }

    @Override
    public DocenteGrupo buscarPorId(Integer id) {
        return docenteGrupoRepository.findById(id).get();
    }

    @Override
    public DocenteGrupo crearOeditar(DocenteGrupo docenteGrupo) {
        return docenteGrupoRepository.save(docenteGrupo);
    }

    @Override
    public void eliminarPorId(Integer id) {
        docenteGrupoRepository.deleteById(id);
    }

}
