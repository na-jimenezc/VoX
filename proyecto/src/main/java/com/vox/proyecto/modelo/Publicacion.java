package com.vox.proyecto.modelo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Publicacion {

    @Id
    @GeneratedValue
    private Long idPub;

    private String descripcion;
    private Date fecha;
    private Boolean anonimo;

    @ManyToOne
    private Usuario autor;

    @OneToMany(mappedBy = "publicacion")
    private List<Like> likes = new ArrayList<>();

    // Constructor con parámetros para crear una publicación completa
    public Publicacion(Usuario autor, String descripcion, Boolean anonimo) {
        this.autor = autor;
        this.descripcion = descripcion;
        this.fecha = new Date();
        this.anonimo = anonimo;
    }

    // Constructor vacío requerido para JPA
    public Publicacion() {
    }


    // Método para cambiar el anonimato de la publicación
    public void cambiarAnonimato(Boolean anonimo) {
        this.anonimo = anonimo;
    }

    // Método para agregar un like a la publicación
    public void agregarLike(Like nuevoLike) {
        this.likes.add(nuevoLike);
    }

    // Método para revelar la identidad del autor
    public void revelarIdentidad() {
        this.anonimo = false;
    }

    // Método para verificar si un usuario es el autor de esta publicación
    public boolean esAutor(Long idUsuario) {
        return this.autor.getIdUsuario().equals(idUsuario);
    }

    // Método para contar la cantidad de likes en la publicación
    public long contarLikes() {
        return likes.size();
    }
}