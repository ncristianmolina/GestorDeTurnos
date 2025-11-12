package Crud;

import Gestores.GestorGenerico;
import Modelos.Turno;
import Modelos.Cliente;
import Modelos.Actividad;
import Enum.EstadoTurno;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class CrudTurnos extends GestorGenerico<Turno> {

    private final Scanner scanner;
    private final CrudClientes crudClientes;
    private final CrudActividades crudActividades;
    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public CrudTurnos(Scanner scanner, CrudClientes crudClientes, CrudActividades crudActividades) {
        super();
        this.scanner = scanner;
        this.crudClientes = crudClientes;
        this.crudActividades = crudActividades;
    }

    public void alta() {
        System.out.println("=== RESERVAR TURNO ===");
        System.out.print("DNI del cliente: ");
        String dni = scanner.nextLine();

        Cliente cliente = null;
        for (Cliente c : crudClientes.getLista()) {
            if (c.getDni().equalsIgnoreCase(dni) && c.getEsActivo()) {
                cliente = c;
                break;
            }
        }
        if (cliente == null) {
            System.out.println("Cliente no encontrado o inactivo.");
            return;
        }

        crudActividades.listarActividades();
        System.out.print("Ingrese ID de actividad: ");
        int idAct = Integer.parseInt(scanner.nextLine());

        Actividad actividad = null;
        for (Actividad a : crudActividades.getLista()) {
            if (a.getIdActividad() == idAct) {
                actividad = a;
                break;
            }
        }
        if (actividad == null) {
            System.out.println("⚠ Actividad no encontrada.");
            return;
        }

        System.out.print("Fecha y hora (yyyy-MM-dd HH:mm): ");
        LocalDateTime fechaHora = LocalDateTime.parse(scanner.nextLine(), FORMAT);

        int idTurno = lista.size() + 1;
        Turno turno = new Turno(idTurno, fechaHora, EstadoTurno.RESERVADO, dni, idAct);
        agregar(turno);

        System.out.println("✔ Turno reservado correctamente.");
    }

    public void cancelar() {
        System.out.println("=== CANCELAR TURNO ===");
        System.out.print("Ingrese ID del turno: ");
        int id = Integer.parseInt(scanner.nextLine());

        for (Turno t : lista) {
            if (t.getIdTurno() == id) {
                t.cancelar();
                System.out.println("✔ Turno cancelado.");
                return;
            }
        }

        System.out.println("No se encontró un turno con ese ID.");
    }

    public void listarTurnos() {
        System.out.println("=== LISTA DE TURNOS ===");
        for (Turno t : lista) {
            System.out.println("ID: " + t.getIdTurno() +
                    " | Cliente: " + t.getDniCliente() +
                    " | Actividad: " + t.getIdActividad() +
                    " | Fecha: " + t.getFechaHora() +
                    " | Estado: " + t.getEstado());
        }
    }
}
