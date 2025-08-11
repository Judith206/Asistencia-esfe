package com.esfe.Asistencia.Modelos;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.*;

@Entity
@Table(name = "docentes")
public class Docente {
    @Id
       @GeneratedValue(strategy = GenerationType.IDENTITY)
       private Integer id;

       @NotBlank(message = "El nombre es requerido")
       private String nombre;

       @NotBlank(message = "El apellido es requerido")
         private String apellido;

       @NotBlank(message = "El email es requerido")
       @Email(message = "La entrada no corresponde a un email válido")
       private String email;

       @NotBlank(message = "El teléfono es requerido")
       private String telefono;

       @NotBlank(message = "El nombre de la escuela es requerido")
       private String escuela;

       @ManyToMany
       @JoinTable(name = "docente_grupo",
           joinColumns = @JoinColumn(name = "docente_id"),
           inverseJoinColumns = @JoinColumn(name = "grupo_id")
       )

       private Set<Grupo> grupos = new HashSet<>();

       


       public Integer getId() {
           return id;
       }

       public void setId(Integer id) {
           this.id = id;
       }

       public String getNombre() {
           return nombre;
       }

       public void setNombre(String nombre) {
           this.nombre = nombre;
       }

       public String getApellido() {
           return apellido;
       }

       public void setApellido(String apellido) {
           this.apellido = apellido;
       }

       public String getEmail() {
           return email;
       }

       public void setEmail(String email) {
           this.email = email;
       }

       public String getTelefono() {
           return telefono;
       }

       public void setTelefono(String telefono) {
           this.telefono = telefono;
       }

       public String getEscuela() {
           return escuela;
       }

       public void setEscuela(String escuela) {
           this.escuela = escuela;
       }}
