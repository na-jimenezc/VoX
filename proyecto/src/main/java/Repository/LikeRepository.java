package Repository;

import Modelo.Like;
import Modelo.Publicacion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

    Like findByPublicacionAndUsername(Publicacion publicacion, String username);
    // Metodos

    long countByPublicacionId(Long publicacionId);
}

