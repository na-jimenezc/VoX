package Repository;

import Modelo.Seguimiento;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeguimientoRepository extends JpaRepository<Seguimiento, Long> {

    Seguimiento findBySeguidorAndSeguido(String string, String username);
    // Metodos

    List<Seguimiento> findBySeguido(String string);

    List<Seguimiento> findByNombreCompleto(String nombre);
}
