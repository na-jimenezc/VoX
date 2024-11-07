package com.vox.proyecto.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Referencia {

    @Id
    @GeneratedValue
    private Long idRef;

    private Boolean anonimoRef;
    private String username;
    //Mientras tanto sera un string
    private String comentario;

    @ManyToOne
    private Publicacion publicacion;

    @ManyToOne
    private Usuario usuario;

    // Constructor por defecto
    public Referencia() {
    }

    public Referencia(Boolean anonimoRef, String username, Publicacion publicacion) {
        this.anonimoRef = anonimoRef;
        this.username = username;
        this.publicacion = publicacion;
    }
// Constructor que recibe Usuario, Publicacion, Comentario (String) y UsuarioRef (String)
    public Referencia(Usuario usuario, Publicacion publicacion, String comentario, String usuarioRef) {
    this.usuario = usuario;
    this.publicacion = publicacion;
    this.comentario = comentario;
    this.username = usuarioRef;   // Usuario referenciado (etiquetado en el comentario)
}


    /*Getters*/
    public Long getIdRef() {
        return idRef;
    }

    public Boolean getAnonimoRef() {
        return anonimoRef;
    }

    public String getUsername() {
        return username;
    }

    public Publicacion getPublicacion() {
        return publicacion;
    }

    /*Setters*/
    public void setIdRef(Long idRef) {
        this.idRef = idRef;
    }

    public void setAnonimoRef(Boolean anonimoRef) {
        this.anonimoRef = anonimoRef;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPublicacion(Publicacion publicacion) {
        this.publicacion = publicacion;
    }

    public void actualizarReferencia(boolean anonimo) {
        this.anonimoRef = anonimo;
    }
}
