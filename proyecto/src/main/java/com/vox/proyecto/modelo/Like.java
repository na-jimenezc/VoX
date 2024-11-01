package com.vox.proyecto.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Like {

    @Id
    @GeneratedValue
    private Long idLike;
    private Boolean anonimoLike;

    @ManyToOne
    private Usuario usuario;

    @ManyToOne
    private Publicacion publicacion;

    // Constructor completo
    public Like(Usuario usuario, Publicacion publicacion, Boolean anonimo) {
        this.usuario = usuario;
        this.publicacion = publicacion;
        this.anonimoLike = anonimo;
        usuario.getLikes().add(this);
        publicacion.getLikes().add(this);
    }

    // Constructor sin argumentos
    public Like() {
        // Constructor vacío
    }

    // Método para eliminar el like de una publicación
    public void eliminarLike() {
        if (publicacion != null) {
            publicacion.getLikes().remove(this);
            this.publicacion = null;
        }
    }

    // Método para cambiar el anonimato del like
    public void cambiarAnonimatoLike(Boolean anonimo) {
        this.anonimoLike = anonimo;
    }

    // Getters y Setters
    public Long getIdLike() {
        return idLike;
    }

    public void setIdLike(Long idLike) {
        this.idLike = idLike;
    }

    public Boolean getAnonimoLike() {
        return anonimoLike;
    }

    public void setAnonimoLike(Boolean anonimoLike) {
        this.anonimoLike = anonimoLike;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Publicacion getPublicacion() {
        return publicacion;
    }

    public void setPublicacion(Publicacion publicacion) {
        this.publicacion = publicacion;
    }

    // Métodos no implementados (eliminados para evitar errores)
    public void setIdUser(Long idUser) {
        throw new UnsupportedOperationException("Unimplemented method 'setIdUser'");
    }

    public Long getIdUser() {
        throw new UnsupportedOperationException("Unimplemented method 'getIdUser'");
    }
}
