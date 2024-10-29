package repository;

import com.vox.proyecto.modelo.Seguimiento;
import com.vox.proyecto.modelo.Usuario;
import com.vox.proyecto.repository.SeguimientoRepository;
import com.vox.proyecto.repository.UsuarioRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class SeguimientoRepositoryTest {

    @Autowired
    private SeguimientoRepository seguimientoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private Usuario seguidor;
    private Usuario seguido;

    @BeforeEach
    public void setUp() {
        seguidor = new Usuario();
        seguidor.setUsername("usuarioSeguidor");
        usuarioRepository.save(seguidor);

        seguido = new Usuario();
        seguido.setUsername("usuarioSeguido");
        usuarioRepository.save(seguido);

        Seguimiento seguimiento = new Seguimiento();
        seguimiento.setSeguidor(seguidor);
        seguimiento.setSeguido(seguido);
        seguimientoRepository.save(seguimiento);
    }

    @Test
    public void testFindBySeguidorAndSeguido() {
        Seguimiento seguimiento = seguimientoRepository.findBySeguidorAndSeguido(seguidor.getIdUsuario(), seguido.getIdUsuario());
        assertThat(seguimiento).isNotNull();
    }

    @Test
    public void testFindBySeguido() {
        List<Seguimiento> seguidores = seguimientoRepository.findBySeguido(seguido.getIdUsuario());
        assertThat(seguidores).isNotEmpty();
    }

    @Test
    public void testFindByNombreCompleto() {
        List<Seguimiento> seguidores = seguimientoRepository.findByNombreCompleto("nombreCompleto");
        assertThat(seguidores).isEmpty(); // Asume que no hay registros con ese nombre por defecto
    }
}
