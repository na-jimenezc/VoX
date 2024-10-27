package Repository;

import Modelo.Seguimiento;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeguimientoRepository extends JpaRepository<Seguimiento, Long> {

    Seguimiento findBySeguidorAndSeguido(Long long1, Long long2);

    List<Seguimiento> findBySeguido(Long long1);

    List<Seguimiento> findByNombreCompleto(String nombre);
}
