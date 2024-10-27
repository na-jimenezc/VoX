import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
public class Publicacion {

    @Id
    @GeneratedValue
    private Long idPub;

    private String descripcion;
    private Date fecha;
    private Boolean anonimo;

    // Relaciones de agregacion y composicion 
    @ManyToOne
    Usuario autor; 

    @OneToMany(mappedBy = "publicacion")
    List<Like> likes = new ArrayList<>();

    // Constructor con parámetros para los atributos principales
    public Publicacion(Long idAutor, String descripcion, Boolean anonimo) {
        this.descripcion = descripcion;
        this.fecha = new Date(); // Fecha de creación actual
        this.anonimo = anonimo;
    }

    // Método para cambiar el anonimato de una publicación
    public void cambiarAnonimato(Boolean anonimo) {
        this.anonimo = anonimo;
    }

    // Método para agregar un like a la publicación
    public void agregarLike(Like nuevoLike) {
        this.likes.add(nuevoLike);
    }
}
