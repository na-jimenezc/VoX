package com.vox.proyecto.controller;

import com.vox.proyecto.modelo.Multimedia;
import com.vox.proyecto.modelo.Publicacion;
import com.vox.proyecto.repository.MultimediaRepository;
import com.vox.proyecto.repository.PublicacionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(ControllerMultimedia.class)
public class ControllerMultimediaTest {

    private MockMvc mockMvc;

    @Mock
    private MultimediaRepository multimediaRepository;

    @Mock
    private PublicacionRepository publicacionRepository;

    @InjectMocks
    private ControllerMultimedia controllerMultimedia;

    private Publicacion publicacion;

    @BeforeEach
    void setUp() {
        publicacion = new Publicacion();
        publicacion.setIdPub(1L);
        mockMvc = MockMvcBuilders.standaloneSetup(controllerMultimedia).build();
    }

    @Test
    void testAgregarMultimedia_Success() throws Exception {
        Multimedia multimedia = new Multimedia("url", "tipo", 1L, publicacion);
        Mockito.when(publicacionRepository.existsById(anyLong())).thenReturn(true);
        Mockito.when(multimediaRepository.save(any(Multimedia.class))).thenReturn(multimedia);

        mockMvc.perform(post("/multimedia/agregar")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"url\":\"url\", \"tipo\":\"tipo\", \"orden\":1, \"idPub\":{\"idPub\":1}}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.url").value("url"))
                .andExpect(jsonPath("$.tipo").value("tipo"));
    }

    @Test
    void testAgregarMultimedia_PublicacionNotFound() throws Exception {
        Mockito.when(publicacionRepository.existsById(anyLong())).thenReturn(false);

        mockMvc.perform(post("/multimedia/agregar")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"url\":\"url\", \"tipo\":\"tipo\", \"orden\":1, \"idPub\":{\"idPub\":1}}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testEliminarMultimedia_Success() throws Exception {
        Mockito.when(multimediaRepository.existsById(anyLong())).thenReturn(true);

        mockMvc.perform(delete("/multimedia/eliminar/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testEliminarMultimedia_NotFound() throws Exception {
        Mockito.when(multimediaRepository.existsById(anyLong())).thenReturn(false);

        mockMvc.perform(delete("/multimedia/eliminar/1"))
                .andExpect(status().isNotFound());
    }
}
