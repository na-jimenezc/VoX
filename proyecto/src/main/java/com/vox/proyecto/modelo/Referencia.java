package com.vox.proyecto.modelo;
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
public class Referencia {

    @Id
    @GeneratedValue
    private Long idRef;

    private Boolean anonimoRef;
    private String username;

    /*REVISAR EL TEMA DE MANY TO ONE Y ONE TO MANY */
    @ManyToOne
    private Publicacion publicacion;

    @ManyToOne
    private Usuario usuario;

    /*Hacer métodos*/
    public Referencia(Boolean anonimoRef, String username, Publicacion idPub) {
        this.anonimoRef = anonimoRef;
        this.username = username;
        this.idPub = idPub;
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

    public Publicacion getIdPub() {
        return idPub;
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

    public void setIdPub(Publicacion idPub) {
        this.idPub = idPub;
    }

    public void actualizarReferencia(boolean anonimo){
        this.anonimoRef = anonimo,
    }
    
}

    