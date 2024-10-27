import java.util.ArrayList;
import java.util.List;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class Usuario {

    @Id
    @GeneratedValue
    Long idUsuario;

    String nombre;
    String username;
    String password;
    int edad;
    String carrera;
    String semestre;
    String biografia;
    String email;

    // Relaciones de agregacion y composicion 
    List<Like> likes = new ArrayList<>(); 
    List<Publicacion> publicaciones = new ArrayList<>(); 
    List<Seguimiento> seguidores = new ArrayList<>();  
    List<Seguimiento> seguidos = new ArrayList<>(); 

    // Métodos de la clase
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
        publicacion.getLike().add(nuevoLike);
    }

    public void quitarLike(Publicacion publicacion) {
        publicacion.getLike().removeIf(l -> l.getIdUser().equals(this.idUsuario));
        this.likes.removeIf(l -> l.getIdPub().equals(publicacion.getIdPub()));
    }

    public Publicacion hacerPublicacion(String descripcion, Boolean anonimo) {
        Publicacion nuevaPub = new Publicacion(this.idUsuario, descripcion, anonimo);
        this.publicaciones.add(nuevaPub);
        return nuevaPub;
    }

    public void revelarIdentidadPub(Publicacion publicacion, Boolean anonimo) {
        if (this.publicaciones.contains(publicacion)) {
            publicacion.setAnonimo(anonimo);
        }
    }

    public void revelarTodaIdentidadPub() {
        this.publicaciones.forEach(pub -> pub.setAnonimo(false));
    }

    public void editarUsername(String username, String nuevoUsername) {
        if (this.username.equals(username)) {
            this.username = nuevoUsername;
        }
    }
}
