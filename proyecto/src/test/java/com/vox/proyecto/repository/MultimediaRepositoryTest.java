package com.vox.proyecto.repository;

import com.vox.proyecto.modelo.Multimedia;
import com.vox.proyecto.modelo.Publicacion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class MultimediaRepositoryTest {

    @Autowired
    private MultimediaRepository multimediaRepository;

    @Autowired
    private PublicacionRepository publicacionRepository;

    private Publicacion publicacion;

    @BeforeEach
    void setUp() {
        publicacion = new Publicacion();
        publicacion.setIdPub(1L);
        publicacionRepository.save(publicacion);
    }

    @Test
    void testSaveMultimedia() {
        Multimedia multimedia = new Multimedia("url", "tipo", 1L, publicacion);
        Multimedia savedMultimedia = multimediaRepository.save(multimedia);
        
        assertThat(savedMultimedia).isNotNull();
        assertThat(savedMultimedia.getIdMul()).isNotNull();
        assertThat(savedMultimedia.getUrl()).isEqualTo("url");
    }

    @Test
    void testFindById() {
        Multimedia multimedia = new Multimedia("url", "tipo", 1L, publicacion);
        multimedia = multimediaRepository.save(multimedia);
        
        Optional<Multimedia> found = multimediaRepository.findById(multimedia.getIdMul());
        
        assertThat(found).isPresent();
        assertThat(found.get().getUrl()).isEqualTo("url");
    }

    @Test
    void testDeleteById() {
        Multimedia multimedia = new Multimedia("url", "tipo", 1L, publicacion);
        multimedia = multimediaRepository.save(multimedia);
        
        multimediaRepository.deleteById(multimedia.getIdMul());
        
        Optional<Multimedia> found = multimediaRepository.findById(multimedia.getIdMul());
        assertThat(found).isNotPresent();
    }
}
