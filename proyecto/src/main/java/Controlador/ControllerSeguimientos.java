package Controlador;

import Modelo.Seguimiento;
import Repository.SeguimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seguimientos")
public class ControllerSeguimientos {

    @Autowired
    private SeguimientoRepository seguimientoRepository;

    // Seguir a un usuario
    @PostMapping("/seguir/{username}")
    public ResponseEntity<Void> seguir(@PathVariable String username) {
        // Aquí debes implementar la lógica para seguir al usuario
        // Suponiendo que tienes un método en el repositorio que maneja esto
        Seguimiento nuevoSeguimiento = new Seguimiento(); 
        nuevoSeguimiento.setSeguidor("usuarioActual");
        nuevoSeguimiento.setSeguido(username);
        
        seguimientoRepository.save(nuevoSeguimiento);
        return ResponseEntity.ok().build();
    }

    // Dejar de seguir a un usuario
    @DeleteMapping("/dejarSeguir/{username}")
    public ResponseEntity<Void> dejarSeguir(@PathVariable String username) {
        // Aquí debes implementar la lógica para dejar de seguir al usuario
        // Suponiendo que tienes un método para buscar el seguimiento
        Seguimiento seguimiento = seguimientoRepository.findBySeguidorAndSeguido("usuarioActual", username);
        
        if (seguimiento != null) {
            seguimientoRepository.delete(seguimiento);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Ver seguidores del usuario actual
    @GetMapping("/seguidores")
    public List<Seguimiento> verSeguidores() {
        // Aquí debes implementar la lógica para obtener los seguidores del usuario actual
        return seguimientoRepository.findBySeguido("usuarioActual"); // Reemplaza con el usuario autenticado
    }

    // Buscar seguidor por nombre de usuario
    @GetMapping("/buscarSeguidorUsername/{username}")
    public ResponseEntity<Seguimiento> buscarSeguidorUsername(@PathVariable String username) {
        // Aquí debes implementar la lógica para buscar un seguidor por nombre de usuario
        Seguimiento seguimiento = seguimientoRepository.findBySeguidorAndSeguido("usuarioActual", username);
        
        if (seguimiento != null) {
            return ResponseEntity.ok(seguimiento);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Buscar seguidor por nombre completo
    @GetMapping("/buscarSeguidorNombre/{nombre}")
    public ResponseEntity<List<Seguimiento>> buscarSeguidorNombre(@PathVariable String nombre) {
        // Aquí debes implementar la lógica para buscar seguidores por nombre completo
        // Suponiendo que tienes un método que busca por nombre completo en tu repositorio
        List<Seguimiento> seguidores = seguimientoRepository.findByNombreCompleto(nombre);
        
        if (!seguidores.isEmpty()) {
            return ResponseEntity.ok(seguidores);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
