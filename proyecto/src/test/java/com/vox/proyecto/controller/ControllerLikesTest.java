package com.vox.proyecto.controller;

import com.vox.proyecto.modelo.Like;
import com.vox.proyecto.modelo.Publicacion;
import com.vox.proyecto.repository.LikeRepository;
import com.vox.proyecto.repository.PublicacionRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@WebMvcTest(ControllerLikes.class)
public class ControllerLikesTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LikeRepository likeRepository;

    @MockBean
    private PublicacionRepository publicacionRepository;

    // Test para el método darLike
    @Test
    public void testDarLike() throws Exception {
        Publicacion publicacion = new Publicacion(null, null, null);
        publicacion.setIdPub(1L);

        Like nuevoLike = new Like();
        // nuevoLike.setIdUser(1L);
        nuevoLike.setPublicacion(publicacion);

        Mockito.when(publicacionRepository.existsById(1L)).thenReturn(true);
        Mockito.when(likeRepository.save(any(Like.class))).thenReturn(nuevoLike);

        mockMvc.perform(post("/likes/darLike")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"idPub\": 1}")
                .param("idUser", "1"))
                .andExpect(status().isOk());
    }

    // Test para el método quitarLike
    @Test
    public void testQuitarLike() throws Exception {
        Publicacion publicacion = new Publicacion(null, null, null);
        publicacion.setIdPub(1L);

        Like likeToRemove = new Like();
        // likeToRemove.setIdUser(1L);
        likeToRemove.setPublicacion(publicacion);

        Mockito.when(likeRepository.findByPublicacionAndIdUser(any(Publicacion.class), eq(1L))).thenReturn(likeToRemove);

        mockMvc.perform(delete("/likes/quitarLike")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"idPub\": 1}")
                .param("idUser", "1"))
                .andExpect(status().isOk());
    }

    // Test para el método contarLikes
    @Test
    public void testContarLikes() throws Exception {
        Mockito.when(likeRepository.countByPublicacionId(1L)).thenReturn(5L);

        mockMvc.perform(get("/likes/contarLikes")
                .param("publicacionId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string("5"));
    }
}
