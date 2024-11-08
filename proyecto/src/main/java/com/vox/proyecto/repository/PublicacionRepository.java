package com.vox.proyecto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vox.proyecto.modelo.Publicacion;

import java.util.List;

@Repository
public interface PublicacionRepository extends JpaRepository<Publicacion, Long> {

    // Obtener todas las publicaciones ordenadas por fecha de creación en orden descendente
    @Query("SELECT p FROM Publicacion p ORDER BY p.fecha DESC")
    List<Publicacion> findAllOrderByFechaDesc();

    // Obtener todas las publicaciones ordenadas por la cantidad de likes en orden descendente
    @Query("SELECT p FROM Publicacion p LEFT JOIN p.likes likes GROUP BY p ORDER BY COUNT(likes) DESC")
    List<Publicacion> findAllOrderByLikesCountDesc();
}
