package com.vox.proyecto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vox.proyecto.modelo.Multimedia;

@Repository
public interface MultimediaRepository extends JpaRepository<Multimedia, Long> {
}
