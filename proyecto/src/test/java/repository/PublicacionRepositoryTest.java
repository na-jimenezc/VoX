package repository;

import com.vox.proyecto.modelo.Publicacion;
import com.vox.proyecto.repository.PublicacionRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class PublicacionRepositoryTest {

    @Autowired
    private PublicacionRepository publicacionRepository;

    // Publicación de prueba
    private Publicacion publicacion;

    @BeforeEach
    public void setUp() {
        publicacion = new Publicacion(null, "Descripcion inicial", true);
        publicacionRepository.save(publicacion);
    }

    // Test para guardar y encontrar una publicación por ID
    @Test
    public void testGuardarYBuscarPublicacionPorId() {
        Publicacion encontrada = publicacionRepository.findById(publicacion.getIdPub()).orElse(null);
        assertThat(encontrada).isNotNull();
        assertThat(encontrada.getDescripcion()).isEqualTo("Descripcion inicial");
    }

    // Test para encontrar todas las publicaciones
    @Test
    public void testBuscarTodasPublicaciones() {
        List<Publicacion> publicaciones = publicacionRepository.findAll();
        assertThat(publicaciones).isNotEmpty();
    }

    // Test para actualizar el anonimato de una publicación
    @Test
    public void testActualizarAnonimatoPublicacion() {
        publicacion.cambiarAnonimato(false);
        publicacionRepository.save(publicacion);
        
        Publicacion actualizada = publicacionRepository.findById(publicacion.getIdPub()).orElse(null);
        assertThat(actualizada).isNotNull();
        assertThat(actualizada.getAnonimo()).isFalse();
    }

    // Test para eliminar una publicación
    @Test
    public void testEliminarPublicacion() {
        publicacionRepository.delete(publicacion);
        boolean existe = publicacionRepository.existsById(publicacion.getIdPub());
        assertThat(existe).isFalse();
    }
}
