package com.vox.proyecto.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data

public class Like {

    @Id
    @GeneratedValue
    private Long idLike;
    private Boolean anonimoLike;

    @ManyToOne
    private Usuario usuario;

    @ManyToOne
    private Publicacion publicacion;

    public Like(Usuario usuario, Publicacion publicacion, Boolean anonimo) {
        this.publicacion = publicacion;
        this.anonimoLike = anonimo;
        usuario.getLikes().add(this);
        publicacion.getLikes().add(this);
    }

    public void eliminarLike() {
        if (publicacion != null) {
            publicacion.getLikes().remove(this);
            this.publicacion = null;
        }
    }

    public void cambiarAnonimatoLike(Boolean anonimo) {
        this.anonimoLike = anonimo;
    }


}
