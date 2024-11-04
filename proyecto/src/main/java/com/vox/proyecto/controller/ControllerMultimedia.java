package com.vox.proyecto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.vox.proyecto.modelo.Multimedia;
//import com.vox.proyecto.modelo.Publicacion;
import com.vox.proyecto.repository.MultimediaRepository;
import com.vox.proyecto.repository.PublicacionRepository;

import java.util.List;

@RestController
@RequestMapping("/multimedia")
public class ControllerMultimedia {

    @Autowired
    private MultimediaRepository multimediaRepository;

    @Autowired
    private PublicacionRepository publicacionRepository;

    // Método para crear una nueva entrada de multimedia
    @PostMapping("/agregar")
    public ResponseEntity<Multimedia> agregarMultimedia(@RequestBody Multimedia multimedia) {
        // Verifica si la publicación existe
        if (!publicacionRepository.existsById(multimedia.getIdPub().getIdPub())) {
            return ResponseEntity.notFound().build();
        }
        
        // Guarda la nueva multimedia y retorna la respuesta
        return ResponseEntity.ok(multimediaRepository.save(multimedia));
    }

    // Método para obtener todas las multimedia de una publicación
    @GetMapping("/listar")
    public ResponseEntity<List<Multimedia>> listarMultimediaPorPublicacion(@RequestParam Long publicacionId) {
        List<Multimedia> multimediaList = multimediaRepository.findAll(); // Ajusta esto si necesitas filtrar por publicación
        return ResponseEntity.ok(multimediaList);
    }

    // Método para eliminar una entrada de multimedia
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarMultimedia(@PathVariable Long id) {
        if (multimediaRepository.existsById(id)) {
            multimediaRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
