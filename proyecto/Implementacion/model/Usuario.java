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

    //Relaciones de agregacion y composicion 
    List<Like> likes = new ArrayList<>(); 
    List<Publicacion> publicaciones = new ArrayList<>(); 
    List<Seguimiento> seguidores = new ArrayList<>();  
    List<Seguimiento> seguidos = new ArrayList<>(); 

    // Métodos de la clase

    /*Seguir un usuario*/
    public void seguir(Usuario usuario) {
        Seguimiento seguimiento = new Seguimiento(this.idUsuario, usuario.getIdUsuario());
        this.seguidos.add(seguimiento);
        usuario.getSeguidores().add(seguimiento);
    }

    /*Dejar de seguir*/
    public void dejarSeguir(Usuario usuario) {
        this.seguidos.removeIf(s -> s.getIdSeguido().equals(usuario.getIdUsuario()));
        usuario.getSeguidores().removeIf(s -> s.getIdSeguidor().equals(this.idUsuario));
    }

    /*Ver seguidores*/
    public List<Usuario> verSeguidores() {
        List<Usuario> listaSeguidores = new ArrayList<>();
        for (Seguimiento seg : seguidores) {
            listaSeguidores.add(seg.getSeguidor());
        }
        return listaSeguidores;
    }

    /*Buscar seguidor por Username*/
    public Usuario buscarSeguidorUsername(String username) {
        return verSeguidores().stream()
                .filter(usuario -> usuario.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    /*Buscar seguidor por nombre*/
    public Usuario buscarSeguidorNombre(String nombre) {
        return verSeguidores().stream()
                .filter(usuario -> usuario.getNombre().equals(nombre))
                .findFirst()
                .orElse(null);
    }

    /*Dar like a una publicación*/
    public void darLike(Publicacion publicacion, Boolean anonimo) {
        Long nuevoIdLike = (long) (publicacion.getLikes().size() + 1);
        Like nuevoLike = new Like(nuevoIdLike, anonimo);
        nuevoLike.setPublicacion(publicacion);
        publicacion.getLikes().add(nuevoLike);
    }

    /*Dar dislike a una publicación*/
    public void quitarLike(Publicacion publicacion) {
        publicacion.getLike().removeIf(l -> l.getIdUser().equals(this.idUsuario));
        this.likes.removeIf(l -> l.getIdPub().equals(publicacion.getIdPub()));
    }

    /*Hacer una nueva publicación*/
    public Publicacion hacerPublicacion(String descripcion, Boolean anonimo) {
        Publicacion nuevaPub = new Publicacion(this.idUsuario, descripcion, anonimo);
        this.publicaciones.add(nuevaPub);
        return nuevaPub;
    }

    /*Eliminar una publicación*/
    public boolean eliminarPublicacion(Publicacion publicacion) {
        if (this.publicaciones.contains(publicacion)) {
            this.publicaciones.remove(publicacion);
            return true; 
        }
        return false; 
    }

    /*Revelar identidad de una publicación*/
    public void revelarIdentidadPub(Publicacion publicacion, Boolean anonimo) {
        if (this.publicaciones.contains(publicacion)) {
            publicacion.setAnonimo(anonimo);
        }
    }

    /*Revelar identidad de todas las publicaciones*/
    public void revelarTodaIdentidadPub() {
        this.publicaciones.forEach(pub -> pub.setAnonimo(false));
    }

    /*Editar username*/
    public void editarUsername(String username, String nuevoUsername) {
        if (this.username.equals(username)) {
            this.username = nuevoUsername;
        }
    }

    /*Editar biografía*/
    public void editarBiografia(String nuevaBiografia) {
        this.biografia = nuevaBiografia;
    }
}
