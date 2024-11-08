package com.vox.proyecto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vox.proyecto.modelo.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByUsername(String username);
    Usuario findByEmail(String email);  // Para buscar un usuario por email.
    List<Usuario> findByNotificacionesActivas(boolean notificacionesActivas);  // Filtrar usuarios por notificación activa

}
