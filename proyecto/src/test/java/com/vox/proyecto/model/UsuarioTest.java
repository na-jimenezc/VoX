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
    private Publicacion publicacion1, publicacion2;

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
    public void testDejarDeSeguirUsuarioNoSeguido() {
        // Asegúrate de que usuario1 no sigue a usuario2
        assertTrue(usuario1.getSeguidos().isEmpty(), "usuario1 no debería seguir a nadie al inicio");
        
        // usuario1 intenta dejar de seguir a usuario2
        usuario1.dejarSeguir(usuario2);
        
        // Verifica que la lista de seguidos de usuario1 sigue vacía
        assertTrue(usuario1.getSeguidos().isEmpty(), "usuario1 no debería tener usuarios seguidos después de intentar dejar de seguir a un no seguido");
        
        // Verifica que usuario2 aún tiene la misma cantidad de seguidores
        assertEquals(0, usuario2.getSeguidores().size(), "usuario2 no debería tener seguidores después del intento fallido");
    }

    @Test
    public void testDarLikeAnonimo() {
        Publicacion publicacion = usuario1.hacerPublicacion("Publicación para like anónimo", true);
        usuario1.darLikeAnonimo(publicacion);
        List<Like> likes = publicacion.getLikes();
        assertEquals(1, likes.size(), "La publicación debe tener 1 like anónimo");
        assertTrue(likes.get(0).getAnonimoLike(), "El like debe ser anónimo");
    }
    
    @Test
    public void testDarLikePublico() {
        Publicacion publicacion = usuario2.hacerPublicacion("Publicación para like público", false);
        usuario1.darLikePublico(publicacion);
        List<Like> likes = publicacion.getLikes();
        assertEquals(1, likes.size(), "La publicación debe tener 1 like público");
        assertFalse(likes.get(0).getAnonimoLike(), "El like no debe ser anónimo");
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
