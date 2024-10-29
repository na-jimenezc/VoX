package repository;

import com.vox.proyecto.modelo.Like;
import com.vox.proyecto.modelo.Publicacion;
import com.vox.proyecto.repository.LikeRepository;
import com.vox.proyecto.repository.PublicacionRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class LikeRepositoryTest {

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private PublicacionRepository publicacionRepository;

    // Prueba para el método findByPublicacionAndIdUser
    @Test
    public void testFindByPublicacionAndIdUser() {
        Publicacion publicacion = new Publicacion(null, null, null);
        publicacion = publicacionRepository.save(publicacion);

        Like like = new Like();
        like.setIdUser(1L);
        like.setPublicacion(publicacion);
        likeRepository.save(like);

        // Verifica que se encuentra el like
        Like foundLike = likeRepository.findByPublicacionAndIdUser(publicacion, 1L);
        assertNotNull(foundLike);
        assertEquals(1L, foundLike.getIdUser());
    }

    // Prueba para el método countByPublicacionId
    @Test
    public void testCountByPublicacionId() {
        Publicacion publicacion = new Publicacion(null, null, null);
        publicacion = publicacionRepository.save(publicacion);

        Like like1 = new Like();
        like1.setIdUser(1L);
        like1.setPublicacion(publicacion);
        likeRepository.save(like1);

        Like like2 = new Like();
        like2.setIdUser(2L);
        like2.setPublicacion(publicacion);
        likeRepository.save(like2);

        // Verifica que el conteo de likes es correcto
        long count = likeRepository.countByPublicacionId(publicacion.getIdPub());
        assertEquals(2, count);
    }
}
