package com.vox.proyecto.controller;

import com.vox.proyecto.modelo.Referencia;
import com.vox.proyecto.modelo.Publicacion;
import com.vox.proyecto.modelo.Usuario;
import com.vox.proyecto.repository.ReferenciaRepository;
import com.vox.proyecto.repository.PublicacionRepository;
import com.vox.proyecto.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ControllerReferencia.class)
public class ControllerReferenciaTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ReferenciaRepository referenciaRepository;

    @Mock
    private PublicacionRepository publicacionRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private ControllerReferencia controllerReferencia;

    private Publicacion publicacion;
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        publicacion = new Publicacion();
        publicacion.setIdPublicacion(1L);

        usuario = new Usuario();
        usuario.setIdUsuario(1L);
    }

    @Test
    void testAgregarReferencia_Success() throws Exception {
        Referencia referencia = new Referencia(usuario, publicacion, "Comentario", "UsuarioReferenciado", false);
        Mockito.when(publicacionRepository.findById(anyLong())).thenReturn(Optional.of(publicacion));
        Mockito.when(usuarioRepository.findById(anyLong())).thenReturn(Optional.of(usuario));
        Mockito.when(referenciaRepository.save(any(Referencia.class))).thenReturn(referencia);

        mockMvc.perform(post("/referencias/agregar")
                .param("idPublicacion", "1")
                .param("idUsuario", "1")
                .param("anonimo", "false")
                .param("username", "UsuarioReferenciado")
                .param("comentario", "Comentario")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("UsuarioReferenciado"))
                .andExpect(jsonPath("$.comentario").value("Comentario"));
    }

    @Test
    void testAgregarReferencia_PublicacionNotFound() throws Exception {
        Mockito.when(publicacionRepository.findById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(post("/referencias/agregar")
                .param("idPublicacion", "1")
                .param("idUsuario", "1")
                .param("anonimo", "false")
                .param("username", "UsuarioReferenciado")
                .param("comentario", "Comentario")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testActualizarAnonimato_Success() throws Exception {
        Referencia referencia = new Referencia(usuario, publicacion, "Comentario", "UsuarioReferenciado", false);
        referencia.setIdRef(1L);

        Mockito.when(referenciaRepository.findById(anyLong())).thenReturn(Optional.of(referencia));
        Mockito.when(referenciaRepository.save(any(Referencia.class))).thenReturn(referencia);

        mockMvc.perform(put("/referencias/actualizarAnonimato")
                .param("idReferencia", "1")
                .param("anonimo", "true")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.anonimoRef").value(true));
    }

    @Test
    void testEliminarReferencia_Success() throws Exception {
        Mockito.when(referenciaRepository.existsById(anyLong())).thenReturn(true);

        mockMvc.perform(delete("/referencias/eliminar")
                .param("idReferencia", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testEliminarReferencia_NotFound() throws Exception {
        Mockito.when(referenciaRepository.existsById(anyLong())).thenReturn(false);

        mockMvc.perform(delete("/referencias/eliminar")
                .param("idReferencia", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
