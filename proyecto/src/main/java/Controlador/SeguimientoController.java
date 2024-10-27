package Controlador;

import Modelo.Seguimiento;
import Repository.SeguimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seguimientos")
public class SeguimientoController {

    @Autowired
    private SeguimientoRepository seguimientoRepository;

    @GetMapping
    public List<Seguimiento> getAllSeguimientos() {
        return seguimientoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Seguimiento> getSeguimientoById(@PathVariable Long id) {
        return seguimientoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Seguimiento createSeguimiento(@RequestBody Seguimiento seguimiento) {
        return seguimientoRepository.save(seguimiento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Seguimiento> updateSeguimiento(@PathVariable Long id, @RequestBody Seguimiento seguimientoDetails) {
        return seguimientoRepository.findById(id)
                .map(seguimiento -> {
                    // Actualiza los campos necesarios
                    return ResponseEntity.ok(seguimientoRepository.save(seguimiento));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteSeguimiento(@PathVariable Long id) {
        return seguimientoRepository.findById(id)
                .map(seguimiento -> {
                    seguimientoRepository.delete(seguimiento);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
