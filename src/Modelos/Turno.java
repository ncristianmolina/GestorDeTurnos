package Modelos;
import java.time.LocalDate;
import java.time.LocalDateTime;

import Interfaces.Reservable;
import Enum.EstadoTurno;

public class Turno implements Reservable {
    private int idTurno;
    private LocalDateTime fechaHora;
    private EstadoTurno estado;
    private Cliente cliente;
    private Actividad Actividad;

    public Turno(int idTurno, LocalDateTime fechaHora, EstadoTurno estado, Cliente cliente, Actividad actividad) {
        this.idTurno = idTurno;
        this.fechaHora = fechaHora;
        this.estado = estado;
        this.cliente = cliente;
        Actividad = actividad;
    }


    public int getIdTurno() { return idTurno; }
    public void setIdTurno(int idTurno) { this.idTurno = idTurno; }

    public LocalDate getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDate fechaHora) {
        this.fechaHora = fechaHora;
    }

    public EstadoTurno getEstado() { return estado; }
    public void setEstado(EstadoTurno estado) { this.estado = estado; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public Actividad getActividad() {
        return Actividad;
    }

    public void setActividad(Actividad actividad) {
        Actividad = actividad;
    }

    @Override
    public void reservar() {
        this.estado = EstadoTurno.PENDIENTE;
    }

    @Override
    public void cancelar() {
        this.estado = EstadoTurno.CANCELADO;
    }
}

