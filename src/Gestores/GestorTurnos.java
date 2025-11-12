package Gestores;

import Exceptions.NoHayTurnosDisponiblesException;
import Exceptions.TurnoOcupadoException;
import Modelos.Actividad;
import Modelos.Cliente;
import Modelos.Turno;
import Enum.EstadoTurno;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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


    /*

    METODO MODIFICAR TURNO- horario y tipo de actividad

    METODO MOSTRAR TODOS LOS TURNOS CARGADOS

    */

/*Método actividades con más inscriptos*/

    public static List<Actividad> actividadesConMasInscriptos(List<Actividad> actividades, List<Turno> turnos) {

        int maxInscriptos = 0;

        for (Actividad a : actividades) {
            int contador = 0;

            for (Turno t : turnos) {
                if (t.getIdActividad() == a.getIdActividad()) {
                    contador++;
                }
            }

            if (contador > maxInscriptos) {
                maxInscriptos = contador;
            }
        }

        List<Actividad> resultado = new ArrayList<>();

        for (Actividad a : actividades) {
            int contador = 0;

            for (Turno t : turnos) {
                if (t.getIdActividad() == a.getIdActividad()) {
                    contador++;
                }
            }

            if (contador == maxInscriptos) {
                resultado.add(a);
            }
        }

        return resultado;
    }

    /*Método turnos por cliente*/

    public static List<Turno> turnosPorCliente(List<Turno> turnos, String dniClienteBuscado) {
        List<Turno> turnosCliente = new ArrayList<>();

        for (Turno t : turnos) {
            if (t.getDniCliente().equals(dniClienteBuscado)) {
                turnosCliente.add(t);
            }
        }

        return turnosCliente;
    }





}
