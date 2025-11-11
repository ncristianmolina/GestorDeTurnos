package Crud;

import Modelos.Actividad;
import Modelos.Cliente;
import Modelos.Turno;
import Gestores.GestorClientes;
import Gestores.GestorTurnos;
import Exceptions.*;
import Enum.EstadoTurno;
import ManejoJSON.gestionJSONTurnos;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class CrudTurnos {
    private GestorTurnos gestorTurnos;
    private GestorClientes gestorClientes;
    private CrudActividades crudActividades;
    private Scanner scanner;
    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public CrudTurnos(GestorTurnos gt, GestorClientes gc, CrudActividades ca) {
        this.gestorTurnos = gt;
        this.gestorClientes = gc;
        this.crudActividades = ca;
        this.scanner = new Scanner(System.in);
    }

    public void alta() {
        System.out.println("=== RESERVAR TURNO ===");
        System.out.print("DNI cliente: ");
        String dni = scanner.nextLine();
        try {
            Cliente c = gestorClientes.buscarPorDni(dni);

            System.out.println("Actividades disponibles:");
            crudActividades.listar();
            System.out.print("Tipo de actividad a reservar: ");
            String tipo = scanner.nextLine();

            Actividad chosen = null;
            for (Actividad a : crudActividades.getActividades()) {
                if (a.getTipoActividad().equalsIgnoreCase(tipo)) {
                    chosen = a;
                    break;
                }
            }
            if (chosen == null) {
                System.out.println("Actividad no encontrada.");
                return;
            }

            System.out.print("Fecha y hora (yyyy-MM-dd HH:mm): ");
            String fecha = scanner.nextLine();
            LocalDateTime fechaHora = LocalDateTime.parse(fecha, FORMAT);

            gestorTurnos.reservarTurno(chosen, fechaHora, dni);
            // persistir turnos
            gestionJSONTurnos.guardarTurnos(gestorTurnos.turnos);
        } catch (ClienteNoEncontradoException | TurnoOcupadoException | NoHayTurnosDisponiblesException e) {
            System.out.println(e.getMessage());
        } catch (Exception ex) {
            System.out.println("Error al reservar: " + ex.getMessage());
        }
    }

    public void cancelar() {
        System.out.println("=== CANCELAR TURNO ===");
        System.out.print("ID de turno: ");
        int id = Integer.parseInt(scanner.nextLine());
        Turno toCancel = null;
        for (Turno t : gestorTurnos.turnos) {
            if (t.getIdTurno() == id) {
                toCancel = t;
                break;
            }
        }
        if (toCancel != null) {
            toCancel.cancelar();
            System.out.println("Turno cancelado.");
            gestionJSONTurnos.guardarTurnos(gestorTurnos.turnos);
        } else {
            System.out.println("Turno no encontrado.");
        }
    }

    public void modificacion() {
        System.out.println("=== MODIFICAR TURNO ===");
        System.out.print("ID de turno: ");
        int id = Integer.parseInt(scanner.nextLine());
        Turno toModify = null;
        for (Turno t : gestorTurnos.turnos) {
            if (t.getIdTurno() == id) {
                toModify = t;
                break;
            }
        }
        if (toModify == null) {
            System.out.println("Turno no encontrado.");
            return;
        }

        System.out.print("Nueva fecha y hora (yyyy-MM-dd HH:mm): ");
        String fecha = scanner.nextLine();
        if (!fecha.isBlank()) {
            LocalDateTime nueva = LocalDateTime.parse(fecha, FORMAT);
            toModify.setFechaHora(nueva);
            System.out.println("Turno modificado.");
            gestionJSONTurnos.guardarTurnos(gestorTurnos.turnos);
        }
    }

    public void listar() {
        System.out.println("=== LISTA DE TURNOS ===");
        for (Turno t : gestorTurnos.turnos) {
            System.out.println("ID:" + t.getIdTurno() +
                    " - Fecha:" + t.getFechaHora() +
                    " - Estado:" + t.getEstado() +
                    " - Cliente:" + t.getCliente().getDni() +
                    " - Actividad:" + t.getActividad().getTipoActividad());
        }
    }
}
