package com.vox.proyecto.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.vox.proyecto.modelo.Usuario;
import com.vox.proyecto.repository.UsuarioRepository;

public class ControllerUsuariosTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private ControllerUsuarios controllerUsuarios;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegistrarUsuario() {
        // Dado
        String nombre = "Juan";
        String username = "juan123";
        String password = "password/";
        int edad = 20;
        String carrera = "Ingeniería";
        String semestre = "2";
        String biografia = "Biografía de Juan";
        String email = "juan@example.com";

        // Cuando
        Usuario nuevoUsuario = new Usuario(nombre, username, password, edad, carrera, semestre, biografia, email, false);
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(nuevoUsuario);

        // Entonces
        controllerUsuarios.registrarUsuario(nombre, username, password, edad, carrera, semestre, biografia, email);
        verify(usuarioRepository, times(1)).save(any(Usuario.class));  // Verificar que se llama a save exactamente una vez
    }

    @Test
    public void testAutenticarUsuario() {
        // Dado
        String username = "juan123";
        String password = "password/";

        // Cuando
        Usuario usuario = new Usuario("Juan", username, password, 20, "Ingeniería", "2", "Biografía de Juan", "juan@example.com", false);
        when(usuarioRepository.findByUsername(username)).thenReturn(usuario);

        // Entonces
        boolean autenticado = controllerUsuarios.autenticarUsuario(username, password);
        assertTrue(autenticado);  // Verificar que la autenticación fue exitosa
        verify(usuarioRepository, times(1)).findByUsername(username);  // Verificar que findByUsername fue llamado exactamente una vez
    }

    @Test
    public void testAutenticarUsuarioFallo() {
        // Dado
        String username = "juan123";
        String password = "wrongpassword";  // Contraseña incorrecta

        // Cuando
        when(usuarioRepository.findByUsername(username)).thenReturn(null);

        // Entonces
        boolean autenticado = controllerUsuarios.autenticarUsuario(username, password);
        assertFalse(autenticado);  // La autenticación debería fallar
    }

    @Test
    public void testVerificarUsuarioExistente() {
        // Dado
        String username = "juan123";

        // Cuando
        when(usuarioRepository.findByUsername(username)).thenReturn(new Usuario());

        // Entonces
        boolean existe = controllerUsuarios.verificarUsuarioExistente(username);
        assertTrue(existe);  // El usuario debería existir
    }

    @Test
    public void testVerificarUsuarioNoExistente() {
        // Dado
        String username = "juan123";

        // Cuando
        when(usuarioRepository.findByUsername(username)).thenReturn(null);

        // Entonces
        boolean existe = controllerUsuarios.verificarUsuarioExistente(username);
        assertFalse(existe);  // El usuario no debería existir
    }

    @Test
    public void testBorrarUsuario() {
        // Dado
        Long id = 1L;

        // Cuando
        controllerUsuarios.borrarUsuario(id);

        // Entonces
        verify(usuarioRepository, times(1)).deleteById(id);  // Verificar que se llama a deleteById exactamente una vez
    }

    @Test
    public void testEditarUsuario() {
        // Dado
        String username = "juan123";
        String nuevoUsername = "juan456";
        Usuario usuario = new Usuario("Juan", username, "password", 20, "Ingeniería", "2", "Biografía de Juan", "juan@example.com", false);
        when(usuarioRepository.findByUsername(username)).thenReturn(usuario);

        // Cuando
        controllerUsuarios.editarUsuario(username, nuevoUsername);

        // Entonces
        verify(usuarioRepository, times(1)).save(usuario);  // Verificar que se llama a save
        assertEquals(nuevoUsername, usuario.getUsername());  // Verificar que el username fue actualizado
    }
}
