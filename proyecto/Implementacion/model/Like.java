import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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

    // Constructor con parámetros para los atributos principales
    public Like(Long idUser, Long idPub, Boolean anonimo) {
        this.idUser = idUser;
        this.idPub = idPub;
        this.anonimoLike = anonimo;
    }

    //Metodos de la clase
    public void eliminarLike(Like like) {

    }

}