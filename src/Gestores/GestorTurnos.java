package Gestores;

import Exceptions.NoHayTurnosDisponiblesException;
import Exceptions.TurnoOcupadoException;
import Modelos.Actividad;
import Modelos.Cliente;
import Modelos.Persona;
import Modelos.Turno;
import Enum.EstadoTurno;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GestorTurnos extends GestorGenerico<Turno> {

    public GestorClientes gestor;
    public GestorActividades gestorActividades;

    public GestorTurnos(GestorClientes gestorClientes, GestorActividades gestorActividades) {
        super();
        this.gestor = gestorClientes;
        this.gestorActividades = gestorActividades;
    }


    // Método reservar turno
    public void reservarTurno(String nombreActividad, LocalDateTime fechaHora, String dniCliente)
            throws TurnoOcupadoException, NoHayTurnosDisponiblesException {

        Actividad actividad = gestorActividades.buscarPorNombre(nombreActividad);
        if (actividad == null) {
            throw new IllegalArgumentException("No se encontró la actividad con nombre: " + nombreActividad);
        }

        int cantidadActual = 0;
        boolean clienteYaReservo = false;

        for (Turno t : lista) {
            if (t.getIdActividad() == actividad.getIdActividad() &&
                    t.getFechaHora().equals(fechaHora)) {
                cantidadActual++;
            }

            if (t.getDniCliente().equals(dniCliente) &&
                    t.getFechaHora().equals(fechaHora)) {
                clienteYaReservo = true;
            }
        }

        if (cantidadActual >= actividad.getCapacidadMaxima()) {
            throw new NoHayTurnosDisponiblesException(
                    "No hay turnos disponibles para " + actividad + " en " + fechaHora);
        }

        if (clienteYaReservo) {
            throw new TurnoOcupadoException("El cliente ya tiene un turno en ese horario.");
        }

        int idTurno = lista.size() + 1;
        Turno nuevoTurno = new Turno(idTurno, fechaHora, EstadoTurno.RESERVADO, dniCliente, actividad.getIdActividad());
        agregar(nuevoTurno);

        System.out.println("Turno reservado con éxito para " + dniCliente +
                " en la actividad: " + nombreActividad + " (" + fechaHora + ")");
    }



    // Metodo modificar turno (tipo de actividad o fecha)
    public boolean modificarTurno(int idTurno, int nuevoIdActividad, LocalDateTime nuevaFechaHora) {
        for (Turno t : lista) {
            if (t.getIdTurno() == idTurno) {
                t.setIdActividad(nuevoIdActividad);
                t.setFechaHora(nuevaFechaHora);
                System.out.println("Turno modificado correctamente.");
                return true;
            }
        }
        System.out.println("No se encontró un turno con ID " + idTurno);
        return false;
    }


    //Metodo mostrar todos los turnos cargados

    public void mostrarTurnos() {
        if (lista.isEmpty()) {
            System.out.println("No hay turnos registrados.");
        } else {
            for (Turno t : lista) {
                System.out.println("ID: " + t.getIdTurno() +
                        " | Cliente: " + t.getDniCliente() +
                        " | Actividad: " + t.getIdActividad() +
                        " | FechaHora: " + t.getFechaHora() +
                        " | Estado: " + t.getEstado());
            }
        }
    }



    //Método turnos por cliente

    public static List<Turno> turnosPorCliente(List<Turno> turnos, String dniClienteBuscado) {
        List<Turno> turnosCliente = new ArrayList<>();

        for (Turno t : turnos) {
            if (t.getDniCliente().equals(dniClienteBuscado)) {
                turnosCliente.add(t);
            }
        }

        return turnosCliente;
    }


    //Método para listar las actividades que todavía tienen turnos disponibles.
    // Se considera que una actividad está disponible si la cantidad de turnos
    // reservados es menor que su capacidad máxima.

    public static List<Actividad> listarTurnosDisponibles(List<Actividad> actividades, List<Turno> turnos) {
        List<Actividad> disponibles = new ArrayList<>();

        for (Actividad a : actividades) {
            int contador = 0;


            for (Turno t : turnos) {
                if (t.getIdActividad() == a.getIdActividad()) {
                    contador++;
                }
            }


            if (contador < a.getCapacidadMaxima()) {
                disponibles.add(a);
            }
        }

        return disponibles;
    }


}
