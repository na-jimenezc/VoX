package com.vox.proyecto.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vox.proyecto.modelo.Publicacion;
import com.vox.proyecto.repository.PublicacionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ControllerPublicaciones.class)
class ControllerPublicacionesTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PublicacionRepository publicacionRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Publicacion publicacion;

    @BeforeEach
    void setUp() {
        publicacion = new Publicacion();
        publicacion.setIdPub(1L);
        publicacion.setDescripcion("Descripción de prueba");
        publicacion.setFecha(new Date());
        publicacion.setAnonimo(false);
    }

    @Test
    void testCrearPublicacion() throws Exception {
        Mockito.when(publicacionRepository.save(any(Publicacion.class))).thenReturn(publicacion);

        mockMvc.perform(post("/publicaciones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(publicacion)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idPub").value(publicacion.getIdPub()))
                .andExpect(jsonPath("$.descripcion").value(publicacion.getDescripcion()));
    }

    @Test
    void testObtenerPublicaciones() throws Exception {
        List<Publicacion> publicaciones = new ArrayList<>();
        publicaciones.add(publicacion);
        Mockito.when(publicacionRepository.findAll()).thenReturn(publicaciones);

        mockMvc.perform(get("/publicaciones"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].descripcion").value(publicacion.getDescripcion()));
    }

    @Test
    void testObtenerPublicacionPorId() throws Exception {
        Mockito.when(publicacionRepository.findById(anyLong())).thenReturn(Optional.of(publicacion));

        mockMvc.perform(get("/publicaciones/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idPub").value(publicacion.getIdPub()))
                .andExpect(jsonPath("$.descripcion").value(publicacion.getDescripcion()));
    }

    @Test
    void testActualizarPublicacion() throws Exception {
        Publicacion publicacionActualizada = new Publicacion();
        publicacionActualizada.setDescripcion("Descripción actualizada");
        publicacionActualizada.setFecha(new Date());
        publicacionActualizada.setAnonimo(true);

        Mockito.when(publicacionRepository.findById(anyLong())).thenReturn(Optional.of(publicacion));
        Mockito.when(publicacionRepository.save(any(Publicacion.class))).thenReturn(publicacionActualizada);

        mockMvc.perform(put("/publicaciones/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(publicacionActualizada)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.descripcion").value(publicacionActualizada.getDescripcion()))
                .andExpect(jsonPath("$.anonimo").value(publicacionActualizada.getAnonimo()));
    }

    @Test
    void testEliminarPublicacion() throws Exception {
        Mockito.doNothing().when(publicacionRepository).deleteById(anyLong());

        mockMvc.perform(delete("/publicaciones/{id}", 1L))
                .andExpect(status().isOk());
    }
}
