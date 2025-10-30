package Modelos;
import java.time.LocalDate;
import java.time.LocalTime;
import Interfaces.Reservable;
import Enum.EstadoTurno;

public class Turno implements Reservable {
    private int idTurno;
    private LocalDate fecha;
    private LocalTime hora;
    private EstadoTurno estado;
    private Cliente cliente;
    

    public Turno(int idTurno, LocalDate fecha, LocalTime hora, EstadoTurno estado, Cliente cliente) {
        this.idTurno = idTurno;
        this.fecha = fecha;
        this.hora = hora;
        this.estado = estado;

        this.cliente = cliente;
    }

    public int getIdTurno() { return idTurno; }
    public void setIdTurno(int idTurno) { this.idTurno = idTurno; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public LocalTime getHora() { return hora; }
    public void setHora(LocalTime hora) { this.hora = hora; }

    public EstadoTurno getEstado() { return estado; }
    public void setEstado(EstadoTurno estado) { this.estado = estado; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    @Override
    public void reservar() {
        this.estado = EstadoTurno.PENDIENTE;
    }

    @Override
    public void cancelar() {
        this.estado = EstadoTurno.CANCELADO;
    }
}

