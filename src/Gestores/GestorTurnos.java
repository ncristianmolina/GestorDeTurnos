package Gestores;

import Exceptions.NoHayTurnosDisponiblesException;
import Exceptions.TurnoOcupadoException;
import ManejoJSON.gestionJSONTurnos;
import Modelos.Actividad;
import Modelos.Turno;
import Enum.EstadoTurno;
import Util.IdGenerator;

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

        try {
            this.lista = gestionJSONTurnos.leerTurnos();

            int maxId = 0;
            for (int i = 0; i < this.lista.size(); i++) {
                Turno t = this.lista.get(i);
                if (t.getIdTurno() > maxId) {
                    maxId = t.getIdTurno();
                }
            }
            IdGenerator.setTurnoCount(maxId);

        } catch (Exception e) {
            System.out.println("No se pudieron cargar turnos previos: " + e.getMessage());
            this.lista = new ArrayList<>();
        }
    }



    public void reservarTurno(String nombreActividad, LocalDateTime fechaHora, String dniCliente)
            throws TurnoOcupadoException, NoHayTurnosDisponiblesException {

        Actividad actividad = gestorActividades.buscarPorNombre(nombreActividad);
        if (actividad == null) {
            throw new IllegalArgumentException("No se encontró la actividad con nombre: " + nombreActividad);
        }

        int cantidadActual = 0;
        boolean clienteYaReservo = false;

        for (Turno t : lista) {

            if (t.getFechaHora() != null && t.getIdActividad() == actividad.getIdActividad()
                    && t.getFechaHora().equals(fechaHora)) {
                cantidadActual++;
            }

            if (t.getDniCliente() != null && t.getDniCliente().equalsIgnoreCase(dniCliente)
                    && t.getFechaHora() != null && t.getFechaHora().equals(fechaHora)) {
                clienteYaReservo = true;
            }
        }

        if (cantidadActual >= actividad.getCapacidadMaxima()) {
            throw new NoHayTurnosDisponiblesException(
                    "No hay turnos disponibles para " + actividad.getTipoActividad() + " en " + fechaHora);
        }

        if (clienteYaReservo) {
            throw new TurnoOcupadoException("El cliente ya tiene un turno en ese horario.");
        }


        int idTurno = IdGenerator.generarId("turnos");
        Turno nuevoTurno = new Turno(idTurno, fechaHora, EstadoTurno.RESERVADO, dniCliente, actividad.getIdActividad());
        agregar(nuevoTurno);


        gestionJSONTurnos.grabarTurno(nuevoTurno);

        System.out.println("Turno reservado con éxito para " + dniCliente +
                " en la actividad: " + actividad.getTipoActividad() + " (" + fechaHora + ")");
    }



    // Método modificar turno (tipo de actividad o fecha)
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

    // Mostrar todos los turnos cargados
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

    // Metodo filtrado turnos por cliente
    public static List<Turno> turnosPorCliente(List<Turno> turnos, String dniClienteBuscado) {
        List<Turno> turnosCliente = new ArrayList<>();

        for (Turno t : turnos) {
            if (t.getDniCliente() != null && t.getDniCliente().equals(dniClienteBuscado)) {
                turnosCliente.add(t);
            }
        }

        return turnosCliente;
    }

    // Método para listar las actividades con turnos disponibles
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
