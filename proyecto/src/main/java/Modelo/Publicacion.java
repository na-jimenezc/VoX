package Modelo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import jakarta.persistence.ManyToMany;
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

    @ManyToMany
    private Usuario autor; 

    @OneToMany(mappedBy = "publicacion")
    private List<Like> likes = new ArrayList<>();

    public Publicacion(Long idAutor, String descripcion, Boolean anonimo) {
        this.descripcion = descripcion;
        this.fecha = new Date();
        this.anonimo = anonimo;
    }

    public void cambiarAnonimato(Boolean anonimo) {
        this.anonimo = anonimo;
    }

    public void agregarLike(Like nuevoLike) {
        this.likes.add(nuevoLike);
    }
}
