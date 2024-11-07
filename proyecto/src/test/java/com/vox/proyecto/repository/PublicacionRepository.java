package com.vox.proyecto.repository;

import com.vox.proyecto.modelo.Publicacion;
import com.vox.proyecto.modelo.Usuario;
import com.vox.proyecto.modelo.Like;  // Asegúrate de importar la clase Like
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PublicacionRepositoryTest {

    @Autowired
    private PublicacionRepository publicacionRepository;

    private Publicacion publicacion1;
    private Publicacion publicacion2;
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        // Preparación de datos de prueba
        usuario = new Usuario();
        usuario.setIdUsuario(1L);

        publicacion1 = new Publicacion(1L, "Descripción clave uno", null, false);
        publicacion2 = new Publicacion(2L, "Descripción clave dos", null, false);
    }

    @Test
    void testFindByKeyword() {
        // Guardar publicaciones en la base de datos (simulada en pruebas)
        publicacionRepository.save(publicacion1);
        publicacionRepository.save(publicacion2);

        // Llamar al método y verificar los resultados
        List<Publicacion> publicaciones = publicacionRepository.findByKeyword("clave");

        assertNotNull(publicaciones);
        assertEquals(2, publicaciones.size());
    }

    @Test
    void testFindAllOrderByLikesDesc() {
        // Crear y agregar likes para simular la cantidad de likes
        Like like1 = new Like();  // Aquí asumiendo que el constructor de Like se maneja correctamente
        like1.setUsuario(usuario);  // Configurar el usuario de like
        publicacion1.agregarLike(like1);  // Agregar like a la publicación

        Like like2 = new Like();
        like2.setUsuario(usuario);
        publicacion2.agregarLike(like2);  // Agregar like a la publicación
        publicacion2.agregarLike(like1);  // Agregar un segundo like a la segunda publicación

        // Guardar publicaciones con los likes en la base de datos (simulada en pruebas)
        publicacionRepository.save(publicacion1);
        publicacionRepository.save(publicacion2);

        // Llamar al método y verificar los resultados
        List<Publicacion> publicaciones = publicacionRepository.findAllOrderByLikesDesc();

        assertNotNull(publicaciones);
        // Verifica que la publicación con más likes esté primero
        assertTrue(publicaciones.get(0).contarLikes() >= publicaciones.get(1).contarLikes());
    }

    @Test
    void testFindAllOrderByFechaCreacionDesc() {
        // Guardar publicaciones en la base de datos (simulada en pruebas)
        publicacionRepository.save(publicacion1);
        publicacionRepository.save(publicacion2);

        // Llamar al método y verificar los resultados
        List<Publicacion> publicaciones = publicacionRepository.findAllOrderByFechaCreacionDesc();

        assertNotNull(publicaciones);
        assertTrue(publicaciones.get(0).getFecha().after(publicaciones.get(1).getFecha()));
    }

    @Test
    void testFindByAutorIdUsuario() {
        // Guardar publicaciones en la base de datos (simulada en pruebas)
        publicacion1.setAutor(usuario);
        publicacion2.setAutor(usuario);
        publicacionRepository.save(publicacion1);
        publicacionRepository.save(publicacion2);

        // Llamar al método y verificar los resultados
        List<Publicacion> publicaciones = publicacionRepository.findByAutor_IdUsuario(1L);

        assertNotNull(publicaciones);
        assertEquals(2, publicaciones.size());
    }

    @Test
    void testFindByPublicacionPadre() {
        // Simular la relación padre-hijo
        Publicacion publicacionPadre = new Publicacion(3L, "Padre publicación", null, false);
        publicacionRepository.save(publicacionPadre);

        publicacion1.setPublicacionPadre(publicacionPadre);
        publicacionRepository.save(publicacion1);

        // Llamar al método y verificar los resultados
        List<Publicacion> publicaciones = publicacionRepository.findByPublicacionPadre(publicacionPadre);

        assertNotNull(publicaciones);
        assertEquals(1, publicaciones.size());
    }
}
