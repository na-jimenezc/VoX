package com.vox.proyecto.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
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

    @OneToMany(mappedBy = "autor")
    private List<Publicacion> publicaciones = new ArrayList<>();

    @OneToMany(mappedBy = "usuario")
    private List<Like> likes = new ArrayList<>();

    @OneToMany
    private List<Seguimiento> seguidores = new ArrayList<>();

    @OneToMany
    private List<Seguimiento> seguidos = new ArrayList<>();

    // Constructor vacío
    public Usuario() {
    }

    // Constructor completo para inicializar todos los atributos
    public Usuario(String nombre, String username, String password, int edad, String carrera, String semestre,
                   String biografia, String email) {
        this.nombre = nombre;
        this.username = username;
        this.password = password;
        this.edad = edad;
        this.carrera = carrera;
        this.semestre = semestre;
        this.biografia = biografia;
        this.email = email;
    }

    // Métodos para gestionar seguidores y seguidos
    /*
    public void seguir(Usuario usuario) {
        Seguimiento seguimiento = new Seguimiento(this.idUsuario, usuario.getIdUsuario());
        this.seguidos.add(seguimiento);
        usuario.getSeguidores().add(seguimiento);
    }
        */

        public void seguir(Usuario usuario) {
            if (!this.equals(usuario)) { // Prevenir que un usuario se siga a sí mismo
                Seguimiento seguimiento = new Seguimiento(this, usuario); // Cambiar aquí
                this.seguidos.add(seguimiento);
                usuario.getSeguidores().add(seguimiento);
            }
        } 
/*
    public void dejarSeguir(Usuario usuario) {
        this.seguidos.removeIf(s -> s.getIdSeguido().equals(usuario.getIdUsuario()));
        usuario.getSeguidores().removeIf(s -> s.getIdSeguidor().equals(this.idUsuario));
    }
    */

    public void dejarSeguir(Usuario usuario) {
        if (usuario != null && usuario.getIdUsuario() != null) { // Verificamos que usuario no sea nulo y su ID sea válido
            // Eliminamos el seguimiento de la lista de seguidos
            seguidos.removeIf(s -> s.getSeguido() != null && s.getSeguido().getIdUsuario() != null && s.getSeguido().getIdUsuario().equals(usuario.getIdUsuario()));
            
            // Eliminamos el seguimiento de la lista de seguidores del usuario seguido
            usuario.getSeguidores().removeIf(s -> s.getSeguidor() != null && s.getSeguidor().getIdUsuario() != null && s.getSeguidor().getIdUsuario().equals(this.getIdUsuario()));
        }
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

    // Métodos para gestionar likes y publicaciones
/*    public void darLike(Publicacion publicacion, Boolean anonimo) {
        Like nuevoLike = new Like(this, publicacion, anonimo);
        this.likes.add(nuevoLike);
        publicacion.getLikes().add(nuevoLike);
    }*/

    public void darLike(Publicacion publicacion, Boolean anonimo) {
        if (this.likes.stream().noneMatch(l -> l.getPublicacion().equals(publicacion))) { // Prevenir likes duplicados
            Like nuevoLike = new Like(this, publicacion, anonimo);
            this.likes.add(nuevoLike);
            publicacion.getLikes().add(nuevoLike);
        }
    }

    public void quitarLike(Publicacion publicacion) {
        publicacion.getLikes().removeIf(l -> l.getUsuario().equals(this));
        this.likes.removeIf(l -> l.getPublicacion().equals(publicacion));
    }

    public Publicacion hacerPublicacion(String descripcion, Boolean anonimo) {
        Publicacion nuevaPub = new Publicacion(this, descripcion, anonimo);
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

    // Métodos get/set para cada atributo
    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public String getSemestre() {
        return semestre;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }

    public String getBiografia() {
        return biografia;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Publicacion> getPublicaciones() {
        return publicaciones;
    }

    public void setPublicaciones(List<Publicacion> publicaciones) {
        this.publicaciones = publicaciones;
    }

    public List<Like> getLikes() {
        return likes;
    }

    public void setLikes(List<Like> likes) {
        this.likes = likes;
    }

    public List<Seguimiento> getSeguidores() {
        return seguidores;
    }

    public void setSeguidores(List<Seguimiento> seguidores) {
        this.seguidores = seguidores;
    }

    public List<Seguimiento> getSeguidos() {
        return seguidos;
    }

    public void setSeguidos(List<Seguimiento> seguidos) {
        this.seguidos = seguidos;
    }

    // Método pendiente
    public boolean eliminarPublicacion(Publicacion pubAEliminar) {
        return this.publicaciones.remove(pubAEliminar);
    }
}
