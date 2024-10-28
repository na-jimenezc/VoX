package com.vox.proyecto.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.vox.proyecto.modelo.Usuario;
import com.vox.proyecto.repository.UsuarioRepository;

@Controller
public class ControllerUsuarios {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Método para registrar un nuevo usuario
    public void registrarUsuario(String nombre, String username, String password, 
    int edad, String carrera, String semestre, String biografia, String email) {
        Usuario nuevoUsuario = new Usuario(nombre, username, password, edad, carrera, semestre, biografia, email);
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
}
