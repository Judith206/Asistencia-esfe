package com.esfe.Asistencia.Repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import com.esfe.Asistencia.Modelos.Estudiante;

public interface IEstudianteRepository extends JpaRepository<Estudiante, Integer> {
}
