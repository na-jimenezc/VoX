package com.vox.proyecto.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.vox.proyecto.modelo.Like;
import com.vox.proyecto.modelo.Publicacion;
import com.vox.proyecto.modelo.Seguimiento;
import com.vox.proyecto.modelo.Usuario;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class UsuarioTest {
    private Usuario usuario1;
    private Usuario usuario2;
    private Publicacion publicacion1;
    private Publicacion publicacion2;

    // Inicialización de usuarios y publicaciones antes de cada prueba
    @BeforeEach
    public void setUp() {
        usuario1 = new Usuario("Katheryn", "kathe123", "contraseña", 18, "Ingeniería de Sistemas", "4to", "Monogamía, Dios y Patria", "KatherynGuasca@javeriana.edu.co");
        usuario2 = new Usuario("Ardi", "ardi123", "contraseña", 119, "Ingeniería de Bucaramanga", "4to", "Viva el atlético Bucaramanga", "JuanArdi@javeriana.edu.co");

        // Publicaciones con autores y configuraciones iniciales
        publicacion1 = new Publicacion(usuario1, "Quiero saber el nombre de este chico", true);
        publicacion2 = new Publicacion(usuario2, "Les quiero contar una historia...", false);
    }

    @Test
    public void testSeguirUsuario() {
        usuario1.seguir(usuario2);
        List<Seguimiento> seguidos = usuario1.getSeguidos();

        assertEquals(1, seguidos.size(), "El usuario debería tener 1 seguido");
        assertEquals(usuario2.getIdUsuario(), seguidos.get(0).getIdSeguido(), "El ID del usuario seguido debe coincidir");
    }
    
    @Test
    public void testDejarDeSeguirUsuario() {
        // Crear usuarios de prueba
        Usuario usuario1 = new Usuario("User1", "user1", "password1", 25, "Carrera", "5to", "Bio1", "user1@example.com");
        Usuario usuario2 = new Usuario("User2", "user2", "password2", 27, "Carrera", "7mo", "Bio2", "user2@example.com");
    
        // usuario1 sigue a usuario2
        usuario1.seguir(usuario2);
    
        // Verifica que usuario1 tiene al menos un usuario en su lista de seguidos
        assertFalse(usuario1.getSeguidos().isEmpty(), "usuario1 debería tener al menos un usuario seguido");
    
        // usuario1 deja de seguir a usuario2
        usuario1.dejarSeguir(usuario2);
    
        // Asegura que usuario1 ya no tiene usuarios en su lista de seguidos
        assertTrue(usuario1.getSeguidos().isEmpty(), "El usuario1 no debería tener usuarios seguidos después de dejar de seguir");
    
        // Verifica que usuario1 ya no aparece en la lista de seguidores de usuario2
        boolean esSeguidor = usuario2.getSeguidores().stream()
                                    .anyMatch(seg -> seg.getSeguidor().equals(usuario1));
        assertFalse(esSeguidor, "usuario1 ya no debería ser seguidor de usuario2");
    }
    

    
    @Test
    public void testDarLikeAnonimo() {
        Usuario usuarioTest = new Usuario("TestUser", "testuser123", "contraseña", 25, "Ingeniería de Software", "5to", "Nueva biografía", "testuser@javeriana.edu.co");
        Publicacion publicacionTest = new Publicacion(usuarioTest, "Prueba de like anónimo", true);
    
        // Dar like anónimo
        usuarioTest.darLike(publicacionTest, true);
    
        // Verificar que la publicación tiene exactamente un like
        assertEquals(1, publicacionTest.getLikes().size());
    
        // Verificar que el like es anónimo y pertenece al usuario correcto
        Like like = publicacionTest.getLikes().get(0);
        assertTrue(like.getAnonimoLike());
        assertEquals(usuarioTest.getIdUsuario(), like.getUsuario().getIdUsuario());
    }
    

    @Test
    public void testDarLikePublico() {
        usuario1.darLike(publicacion2, false); // usuario1 da un like público a publicacion2
        assertEquals(1, publicacion2.getLikes().size(), "La publicación debe tener 1 like"); // Verifica que haya un like
        assertFalse(publicacion2.getLikes().get(0).getAnonimoLike(), "El like debe ser público"); // Verifica que no sea anónimo
        assertEquals(usuario1.getIdUsuario(), publicacion2.getLikes().get(0).getUsuario().getIdUsuario(), "El like debe pertenecer a usuario1"); // Verifica que el like pertenezca a usuario1
    }

    @Test
    public void testQuitarLike() {
        usuario1.darLike(publicacion1, true); // usuario1 da un like anónimo a publicacion1
        usuario1.quitarLike(publicacion1); // usuario1 quita el like a publicacion1
        assertTrue(publicacion1.getLikes().isEmpty(), "La publicación no debe tener likes después de quitarlos");
    }
    

    @Test
    public void testHacerPublicacionAnonima() {
        Publicacion nuevaPublicacionAnonima = usuario1.hacerPublicacion("Esta es una publicación anónima", true);
        assertTrue(usuario1.getPublicaciones().contains(nuevaPublicacionAnonima), "La publicación anónima debe estar en la lista de publicaciones del usuario");
        assertTrue(nuevaPublicacionAnonima.getAnonimo(), "La publicación debe ser anónima");
    }

    @Test
    public void testHacerPublicacionPublica() {
        Publicacion nuevaPublicacionPublica = usuario1.hacerPublicacion("Esta es una publicación pública", false);
        assertTrue(usuario1.getPublicaciones().contains(nuevaPublicacionPublica), "La publicación pública debe estar en la lista de publicaciones del usuario");
        assertFalse(nuevaPublicacionPublica.getAnonimo(), "La publicación debe ser pública");
    }

    @Test
    public void testEliminarPublicacion() {
        Publicacion pubAEliminar = usuario1.hacerPublicacion("Prueba de eliminación", false);
        boolean eliminado = usuario1.eliminarPublicacion(pubAEliminar);

        assertTrue(eliminado, "La publicación debe haberse eliminado correctamente");
        assertFalse(usuario1.getPublicaciones().contains(pubAEliminar), "La lista de publicaciones no debe contener la publicación eliminada");
    }

    @Test
    public void testEditarBiografia() {
        String nuevaBiografia = "Nueva biografía actualizada";
        usuario1.setBiografia(nuevaBiografia);

        assertEquals(nuevaBiografia, usuario1.getBiografia(), "La biografía debe actualizarse correctamente");
    }

    @Test
    public void testCambiarUsername() {
        String nuevoUsername = "kathe456";
        usuario1.editarUsername(usuario1.getUsername(), nuevoUsername);

        assertEquals(nuevoUsername, usuario1.getUsername(), "El username debe cambiarse correctamente");
    }
}
