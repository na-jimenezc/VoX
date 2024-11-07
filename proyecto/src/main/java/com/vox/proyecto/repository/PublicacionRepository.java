package com.vox.proyecto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vox.proyecto.modelo.Publicacion;

@Repository
public interface PublicacionRepository extends JpaRepository<Publicacion, Long> {

    List<Publicacion> findByKeyword(String keyword);
    // Metodos

    List<Publicacion> findByAutor_IdUsuario(Long idUsuario);

    List<Publicacion> findAllOrderByLikesDesc();

    List<Publicacion> findAllOrderByFechaCreacionDesc();
}
