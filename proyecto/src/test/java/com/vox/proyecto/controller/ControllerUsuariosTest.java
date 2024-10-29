package com.vox.proyecto.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.vox.proyecto.modelo.Usuario;
import com.vox.proyecto.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
        Usuario nuevoUsuario = new Usuario("nombre", "username", "password", 20, "carrera", "semestre", "biografia", "email@example.com");

        when(usuarioRepository.save(any(Usuario.class))).thenReturn(nuevoUsuario);

        controllerUsuarios.registrarUsuario("nombre", "username", "password", 20, "carrera", "semestre", "biografia", "email@example.com");

        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    public void testAutenticarUsuario() {
        Usuario usuario = new Usuario("nombre", "username", "password", 20, "carrera", "semestre", "biografia", "email@example.com");
        when(usuarioRepository.findByUsername("username")).thenReturn(usuario);

        boolean autenticado = controllerUsuarios.autenticarUsuario("username", "password");

        System.out.println("Resultado de autenticación (correcto): " + autenticado); // Imprime el resultado en consola
        assertTrue(autenticado);
    }

    @Test
    public void testAutenticarUsuarioFallo() {
        when(usuarioRepository.findByUsername("username")).thenReturn(null);

        boolean autenticado = controllerUsuarios.autenticarUsuario("username", "wrongpassword");

        System.out.println("Resultado de autenticación (fallido): " + autenticado); // Imprime el resultado en consola
        assertFalse(autenticado);
    }

    @Test
    public void testVerificarUsuarioExistente() {
        when(usuarioRepository.findByUsername("username")).thenReturn(new Usuario());

        boolean existe = controllerUsuarios.verificarUsuarioExistente("username");

        assertTrue(existe);
    }

    @Test
    public void testVerificarUsuarioNoExistente() {
        when(usuarioRepository.findByUsername("username")).thenReturn(null);

        boolean existe = controllerUsuarios.verificarUsuarioExistente("username");

        assertFalse(existe);
    }

    @Test
    public void testBorrarUsuario() {
        Long id = 1L;

        controllerUsuarios.borrarUsuario(id);

        verify(usuarioRepository, times(1)).deleteById(id);
    }

    @Test
    public void testEditarUsuario() {
        Usuario usuario = new Usuario("nombre", "username", "password", 20, "carrera", "semestre", "biografia", "email@example.com");
        when(usuarioRepository.findByUsername("username")).thenReturn(usuario);

        controllerUsuarios.editarUsuario("username", "nuevoUsername");

        verify(usuarioRepository, times(1)).save(usuario);
        assertEquals("nuevoUsername", usuario.getUsername());
    }
}
