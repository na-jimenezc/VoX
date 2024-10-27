package Modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Like {

    @Id
    @GeneratedValue
    private Long idLike;

    private Long idUser;
    private Long idPub;
    private Boolean anonimoLike;

    @ManyToOne
    private Publicacion publicacion;

    public Like(Long idUser, Long idPub, Boolean anonimo) {
        this.idUser = idUser;
        this.idPub = idPub;
        this.anonimoLike = anonimo;
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
