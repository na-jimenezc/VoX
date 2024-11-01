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

    public Like(Usuario usuario2, Publicacion publicacion2, Boolean anonimo) {
        this.publicacion = publicacion;
        this.anonimoLike = anonimo;
        usuario.getLikes().add(this);
        publicacion.getLikes().add(this);
    }

    public Like() {
        //TODO Auto-generated constructor stub
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

    public void setIdUser(Long idUser) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setIdUser'");
    }


}
