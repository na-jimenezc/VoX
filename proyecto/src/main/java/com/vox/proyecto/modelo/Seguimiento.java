package com.vox.proyecto.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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

    public Seguimiento(Long idUsuario, Long long1) {
        this.idSeguido = idSeguido;
        this.idSeguidor = idSeguidor;
    }

    public Seguimiento() {
        //TODO Auto-generated constructor stub
    }

    public void eliminarSeguimiento() {
        if (seguidor != null && seguido != null) {
            seguidor.getSeguidos().remove(this);
            seguido.getSeguidos().remove(this);
        }
    }

    public static List<Usuario> obtenerSeguidoresUsuario(Long idUsuario, List<Seguimiento> todosLosSeguimientos) {
        List<Usuario> seguidores = new ArrayList<>();
        for (Seguimiento seguimiento : todosLosSeguimientos) {
            if (seguimiento.getIdSeguido().equals(idUsuario)) {
                seguidores.add(seguimiento.getSeguidor());
            }
        }
        return seguidores;
    }

    // Setters actualizados para aceptar objetos Usuario
    public void setSeguidor(Usuario seguidor) {
        this.seguidor = seguidor;
    }

    public void setSeguido(Usuario seguido) {
        this.seguido = seguido;
    }
}
