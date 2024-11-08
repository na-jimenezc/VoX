package com.vox.proyecto.repository;

import com.vox.proyecto.modelo.Publicacion;
import com.vox.proyecto.modelo.Usuario;
import com.vox.proyecto.modelo.Like;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
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

        publicacion1 = new Publicacion();
        publicacion1.setDescripcion("Descripción clave uno");
        publicacion1.setFecha(new Date());
        publicacion1.setAnonimo(false);
        publicacion1.setAutor(usuario);

        publicacion2 = new Publicacion();
        publicacion2.setDescripcion("Descripción clave dos");
        publicacion2.setFecha(new Date());
        publicacion2.setAnonimo(false);
        publicacion2.setAutor(usuario);
    }

    @Test
    void testSearchByKeywordInDescripcion() {
        // Guardar publicaciones en la base de datos
        publicacionRepository.save(publicacion1);
        publicacionRepository.save(publicacion2);

        // Llamar al método de búsqueda
        List<Publicacion> publicaciones = publicacionRepository.searchByKeywordInDescripcion("clave");

        assertNotNull(publicaciones);
        assertEquals(2, publicaciones.size());
    }

    @Test
    void testFindAllOrderByLikesCountDesc() {
        // Crear y agregar likes para simular la cantidad de likes
        Like like1 = new Like();
        like1.setUsuario(usuario);
        publicacion1.getLikes().add(like1);

        Like like2 = new Like();
        like2.setUsuario(usuario);
        publicacion2.getLikes().add(like2);
        publicacion2.getLikes().add(like1);

        // Guardar publicaciones con likes
        publicacionRepository.save(publicacion1);
        publicacionRepository.save(publicacion2);

        // Llamar al método y verificar los resultados
        List<Publicacion> publicaciones = publicacionRepository.findAllOrderByLikesCountDesc();

        assertNotNull(publicaciones);
        assertTrue(publicaciones.get(0).getLikes().size() >= publicaciones.get(1).getLikes().size());
    }

    @Test
    void testFindAllOrderByFechaDesc() {
        // Guardar publicaciones con diferentes fechas
        publicacion1.setFecha(new Date(System.currentTimeMillis() - 1000 * 60 * 60));
        publicacion2.setFecha(new Date());
        publicacionRepository.save(publicacion1);
        publicacionRepository.save(publicacion2);

        // Llamar al método y verificar los resultados
        List<Publicacion> publicaciones = publicacionRepository.findAllOrderByFechaDesc();

        assertNotNull(publicaciones);
        assertTrue(publicaciones.get(0).getFecha().after(publicaciones.get(1).getFecha()));
    }

    @Test
    void testFindByAutor_Id() {
        // Guardar publicaciones con el mismo autor
        publicacion1.setAutor(usuario);
        publicacion2.setAutor(usuario);
        publicacionRepository.save(publicacion1);
        publicacionRepository.save(publicacion2);

        // Llamar al método y verificar los resultados
        List<Publicacion> publicaciones = publicacionRepository.findByAutor_Id(usuario.getIdUsuario());

        assertNotNull(publicaciones);
        assertEquals(2, publicaciones.size());
    }

}
