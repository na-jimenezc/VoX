package com.vox.proyecto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.vox.proyecto.modelo.Seguimiento;
import com.vox.proyecto.modelo.Usuario;
import com.vox.proyecto.repository.SeguimientoRepository;
import com.vox.proyecto.repository.UsuarioRepository;

import java.util.List;

@RestController
@RequestMapping("/seguimientos")
public class ControllerSeguimientos {

    @Autowired
    private SeguimientoRepository seguimientoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository; 

    // Seguir a un usuario
    @PostMapping("/seguir/{username}")
    public ResponseEntity<Void> seguir(String username) {
        // Obtener el usuario actual
        Usuario seguidor = usuarioRepository.findByUsername("usuarioActual"); 
        Usuario seguido = usuarioRepository.findByUsername(username); // Busca el usuario a seguir
        
        if (seguido != null && seguidor != null) {
            Seguimiento nuevoSeguimiento = new Seguimiento();
            nuevoSeguimiento.setSeguidor(seguidor);
            nuevoSeguimiento.setSeguido(seguido);
            seguimientoRepository.save(nuevoSeguimiento);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Dejar de seguir a un usuario
    @DeleteMapping("/dejarSeguir/{username}")
    public ResponseEntity<Void> dejarSeguir(@PathVariable String username) {
        Usuario seguidor = usuarioRepository.findByUsername("usuarioActual");
        Usuario seguido = usuarioRepository.findByUsername(username);
        
        if (seguido != null && seguidor != null) {
            Seguimiento seguimiento = seguimientoRepository.findBySeguidorAndSeguido(seguidor.getIdUsuario(), seguido.getIdUsuario());
            if (seguimiento != null) {
                seguimientoRepository.delete(seguimiento);
                return ResponseEntity.ok().build();
            }
        }
        return ResponseEntity.notFound().build();
    }

    // Ver seguidores del usuario actual
    @GetMapping("/seguidores")
    public List<Seguimiento> verSeguidores() {
        Usuario usuarioActual = usuarioRepository.findByUsername("usuarioActual"); 
        return seguimientoRepository.findBySeguido(usuarioActual.getIdUsuario());
    }

    // Buscar seguidor por nombre de usuario
    @GetMapping("/buscarSeguidorUsername/{username}")
    public ResponseEntity<Seguimiento> buscarSeguidorUsername(@PathVariable String username) {
        Usuario seguidor = usuarioRepository.findByUsername("usuarioActual");
        Usuario seguido = usuarioRepository.findByUsername(username);
        
        if (seguido != null && seguidor != null) {
            Seguimiento seguimiento = seguimientoRepository.findBySeguidorAndSeguido(seguidor.getIdUsuario(), seguido.getIdUsuario());
            if (seguimiento != null) {
                return ResponseEntity.ok(seguimiento);
            }
        }
        return ResponseEntity.notFound().build();
    }

    // Buscar seguidor por nombre completo
    @GetMapping("/buscarSeguidorNombre/{nombre}")
    public ResponseEntity<List<Seguimiento>> buscarSeguidorNombre(@PathVariable String nombre) {
        List<Seguimiento> seguidores = seguimientoRepository.findByNombreCompleto(nombre);
        if (!seguidores.isEmpty()) {
            return ResponseEntity.ok(seguidores);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
