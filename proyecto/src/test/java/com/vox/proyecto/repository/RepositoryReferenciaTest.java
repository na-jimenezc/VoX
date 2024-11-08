package com.vox.proyecto.repository;

import com.vox.proyecto.modelo.Publicacion;
import com.vox.proyecto.modelo.Usuario;
import com.vox.proyecto.modelo.Referencia;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class RepositoryReferenciaTest {

    @Autowired
    private ReferenciaRepository referenciaRepository;

    private Publicacion publicacion;
    private Usuario autor;
    private Referencia referencia;

    @BeforeEach
    public void setUp() {
        autor = new Usuario();  
        publicacion = new Publicacion(autor, "Descripción de prueba", false);  // Crear publicación con autor
        
        // Crear la referencia directamente sin setters
        referencia = new Referencia(autor, publicacion, "Comentario de referencia", "UsuarioReferencia", false);
        
        // Guardar la publicación y la referencia en la base de datos para las pruebas
        referenciaRepository.save(referencia);
    }

    @Test
    public void testGuardarReferencia() {
        Referencia encontrado = referenciaRepository.findById(referencia.getIdRef()).orElse(null);
        assertNotNull(encontrado);  // Asegura que la referencia fue guardada correctamente
        assertEquals(referencia.getComentario(), encontrado.getComentario());
    }

    @Test
    public void testEliminarReferencia() {
        // Eliminar la referencia
        referenciaRepository.delete(referencia);
        
        Referencia encontrado = referenciaRepository.findById(referencia.getIdRef()).orElse(null);
        assertEquals(null, encontrado);  // Asegura que la referencia ha sido eliminada
    }

    @Test
    public void testActualizarReferencia() {
        referencia.setAnonimoRef(true);
        referenciaRepository.save(referencia);
        
        Referencia actualizado = referenciaRepository.findById(referencia.getIdRef()).orElse(null);
        assertNotNull(actualizado);
        assertEquals(true, actualizado.getAnonimoRef());  // Verifica si el anonimato se actualizó correctamente
    }
}
