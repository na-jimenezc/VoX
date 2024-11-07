package com.vox.proyecto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vox.proyecto.modelo.Publicacion;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface PublicacionRepository extends JpaRepository<Publicacion, Long> {

    //Buscar por palabras clave/
    @Query("SELECT p FROM Publicacion p WHERE LOWER(p.descripcion) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Publicacion> findByKeyword(@Param("keyword") String keyword);


    //Buscar por viralidad (por likes de mayor a menor/
    @Query("SELECT p FROM Publicacion p ORDER BY p.likes DESC")
    List<Publicacion> findAllOrderByLikesDesc();

    //Por fecha de creación más reciente a la antigua/
    @Query("SELECT p FROM Publicacion p ORDER BY p.fechaCreacion DESC")
    List<Publicacion> findAllOrderByFechaCreacionDesc();

    //Encuentra todas las publicaciones del autor/
    List<Publicacion> findByAutor_IdUsuario(Long idUsuario);

    //Encuentra todos los comentarios de una publicación específica/
    List<Publicacion> findByPublicacionPadre(Publicacion publicacionPadre);
}