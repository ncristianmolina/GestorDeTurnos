package Gestores;

import Exceptions.NoHayTurnosDisponiblesException;
import Exceptions.TurnoOcupadoException;
import Modelos.Actividad;
import Modelos.Cliente;
import Modelos.Turno;
import Enum.EstadoTurno;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class GestorTurnos {
    public ArrayList<Turno> turnos;
    public GestorClientes gestor;
    public GestorActividades gestorActividades;

    public GestorTurnos() {
        this.turnos = new ArrayList<>();
    }

    public void reservarTurno(String nombreActividad, LocalDateTime fechaHora, String dniCliente)
            throws TurnoOcupadoException, NoHayTurnosDisponiblesException {

        Actividad actividad = gestorActividades.buscarPorNombre(nombreActividad);
        if (actividad == null) {
            throw new IllegalArgumentException("❌ No se encontró la actividad con nombre: " + nombreActividad);
        }

        int cantidadActual = 0;
        boolean clienteYaReservo = false;

        for (Turno t : turnos) {
            if (t.getIdActividad() == actividad.getIdActividad() &&
                    t.getFechaHora().equals(fechaHora))  {
                cantidadActual++;
            }
        }

        if (cantidadActual >= actividad.getCapacidadMaxima()) {
            throw new NoHayTurnosDisponiblesException(
                    "No hay turnos disponibles para " + actividad + " en " + fechaHora);
        }


        for (Turno t : turnos) {
            if (t.getDniCliente().equals(dniCliente) &&
                    t.getFechaHora().equals(fechaHora)) {
                clienteYaReservo = true;

            }
        }

        if (clienteYaReservo) {
            throw new TurnoOcupadoException("El cliente ya tiene un turno en ese horario.");
        }


        int idTurno = turnos.size() + 1;
        Turno nuevoTurno = new Turno(idTurno, fechaHora, EstadoTurno.RESERVADO, dniCliente, actividad.getIdActividad());
        turnos.add(nuevoTurno);

        System.out.println("✔ Turno reservado con éxito para " + dniCliente +
                " en la actividad: " + nombreActividad + " (" + fechaHora + ")");
    }


    // METODO ELIMINAR TURNO




    //METODO LIBERAR TURNO (NO SE ELIMINA )


    //METODO MODIFICAR TURNO






//METODO LISTAR TURNOS DISPONIBLES



//METODO MOSTRAR TODOS LOS TURNOS CARGADOS







}
