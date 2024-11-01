package com.vox.proyecto.controller;

import com.vox.proyecto.modelo.Publicacion;
import com.vox.proyecto.repository.PublicacionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ControllerPublicacionesTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PublicacionRepository publicacionRepository;

    // Test para crear una nueva publicación
    @Test
    public void testHacerPublicacion() throws Exception {
        mockMvc.perform(post("/publicaciones/crear")
                .param("descripcion", "Nueva publicacion de prueba")
                .param("anonimo", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.descripcion", is("Nueva publicacion de prueba")))
                .andExpect(jsonPath("$.anonimo", is(true)));
    }

    // Test para revelar la identidad de una publicación
    @Test
    public void testRevelarIdentidadPub() throws Exception {
        // Crea y guarda una publicación anónima
        Publicacion publicacion = new Publicacion(null, "Descripcion prueba", true);
        publicacion = publicacionRepository.save(publicacion);

        mockMvc.perform(put("/publicaciones/revelarIdentidad")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"idPub\": " + publicacion.getIdPub() + " }"))
                .andExpect(status().isOk());

        // Verifica que la publicación ahora no es anónima
        Publicacion updatedPub = publicacionRepository.findById(publicacion.getIdPub()).orElse(null);
        assert updatedPub != null;
        assert !updatedPub.getAnonimo();
    }

    // Test para revelar la identidad de todas las publicaciones
    @Test
    public void testRevelarTodasIdentidadPub() throws Exception {
        // Crea y guarda varias publicaciones anónimas
        Publicacion pub1 = new Publicacion(null, "Descripcion prueba 1", true);
        Publicacion pub2 = new Publicacion(null, "Descripcion prueba 2", true);
        publicacionRepository.save(pub1);
        publicacionRepository.save(pub2);

        mockMvc.perform(put("/publicaciones/revelarTodasIdentidad"))
                .andExpect(status().isOk());

        // Verifica que todas las publicaciones ya no sean anónimas
        assert !publicacionRepository.findById(pub1.getIdPub()).get().getAnonimo();
        assert !publicacionRepository.findById(pub2.getIdPub()).get().getAnonimo();
    }

    // Test para eliminar una publicación
    @Test
    public void testEliminarPublicacion() throws Exception {
        // Crea y guarda una publicación
        Publicacion publicacion = new Publicacion(null, "Descripcion prueba", true);
        publicacion = publicacionRepository.save(publicacion);

        mockMvc.perform(delete("/publicaciones/eliminar")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"idPub\": " + publicacion.getIdPub() + " }"))
                .andExpect(status().isOk());

        // Verifica que la publicación haya sido eliminada
        assert !publicacionRepository.existsById(publicacion.getIdPub());
    }

    // Test para verificar si un usuario es el autor de una publicación
    @Test
    public void testVerificarPertenenciaPub() throws Exception {
        // Crea y guarda una publicación con un autor específico
        Publicacion publicacion = new Publicacion(null, "Descripcion prueba", true);
        publicacionRepository.save(publicacion);

        mockMvc.perform(get("/publicaciones/verificarPertenencia")
                .contentType(MediaType.APPLICATION_JSON)
                .param("idUsuario", "1")
                .content("{ \"idPub\": " + publicacion.getIdPub() + " }"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }
}
