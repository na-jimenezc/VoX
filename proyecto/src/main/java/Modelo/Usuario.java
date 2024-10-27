package Modelo;

import java.util.ArrayList;
import java.util.List;
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
public class Usuario {

    @Id
    @GeneratedValue
    private Long idUsuario;

    private String nombre;
    private String username;
    private String password;
    private int edad;
    private String carrera;
    private String semestre;
    private String biografia;
    private String email;

    private List<Like> likes = new ArrayList<>(); 
    private List<Publicacion> publicaciones = new ArrayList<>(); 
    private List<Seguimiento> seguidores = new ArrayList<>();  
    private List<Seguimiento> seguidos = new ArrayList<>(); 

                // Constructor adicional para registrar usuario
        public Usuario(String nombre, String username, String password, int edad, String carrera, String semestre, String biografia, String email) {
        this.nombre = nombre;
        this.username = username;
        this.password = password;
        this.edad = edad;
        this.carrera = carrera;
        this.semestre = semestre;
        this.biografia = biografia;
        this.email = email;
        }

    public void seguir(Usuario usuario) {
        Seguimiento seguimiento = new Seguimiento(this.idUsuario, usuario.getIdUsuario());
        this.seguidos.add(seguimiento);
        usuario.getSeguidores().add(seguimiento);
    }

    public void dejarSeguir(Usuario usuario) {
        this.seguidos.removeIf(s -> s.getIdSeguido().equals(usuario.getIdUsuario()));
        usuario.getSeguidores().removeIf(s -> s.getIdSeguidor().equals(this.idUsuario));
    }

    public List<Usuario> verSeguidores() {
        List<Usuario> listaSeguidores = new ArrayList<>();
        for (Seguimiento seg : seguidores) {
            listaSeguidores.add(seg.getSeguidor());
        }
        return listaSeguidores;
    }

    public Usuario buscarSeguidorUsername(String username) {
        return verSeguidores().stream()
                .filter(usuario -> usuario.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    public Usuario buscarSeguidorNombre(String nombre) {
        return verSeguidores().stream()
                .filter(usuario -> usuario.getNombre().equals(nombre))
                .findFirst()
                .orElse(null);
    }

    public void darLike(Publicacion publicacion, Boolean anonimo) {
        Like nuevoLike = new Like(this.idUsuario, publicacion.getIdPub(), anonimo);
        this.likes.add(nuevoLike);
        publicacion.agregarLike(nuevoLike);
    }

    public void quitarLike(Publicacion publicacion) {
        publicacion.getLikes().removeIf(l -> l.getIdUser().equals(this.idUsuario));
        this.likes.removeIf(l -> l.getIdPub().equals(publicacion.getIdPub()));
    }

    public Publicacion hacerPublicacion(String descripcion, Boolean anonimo) {
        Publicacion nuevaPub = new Publicacion(this.idUsuario, descripcion, anonimo);
        this.publicaciones.add(nuevaPub);
        return nuevaPub;
    }

    public void revelarIdentidadPub(Publicacion publicacion, Boolean anonimo) {
        if (this.publicaciones.contains(publicacion)) {
            publicacion.cambiarAnonimato(anonimo);
        }
    }

    public void revelarTodaIdentidadPub() {
        this.publicaciones.forEach(pub -> pub.cambiarAnonimato(false));
    }

    public void editarUsername(String username, String nuevoUsername) {
        if (this.username.equals(username)) {
            this.username = nuevoUsername;
        }
    }
}
