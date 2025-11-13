package Crud;

import Gestores.GestorTurnos;
import Gestores.GestorActividades;
import Gestores.GestorClientes;
import ManejoJSON.gestionJSONTurnos;
import Modelos.Turno;
import Modelos.Actividad;
import Modelos.Cliente;
import Enum.EstadoTurno;
import Exceptions.TurnoOcupadoException;
import Exceptions.NoHayTurnosDisponiblesException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class CrudTurnos {

    private final Scanner scanner;
    private final GestorTurnos gestorTurnos;
    private final GestorClientes gestorClientes;
    private final GestorActividades gestorActividades;
    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public CrudTurnos(Scanner scanner, GestorTurnos gestorTurnos, GestorClientes gestorClientes, GestorActividades gestorActividades) {
        this.scanner = scanner;
        this.gestorTurnos = gestorTurnos;
        this.gestorClientes = gestorClientes;
        this.gestorActividades = gestorActividades;
    }

    public void alta() {
        System.out.println("=== RESERVAR TURNO ===");
        System.out.print("DNI del cliente: ");
        String dni = scanner.nextLine();

        // validar cliente activo
        Cliente cliente = null;
        try {
            cliente = (Cliente) gestorClientes.buscarPorDni(dni);
            if (!cliente.getEsActivo()) {
                System.out.println("Cliente inactivo.");
                return;
            }
        } catch (Exception e) {
            System.out.println("Cliente no encontrado.");
            return;
        }

        // mostrar actividades y seleccionar por ID
        System.out.println("Actividades:");
        for (Actividad a : gestorActividades.getLista()) {
            System.out.println(a.getIdActividad() + " - " + a.getTipoActividad() + " (cap: " + a.getCapacidadMaxima() + ")");
        }
        System.out.print("Ingrese ID de actividad: ");
        int idAct = Integer.parseInt(scanner.nextLine());

        Actividad actividad = gestorActividades.buscarPorId(idAct);
        if (actividad == null) {
            System.out.println("Actividad no encontrada.");
            return;
        }

        System.out.print("Fecha y hora (yyyy-MM-dd HH:mm): ");
        LocalDateTime fechaHora;
        try {
            fechaHora = LocalDateTime.parse(scanner.nextLine(), FORMAT);
        } catch (Exception e) {
            System.out.println("Formato de fecha inválido.");
            return;
        }

        try {
            gestorTurnos.reservarTurno(actividad.getTipoActividad(), fechaHora, dni);
            System.out.println("Turno reservado correctamente.");
        } catch (TurnoOcupadoException | NoHayTurnosDisponiblesException e) {
            System.out.println("Error al reservar: " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("Error al guardar turnos: " + e.getMessage());
        }
    }

    public void cancelar() {
        System.out.println("=== CANCELAR TURNO ===");
        System.out.print("Ingrese ID del turno: ");
        int id = Integer.parseInt(scanner.nextLine());

        for (Turno t : gestorTurnos.getLista()) {
            if (t.getIdTurno() == id) {
                t.cancelar();
                try {
                    // persistimos TURNOS después de cancelar
                    gestionJSONTurnos.grabarTurnos(gestorTurnos.getLista());
                    System.out.println("Turno cancelado correctamente.");
                } catch (RuntimeException e) {
                    System.out.println("Turno cancelado en memoria pero falló al guardar: " + e.getMessage());
                }
                return;
            }
        }
        System.out.println("No se encontró un turno con ese ID.");
    }

    public void listarTurnos() {
        System.out.println("=== LISTA DE TURNOS ===");
        gestorTurnos.mostrarTurnos();
    }

    // getter del gestor si necesitás usarlo externamente
    public GestorTurnos getGestorTurnos() {
        return gestorTurnos;
    }
}
