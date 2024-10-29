package repository;

import com.vox.proyecto.controller.ControllerUsuarios;
import com.vox.proyecto.modelo.Usuario;
import com.vox.proyecto.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class ControllerUsuariosTest {

    @InjectMocks
    private ControllerUsuarios controllerUsuarios;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private Usuario mockUsuario;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegistrarUsuario() {
        String nombre = "Juan";
        String username = "juan123";
        String password = "password";
        int edad = 20;
        String carrera = "Ingeniería";
        String semestre = "2";
        String biografia = "Biografía de Juan";
        String email = "juan@example.com";

        controllerUsuarios.registrarUsuario(nombre, username, password, edad, carrera, semestre, biografia, email);

        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    void testAutenticarUsuario() {
        String username = "juan123";
        String password = "password";

        when(usuarioRepository.findByUsername(username)).thenReturn(mockUsuario);
        when(mockUsuario.getPassword()).thenReturn(password);

        boolean result = controllerUsuarios.autenticarUsuario(username, password);

        assertTrue(result);
        verify(usuarioRepository, times(1)).findByUsername(username);
    }

    @Test
    void testBorrarUsuario() {
        Long id = 1L;

        controllerUsuarios.borrarUsuario(id);

        verify(usuarioRepository, times(1)).deleteById(id);
    }
}
