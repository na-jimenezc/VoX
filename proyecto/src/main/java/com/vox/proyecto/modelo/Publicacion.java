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

    //ATRIBUTO PAARA REVISAR COMENTARIOS
    @ManyToOne
    private Publicacion publicacionPadre;

    @OneToMany(mappedBy = "publicacion")
    private List<Like> likes = new ArrayList<>();

    //MAPEADO JPA PARA LOS COMENTARIOS
    @OneToMany(mappedBy = "publicacionPadre")
    private List<Publicacion> comentarios = new ArrayList<>();

    //MAPEADO JPA PARA MULTIMEDIA
    @OneToMany(mappedBy = "publicacion")
    private List<Multimedia> multimedia = new ArrayList<>();

    //Constructor actualizado para tener lista de comentarios
    public Publicacion(long idPub, String descripcion, Date fecha, boolean anonimo) {
        this.idPub = idPub;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.anonimo = anonimo;
        this.comentarios = new ArrayList<>(); 
        this.multimedia = new ArrayList<>(); 
    }


    // Constructor vacío requerido para JPA
    public Publicacion() {
    }


    public Publicacion(Object object, String descripcion2, Boolean anonimo2) {
        //TODO Auto-generated constructor stub
    }


    // Método para cambiar el anonimato de la publicación
    public void cambiarAnonimato(Boolean anonimo) {
        this.anonimo = anonimo;
    }

    // Método para agregar un like a la publicación
    public void agregarLike(Like nuevoLike) {
        // Check if this user has already liked this publication
        for (Like like : likes) {
            if (like.getUsuario().equals(nuevoLike.getUsuario())) {
                return; // User has already liked this publication, do not add again
            }
        }
        this.likes.add(nuevoLike); // Add the new like if it is unique
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

    //Métodos para manejar comentarios

    /*Para añadir un nuevo comentario*/
    public void addComentario(Publicacion comentario) {
        /*Se establece la publicación actual como padre*/
        comentario.setPublicacionPadre(this);
        comentarios.add(comentario);
    }

    /*Para eliminar un comentario*/
    public void removeComentario(Publicacion comentario) {
        comentarios.remove(comentario);
    }

    /*Getter*/
    public List<Publicacion> getComentarios() {
        return comentarios;
    }

    /*Getter y setter para la publicación padre*/
    public Publicacion getPublicacionPadre() {
        return publicacionPadre;
    }

    public void setPublicacionPadre(Publicacion publicacionPadre) {
        this.publicacionPadre = publicacionPadre;
    }

    public void actualizarReferencia(long idRef, boolean anonimo, List<Referencia> referencias) {
        for (Referencia r : referencias) {
            if (r.getIdRef() != null && r.getIdRef().equals(idRef)) {
                r.setAnonimoRef(anonimo); 
                break; 
            }
        }
    }
}