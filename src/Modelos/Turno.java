package Modelos;
import java.time.LocalDate;
import java.time.LocalDateTime;

import Interfaces.Reservable;
import Enum.EstadoTurno;

public class Turno implements Reservable {
    private int idTurno;
    private LocalDateTime fechaHora;
    private EstadoTurno estado;
    private String dniCliente;
    private int idActividad;


    public Turno(int idTurno, LocalDateTime fechaHora, EstadoTurno estado, String dniCliente, int idActividad) {
        this.idTurno = idTurno;
        this.fechaHora = fechaHora;
        this.estado = estado;
        this.dniCliente = dniCliente;
        this.idActividad = idActividad;
    }


    public int getIdTurno() {
        return idTurno; }

    public void setIdTurno(int idTurno) {
        this.idTurno = idTurno; }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public EstadoTurno getEstado() { return estado; }
    public void setEstado(EstadoTurno estado) { this.estado = estado; }

    public String getDniCliente() { return dniCliente; }
    public void setDniCliente(Cliente cliente) { this.dniCliente = dniCliente; }

    public int getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(int idActividad) {
        this.idActividad = idActividad;
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

