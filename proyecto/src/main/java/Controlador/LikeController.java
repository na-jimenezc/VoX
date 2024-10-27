package Controlador;

import Modelo.Like;
import Modelo.Publicacion;
import Repository.LikeRepository;
import Repository.PublicacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/likes")
public class LikeController {

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private PublicacionRepository publicacionRepository;

    @GetMapping
    public List<Like> getAllLikes() {
        return likeRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Like> getLikeById(@PathVariable Long id) {
        return likeRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Like createLike(@RequestBody Like like) {
        return likeRepository.save(like);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Like> updateLike(@PathVariable Long id, @RequestBody Like likeDetails) {
        return likeRepository.findById(id)
                .map(like -> {
                    // Actualiza los campos necesarios
                    return ResponseEntity.ok(likeRepository.save(like));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteLike(@PathVariable Long id) {
        return likeRepository.findById(id)
                .map(like -> {
                    likeRepository.delete(like);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Métodos personalizados
    @PostMapping("/darLike")
    public ResponseEntity<Like> darLike(@RequestBody Publicacion publicacion, @RequestParam String username) {
        // Verifica si la publicación existe
        if (!publicacionRepository.existsById(publicacion.getId())) {
            return ResponseEntity.notFound().build();
        }

        // Crea un nuevo Like
        Like newLike = new Like();
        newLike.setPublicacion(publicacion);
        newLike.setUsername(username);

        // Guarda el like y retorna la respuesta
        return ResponseEntity.ok(likeRepository.save(newLike));
    }

    @DeleteMapping("/quitarLike")
    public ResponseEntity<Void> quitarLike(@RequestBody Publicacion publicacion, @RequestParam String username) {
        // Busca el Like asociado
        Like likeToRemove = likeRepository.findByPublicacionAndUsername(publicacion, username);
        if (likeToRemove != null) {
            likeRepository.delete(likeToRemove);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/contarLikes/{publicacionId}")
    public long contarLikes(@PathVariable Long publicacionId) {
        return likeRepository.countByPublicacionId(publicacionId);
    }
}
