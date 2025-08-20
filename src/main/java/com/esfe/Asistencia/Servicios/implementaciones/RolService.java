package com.esfe.Asistencia.Servicios.implementaciones;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esfe.Asistencia.Modelos.Rol;
import com.esfe.Asistencia.Repositorios.IRolRepository;
import com.esfe.Asistencia.Servicios.Interfaces.IRolService;

@Service
public class RolService implements IRolService {

    @Autowired
    private IRolRepository rolRepository;

    @Override
    public List<Rol> obtenerTodos() {
        return rolRepository.findAll();
    }

}
