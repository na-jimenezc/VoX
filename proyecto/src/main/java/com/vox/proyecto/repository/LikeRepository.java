package com.vox.proyecto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vox.proyecto.modelo.Like;
import com.vox.proyecto.modelo.Publicacion;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

    // Busca un "Like" por la publicación y el ID del usuario
    Like findByPublicacionAndIdUser(Publicacion publicacion, Long idUser);

    // Contar "likes" por ID de publicación
    long countByPublicacionId(Long publicacionId);
}
