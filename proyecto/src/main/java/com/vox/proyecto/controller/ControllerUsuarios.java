package com.vox.proyecto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.vox.proyecto.modelo.Usuario;
import com.vox.proyecto.modelo.Publicacion;
import com.vox.proyecto.repository.UsuarioRepository;
import com.vox.proyecto.repository.PublicacionRepository;

@Controller
public class ControllerUsuarios {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PublicacionRepository publicacionRepository;

    // Método para registrar un nuevo usuario
    public void registrarUsuario(String nombre, String username, String password, 
                                  int edad, String carrera, String semestre, 
                                  String biografia, String email) {
        Usuario nuevoUsuario = new Usuario(nombre, username, password, edad, carrera, semestre, biografia, email, false);
        usuarioRepository.save(nuevoUsuario);
    }

    // Método para autenticar un usuario
    public boolean autenticarUsuario(String username, String password) {
        Usuario usuario = usuarioRepository.findByUsername(username);
        return usuario != null && usuario.getPassword().equals(password);
    }

    // Método para verificar si un usuario ya existe
    public boolean verificarUsuarioExistente(String username) {
        return usuarioRepository.findByUsername(username) != null;
    }

    // Método para borrar un usuario
    public void borrarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    // Método para editar el nombre de usuario
    public void editarUsuario(String username, String nuevoUsername) {
        Usuario usuario = usuarioRepository.findByUsername(username);
        if (usuario != null) {
            usuario.setUsername(nuevoUsername);
            usuarioRepository.save(usuario);
        }
    }

    // Método para que un usuario siga a otro
    public void seguirUsuario(Long idSeguidor, Long idSeguido) {
        Usuario seguidor = usuarioRepository.findById(idSeguidor).orElse(null);
        Usuario seguido = usuarioRepository.findById(idSeguido).orElse(null);
        
        if (seguidor != null && seguido != null) {
            seguidor.seguir(seguido);
            usuarioRepository.save(seguidor);
            usuarioRepository.save(seguido);
        }
    }

    // Método para que un usuario deje de seguir a otro
    public void dejarDeSeguir(Long idSeguidor, Long idSeguido) {
        Usuario seguidor = usuarioRepository.findById(idSeguidor).orElse(null);
        Usuario seguido = usuarioRepository.findById(idSeguido).orElse(null);
        
        if (seguidor != null && seguido != null) {
            seguidor.dejarSeguir(seguido);
            usuarioRepository.save(seguidor);
            usuarioRepository.save(seguido);
        }
    }

    // Método para dar like a una publicación
    public void darLike(Long idUsuario, Long idPublicacion, boolean anonimo) {
        Usuario usuario = usuarioRepository.findById(idUsuario).orElse(null);
        Publicacion publicacion = publicacionRepository.findById(idPublicacion).orElse(null);
        
        if (usuario != null && publicacion != null) {
            usuario.darLike(publicacion, anonimo);
            usuarioRepository.save(usuario);
            publicacionRepository.save(publicacion);
        }
    }

    // Método para quitar un like de una publicación
    public void quitarLike(Long idUsuario, Long idPublicacion) {
        Usuario usuario = usuarioRepository.findById(idUsuario).orElse(null);
        Publicacion publicacion = publicacionRepository.findById(idPublicacion).orElse(null);
        
        if (usuario != null && publicacion != null) {
            usuario.quitarLike(publicacion);
            usuarioRepository.save(usuario);
            publicacionRepository.save(publicacion);
        }
    }

    // Método para hacer una publicación
    public void hacerPublicacion(Long idUsuario, String descripcion, boolean anonimo) {
        Usuario usuario = usuarioRepository.findById(idUsuario).orElse(null);
        
        if (usuario != null) {
            Publicacion nuevaPublicacion = usuario.hacerPublicacion(descripcion, anonimo);
            publicacionRepository.save(nuevaPublicacion);
        }
    }

    // Método para eliminar una publicación
    public void eliminarPublicacion(Long idUsuario, Long idPublicacion) {
        Usuario usuario = usuarioRepository.findById(idUsuario).orElse(null);
        Publicacion publicacion = publicacionRepository.findById(idPublicacion).orElse(null);
        
        if (usuario != null && publicacion != null && usuario.eliminarPublicacion(publicacion)) {
            publicacionRepository.delete(publicacion);
        }
    }

    // Método para hacer una referencia en una publicación
    public void hacerReferencia(Long idUsuario, Long idPublicacion, String comentario, String usuarioRef, boolean anonimo) {
        Usuario usuario = usuarioRepository.findById(idUsuario).orElse(null);
        Publicacion publicacion = publicacionRepository.findById(idPublicacion).orElse(null);
        
        if (usuario != null && publicacion != null) {
            usuario.hacerReferenciacion(usuarioRef, publicacion, comentario, anonimo);
            usuarioRepository.save(usuario);
            publicacionRepository.save(publicacion);
        }
    }

}
