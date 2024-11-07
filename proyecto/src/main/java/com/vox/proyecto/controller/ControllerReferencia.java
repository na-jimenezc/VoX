package com.vox.proyecto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.vox.proyecto.modelo.Referencia;
import com.vox.proyecto.modelo.Publicacion;
import com.vox.proyecto.modelo.Usuario;
import com.vox.proyecto.repository.ReferenciaRepository;
import com.vox.proyecto.repository.PublicacionRepository;
import com.vox.proyecto.repository.UsuarioRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/referencias")
public class ControllerReferencia {

    @Autowired
    private ReferenciaRepository referenciaRepository;

    @Autowired
    private PublicacionRepository publicacionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Crear una nueva referencia
    @PostMapping("/agregar")
    public ResponseEntity<Referencia> agregarReferencia(
            @RequestParam Long idPublicacion,
            @RequestParam Long idUsuario,
            @RequestParam Boolean anonimo,
            @RequestParam String username) {

        Optional<Publicacion> publicacionOpt = publicacionRepository.findById(idPublicacion);
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(idUsuario);

        if (publicacionOpt.isPresent() && usuarioOpt.isPresent()) {
            Referencia nuevaReferencia = new Referencia();
            nuevaReferencia.setUsuario(usuarioOpt.get());
            return ResponseEntity.ok(referenciaRepository.save(nuevaReferencia));
        }
        return ResponseEntity.notFound().build();
    }

    // Obtener todas las referencias de una publicación
    @GetMapping("/listar")
    public ResponseEntity<List<Referencia>> listarReferencias(@RequestParam Long idPublicacion) {
        Optional<Publicacion> publicacionOpt = publicacionRepository.findById(idPublicacion);
        if (publicacionOpt.isPresent()) {
            List<Referencia> referencias = referenciaRepository.findByPublicacion(publicacionOpt.get());
            return ResponseEntity.ok(referencias);
        }
        return ResponseEntity.notFound().build();
    }

    // Actualizar el estado de anonimato de una referencia
    @PutMapping("/actualizarAnonimato")
    public ResponseEntity<Referencia> actualizarAnonimato(
            @RequestParam Long idReferencia,
            @RequestParam Boolean anonimo) {

        Optional<Referencia> referenciaOpt = referenciaRepository.findById(idReferencia);
        if (referenciaOpt.isPresent()) {
            Referencia referencia = referenciaOpt.get();
            referencia.setAnonimoRef(anonimo);
            return ResponseEntity.ok(referenciaRepository.save(referencia));
        }
        return ResponseEntity.notFound().build();
    }

    // Eliminar una referencia
    @DeleteMapping("/eliminar")
    public ResponseEntity<Void> eliminarReferencia(@RequestParam Long idReferencia) {
        if (referenciaRepository.existsById(idReferencia)) {
            referenciaRepository.deleteById(idReferencia);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Contar referencias anónimas de una publicación
    @GetMapping("/contarAnonimas")
    public ResponseEntity<Long> contarAnonimas(@RequestParam Long idPublicacion) {
        Optional<Publicacion> publicacionOpt = publicacionRepository.findById(idPublicacion);
        if (publicacionOpt.isPresent()) {
            long count = referenciaRepository.countByPublicacionAndAnonimoRef(publicacionOpt.get(), true);
            return ResponseEntity.ok(count);
        }
        return ResponseEntity.notFound().build();
    }
}
