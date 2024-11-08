package com.vox.proyecto.controller;

import com.vox.proyecto.modelo.Publicacion;
import com.vox.proyecto.repository.PublicacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/publicaciones")
public class ControllerPublicaciones {

    @Autowired
    private PublicacionRepository publicacionRepository;

    // Crear una nueva publicación
    @PostMapping
    public Publicacion crearPublicacion(@RequestBody Publicacion publicacion) {
        return publicacionRepository.save(publicacion);
    }

    // Obtener todas las publicaciones
    @GetMapping
    public List<Publicacion> obtenerPublicaciones() {
        return publicacionRepository.findAll();
    }

    // Obtener una publicación por ID
    @GetMapping("/{id}")
    public Publicacion obtenerPublicacionPorId(@PathVariable Long id) {
        return publicacionRepository.findById(id).orElse(null);
    }

    // Actualizar una publicación por ID
    @PutMapping("/{id}")
    public Publicacion actualizarPublicacion(@PathVariable Long id, @RequestBody Publicacion publicacionActualizada) {
        Optional<Publicacion> optionalPublicacion = publicacionRepository.findById(id);

        if (optionalPublicacion.isPresent()) {
            Publicacion publicacion = optionalPublicacion.get();
            publicacion.setDescripcion(publicacionActualizada.getDescripcion());
            publicacion.setFecha(publicacionActualizada.getFecha());
            publicacion.setAnonimo(publicacionActualizada.getAnonimo());
            publicacion.setAutor(publicacionActualizada.getAutor());
            publicacion.setComentarios(publicacionActualizada.getComentarios());
            publicacion.setLikes(publicacionActualizada.getLikes());
            publicacion.setMultimedia(publicacionActualizada.getMultimedia());
            publicacion.setReferencias(publicacionActualizada.getReferencias());

            return publicacionRepository.save(publicacion);
        } else {
            throw new RuntimeException("Publicación no encontrada con ID: " + id);
        }
    }

    // Eliminar una publicación por ID
    @DeleteMapping("/{id}")
    public void eliminarPublicacion(@PathVariable Long id) {
        publicacionRepository.deleteById(id);
    }
}
