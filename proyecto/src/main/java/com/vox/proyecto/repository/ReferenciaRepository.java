package com.vox.proyecto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vox.proyecto.modelo.Referencia;
import com.vox.proyecto.modelo.Publicacion;

import java.util.List;

@Repository
public interface ReferenciaRepository extends JpaRepository<Referencia, Long> {

    // Busca todas las referencias de una publicación específica
    List<Referencia> findByPublicacion(Publicacion publicacion);

    // Cuenta el número de referencias anónimas de una publicación
    long countByPublicacionAndAnonimoRef(Publicacion publicacion, Boolean anonimoRef);
}
