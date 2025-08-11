package com.esfe.Asistencia.Modelos;

import java.util.*;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Grupo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @NotBlank(message = "El nombre es requerido")
    private String Nombre;

    @NotBlank(message = "La descripción es requerida")
    private String Descripcion;

    @ManyToMany(mappedBy = "grupos")
    private Set<Docente> docentes = new HashSet<>();

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public Set<Docente> getDocentes() {
        return docentes;
    }

    public void setDocentes(Set<Docente> docentes) {
        this.docentes = docentes;
    }

    

    
}
