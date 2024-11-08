package com.vox.proyecto.repository;

import com.vox.proyecto.modelo.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioRepositoryTest {

    @InjectMocks
    private UsuarioRepository usuarioRepository;

    @Mock
    private Usuario mockUsuario;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); 
    }

    @Test
    void testFindByUsername() {
        // Dado
        String username = "juan123";
        Usuario usuario = new Usuario("Juan", username, "password", 20, "Ingeniería", "2", "Biografía de Juan", "juan@example.com", false);

        // Cuando
        when(usuarioRepository.findByUsername(username)).thenReturn(usuario);

        // Entonces
        Usuario result = usuarioRepository.findByUsername(username);
        assertNotNull(result); // Verifica que no sea nulo
        assertEquals(username, result.getUsername()); // Verifica que el username sea el esperado
        verify(usuarioRepository, times(1)).findByUsername(username); // Verifica que findByUsername se haya llamado una vez
    }

    @Test
    void testFindByUsernameNoUsuario() {
        // Dado
        String username = "juan123";

        // Cuando
        when(usuarioRepository.findByUsername(username)).thenReturn(null);

        // Entonces
        Usuario result = usuarioRepository.findByUsername(username);
        assertNull(result); // Verifica que el resultado sea nulo
        verify(usuarioRepository, times(1)).findByUsername(username); // Verifica que findByUsername se haya llamado una vez
    }

    @Test
    void testSaveUsuario() {
        // Dado
        Usuario usuario = new Usuario("Juan", "juan123", "password", 20, "Ingeniería", "2", "Biografía de Juan", "juan@example.com", false);

        // Cuando
        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        // Entonces
        Usuario result = usuarioRepository.save(usuario);
        assertNotNull(result); // Verifica que el resultado no sea nulo
        assertEquals(usuario.getUsername(), result.getUsername()); // Verifica que el username sea el esperado
        verify(usuarioRepository, times(1)).save(usuario); // Verifica que save se haya llamado una vez
    }

    @Test
    void testDeleteById() {
        // Dado
        Long id = 1L;

        // Cuando
        usuarioRepository.deleteById(id);

        // Entonces
        verify(usuarioRepository, times(1)).deleteById(id); // Verifica que deleteById se haya llamado exactamente una vez
    }

    @Test
    void testUpdateUsuario() {
        // Dado
        String username = "juan123";
        String nuevoUsername = "juan456";
        Usuario usuario = new Usuario("Juan", username, "password", 20, "Ingeniería", "2", "Biografía de Juan", "juan@example.com", false);
        when(usuarioRepository.findByUsername(username)).thenReturn(usuario);

        // Cuando
        usuario.setUsername(nuevoUsername); // Cambiar el nombre de usuario
        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        // Entonces
        Usuario result = usuarioRepository.save(usuario);
        assertEquals(nuevoUsername, result.getUsername()); // Verifica que el username haya sido actualizado
        verify(usuarioRepository, times(1)).save(usuario); // Verifica que save haya sido llamado una vez
    }
}
