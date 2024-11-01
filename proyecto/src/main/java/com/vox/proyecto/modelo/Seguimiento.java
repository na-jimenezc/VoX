package com.vox.proyecto.modelo;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Seguimiento {

    @Id
    @GeneratedValue
    private Long idSeguimiento;

    private Long idSeguido;
    private Long idSeguidor;

    @ManyToOne
    private Usuario seguidor;

    @ManyToOne
    private Usuario seguido;

    // Constructor que recibe los IDs correcto
    public Seguimiento(Long idSeguidor, Long idSeguido) {
        this.idSeguido = idSeguido;
        this.idSeguidor = idSeguidor;
    }

    public Seguimiento() {
        // Constructor vacío
    }

    // Método para eliminar el seguimiento entre usuarios
    public void eliminarSeguimiento() {
        if (seguidor != null) {
            seguidor.getSeguidos().remove(this);
        }
        if (seguido != null) {
            seguido.getSeguidores().remove(this);
        }
    }

    // Método para obtener todos los seguidores de un usuario
    public static List<Usuario> obtenerSeguidoresUsuario(Long idUsuario, List<Seguimiento> todosLosSeguimientos) {
        List<Usuario> seguidores = new ArrayList<>();
        for (Seguimiento seguimiento : todosLosSeguimientos) {
            if (seguimiento.getIdSeguido().equals(idUsuario)) {
                seguidores.add(seguimiento.getSeguidor());
            }
        }
        return seguidores;
    }

    // Setters para Usuario seguidor y seguido
    public void setSeguidor(Usuario seguidor) {
        this.seguidor = seguidor;
        this.idSeguidor = seguidor != null ? seguidor.getIdUsuario() : null;
    }

    public void setSeguido(Usuario seguido) {
        this.seguido = seguido;
        this.idSeguido = seguido != null ? seguido.getIdUsuario() : null;
    }
}
