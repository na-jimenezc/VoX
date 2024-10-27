package Controlador;

import Modelo.Publicacion;
import Repository.PublicacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/publicaciones")
public class ControllerPublicaciones {

    @Autowired
    private PublicacionRepository publicacionRepository;

    @GetMapping
    public List<Publicacion> getAllPublicaciones() {
        return publicacionRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Publicacion> getPublicacionById(@PathVariable Long id) {
        return publicacionRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Publicacion createPublicacion(@RequestBody Publicacion publicacion) {
        return publicacionRepository.save(publicacion);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Publicacion> updatePublicacion(@PathVariable Long id, @RequestBody Publicacion publicacionDetails) {
        return publicacionRepository.findById(id)
                .map(publicacion -> {
                    publicacion.setDescripcion(publicacionDetails.getDescripcion());
                    publicacion.setAnonimo(publicacionDetails.getAnonimo());
                    // Actualiza otros campos según sea necesario
                    return ResponseEntity.ok(publicacionRepository.save(publicacion));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletePublicacion(@PathVariable Long id) {
        return publicacionRepository.findById(id)
                .map(publicacion -> {
                    publicacionRepository.delete(publicacion);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
