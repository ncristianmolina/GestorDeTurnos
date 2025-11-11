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

    public GestorTurnos() {
        this.turnos = new ArrayList<>();
    }

    public void reservarTurno(Actividad actividad, LocalDateTime fechaHora, String cliente)
            throws TurnoOcupadoException, NoHayTurnosDisponiblesException {

        int cantidadActual = 0;
        boolean clienteYaReservo = false;

        for (Turno t : turnos) {
            if (t.getIdActividad() == actividad && t.getFechaHora().equals(fechaHora)) {
                cantidadActual++;
            }
        }

        if (cantidadActual >= actividad.getCapacidadMaxima()) {
            throw new NoHayTurnosDisponiblesException(
                    "No hay turnos disponibles para " + actividad + " en " + fechaHora);
        }


        for (Turno t : turnos) {
            if (t.getCliente().equals(cliente) && t.getFechaHora().equals(fechaHora)) {
                clienteYaReservo = true;

            }
        }

        if (clienteYaReservo) {
            throw new TurnoOcupadoException("El cliente ya tiene un turno en ese horario.");
        }


        int idTurno = turnos.size() + 1;
        Cliente clienteObj =  gestor.buscarPorDni(cliente);
        turnos.add(new Turno(idTurno, fechaHora ,EstadoTurno.RESERVADO, clienteObj, actividad));

        System.out.println("Turno reservado con éxito para " + cliente + " en " + actividad);
    }


    // METODO ELIMINAR TURNO




    //METODO LIBERAR TURNO (NO SE ELIMINA )


    //METODO MODIFICAR TURNO






//METODO LISTAR TURNOS DISPONIBLES



//METODO MOSTRAR TODOS LOS TURNOS CARGADOS







}
