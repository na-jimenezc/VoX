package com.vox.proyecto.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.vox.proyecto.modelo.Publicacion;
import com.vox.proyecto.repository.PublicacionRepository;

import java.util.List;

@RestController
@RequestMapping("/publicaciones")
public class ControllerPublicaciones {

    @Autowired
    private PublicacionRepository publicacionRepository;

    // Crear una nueva publicación
    @PostMapping("/crear")
    public Publicacion hacerPublicacion(@RequestParam String descripcion, @RequestParam Boolean anonimo) {
        Publicacion nuevaPublicacion = new Publicacion(null, descripcion, anonimo);
        return publicacionRepository.save(nuevaPublicacion);
    }

    // Revelar la identidad del autor de una publicación específica
    @PutMapping("/revelarIdentidad")
    public void revelarIdentidadPub(@RequestBody Publicacion publicacion) {
        publicacion.revelarIdentidad();
        publicacionRepository.save(publicacion);
    }

    // Revelar la identidad de todos los autores de las publicaciones
    @PutMapping("/revelarTodasIdentidad")
    public void revelarTodasIdentidadPub() {
        List<Publicacion> publicaciones = publicacionRepository.findAll();
        for (Publicacion publicacion : publicaciones) {
            publicacion.revelarIdentidad();
            publicacionRepository.save(publicacion);
        }
    }

    // Eliminar una publicación específica
    @DeleteMapping("/eliminar")
    public void eliminarPublicacion(@RequestBody Publicacion publicacion) {
        publicacionRepository.delete(publicacion);
    }

    // Verificar si un usuario es el autor de una publicación
    @GetMapping("/verificarPertenencia")
    public Boolean verificarPertenenciaPub(@RequestBody Publicacion publicacion, @RequestParam Long idUsuario) {
        return publicacion.getAutor().getIdUsuario().equals(idUsuario);
    }
}
