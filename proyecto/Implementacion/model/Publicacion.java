import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Like {

    @Id
    private Long idLike; 
    private Boolean anonimoLike;

    @ManyToOne
    private Publicacion publicacion;

    /*Constructor básico del like*/
    public Like(Long idLike, Boolean anonimo) {
        this.idLike = idLike;
        this.anonimoLike = anonimo;
    }

    /*Método para eliminar el like*/
    public void eliminarLike() {
        if (publicacion != null) {
            publicacion.getLikes().remove(this);
            this.publicacion = null;
        }
    }

    /*Método para cambiar el anonimato del like*/
    public void cambiarAnonimatoLike(Boolean anonimo) {
        this.anonimoLike = anonimo;
    }
}