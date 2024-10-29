package com.vox.proyecto.controller;

import com.vox.proyecto.modelo.Seguimiento;
import com.vox.proyecto.modelo.Usuario;
import com.vox.proyecto.repository.SeguimientoRepository;
import com.vox.proyecto.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class ControllerSeguimientosTest {

    @Mock
    private SeguimientoRepository seguimientoRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private ControllerSeguimientos controllerSeguimientos;

    private Usuario seguidor;
    private Usuario seguido;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        seguidor = new Usuario();
        seguidor.setIdUsuario(1L);
        seguidor.setUsername("usuarioActual");

        seguido = new Usuario();
        seguido.setIdUsuario(2L);
        seguido.setUsername("usernameSeguido");

        when(usuarioRepository.findByUsername("usuarioActual")).thenReturn(seguidor);
    }

    @Test
    void testSeguirUsuarioExistente() {
        when(usuarioRepository.findByUsername("usernameSeguido")).thenReturn(seguido);

        ResponseEntity<Void> response = controllerSeguimientos.seguir("usernameSeguido");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(seguimientoRepository, times(1)).save(any(Seguimiento.class));
    }

    @Test
    void testSeguirUsuarioNoExistente() {
        when(usuarioRepository.findByUsername("usernameSeguido")).thenReturn(null);

        ResponseEntity<Void> response = controllerSeguimientos.seguir("usernameSeguido");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void testDejarSeguirUsuarioExistente() {
        when(usuarioRepository.findByUsername("usernameSeguido")).thenReturn(seguido);
        Seguimiento seguimiento = new Seguimiento();
        when(seguimientoRepository.findBySeguidorAndSeguido(seguidor.getIdUsuario(), seguido.getIdUsuario())).thenReturn(seguimiento);

        ResponseEntity<Void> response = controllerSeguimientos.dejarSeguir("usernameSeguido");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(seguimientoRepository, times(1)).delete(seguimiento);
    }

    @Test
    void testDejarSeguirUsuarioNoExistente() {
        when(usuarioRepository.findByUsername("usernameSeguido")).thenReturn(null);

        ResponseEntity<Void> response = controllerSeguimientos.dejarSeguir("usernameSeguido");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void testVerSeguidores() {
        List<Seguimiento> seguidores = new ArrayList<>();
        seguidores.add(new Seguimiento());
        when(seguimientoRepository.findBySeguido(seguidor.getIdUsuario())).thenReturn(seguidores);

        List<Seguimiento> result = controllerSeguimientos.verSeguidores();

        assertThat(result).isEqualTo(seguidores);
    }

    @Test
    void testBuscarSeguidorPorUsernameExistente() {
        when(usuarioRepository.findByUsername("usernameSeguido")).thenReturn(seguido);
        Seguimiento seguimiento = new Seguimiento();
        when(seguimientoRepository.findBySeguidorAndSeguido(seguidor.getIdUsuario(), seguido.getIdUsuario())).thenReturn(seguimiento);

        ResponseEntity<Seguimiento> response = controllerSeguimientos.buscarSeguidorUsername("usernameSeguido");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(seguimiento);
    }

    @Test
    void testBuscarSeguidorPorNombreCompletoExistente() {
        List<Seguimiento> seguidores = new ArrayList<>();
        seguidores.add(new Seguimiento());
        when(seguimientoRepository.findByNombreCompleto("nombreCompleto")).thenReturn(seguidores);

        ResponseEntity<List<Seguimiento>> response = controllerSeguimientos.buscarSeguidorNombre("nombreCompleto");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(seguidores);
    }
}
