package Controlador;

import Modelo.Like;
import Modelo.Publicacion;
import Repository.LikeRepository;
import Repository.PublicacionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/likes")
public class ControllerLikes {

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private PublicacionRepository publicacionRepository;

    // Método para dar un "Like" a una publicación
    @PostMapping("/darLike")
    public ResponseEntity<Like> darLike(@RequestBody Publicacion publicacion, @RequestParam Long idUser) {
        // Verifica si la publicación existe
        if (!publicacionRepository.existsById(publicacion.getIdPub())) {
            return ResponseEntity.notFound().build();
        }

        // Crea un nuevo Like
        Like nuevoLike = new Like();
        nuevoLike.setPublicacion(publicacion);
        nuevoLike.setIdUser(idUser);

        // Guarda el like y retorna la respuesta
        return ResponseEntity.ok(likeRepository.save(nuevoLike));
    }

    // Método para quitar un "Like" de una publicación
    @DeleteMapping("/quitarLike")
    public ResponseEntity<Void> quitarLike(@RequestBody Publicacion publicacion, @RequestParam Long idUser) {
        // Busca el Like asociado
        Like likeToRemove = likeRepository.findByPublicacionAndIdUser(publicacion, idUser);
        if (likeToRemove != null) {
            likeRepository.delete(likeToRemove);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Método para contar los "likes" de una publicación
    @GetMapping("/contarLikes")
    public ResponseEntity<Long> contarLikes(@RequestParam Long publicacionId) {
        long count = likeRepository.countByPublicacionId(publicacionId);
        return ResponseEntity.ok(count);
    }
}
