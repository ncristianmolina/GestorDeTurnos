package Crud;

import Gestores.GestorActividades;
import Modelos.Actividad;

import java.util.Scanner;

public class CrudActividades {

    private final Scanner scanner;
    private final GestorActividades gestor;

    public CrudActividades(Scanner scanner, GestorActividades gestor) {
        this.scanner = scanner;
        this.gestor = gestor;
    }

    public void alta() {
        System.out.println("=== ALTA DE ACTIVIDAD ===");
        System.out.print("Capacidad máxima: ");
        int capacidad = Integer.parseInt(scanner.nextLine());
        System.out.print("Precio: ");
        double precio = Double.parseDouble(scanner.nextLine());
        System.out.print("Tipo de actividad: ");
        String tipo = scanner.nextLine();

        int id = gestor.cantidad() + 1;
        Actividad nueva = new Actividad();
        nueva.setCapacidadMaxima(capacidad);
        nueva.setPrecio(precio);
        nueva.setTipoActividad(tipo);
        nueva.setIdActividad(id);

        gestor.agregar(nueva);
        System.out.println("Actividad agregada correctamente.");
    }

    public void baja() {
        System.out.println("=== ELIMINAR ACTIVIDAD ===");
        System.out.print("Ingrese ID de la actividad a eliminar: ");
        int id = Integer.parseInt(scanner.nextLine());
        Actividad encontrada = gestor.buscarPorId(id);
        if (encontrada != null) {
            gestor.getLista().remove(encontrada);
            System.out.println("Actividad eliminada correctamente.");
        } else {
            System.out.println("No se encontró actividad con ese ID.");
        }
    }

    public void modificacion() {
        System.out.println("=== MODIFICAR ACTIVIDAD ===");
        System.out.print("Ingrese ID de la actividad: ");
        int id = Integer.parseInt(scanner.nextLine());
        Actividad a = gestor.buscarPorId(id);
        if (a == null) {
            System.out.println("Actividad no encontrada.");
            return;
        }

        System.out.println("Actividad actual: " + a);
        System.out.print("Nueva capacidad (ENTER para mantener): ");
        String capStr = scanner.nextLine();
        if (!capStr.isBlank()) a.setCapacidadMaxima(Integer.parseInt(capStr));

        System.out.print("Nuevo precio (ENTER para mantener): ");
        String precioStr = scanner.nextLine();
        if (!precioStr.isBlank()) a.setPrecio(Double.parseDouble(precioStr));

        System.out.print("Nuevo tipo (ENTER para mantener): ");
        String tipoStr = scanner.nextLine();
        if (!tipoStr.isBlank()) a.setTipoActividad(tipoStr);

        System.out.println("Actividad modificada correctamente.");
    }

    public void listarActividades() {
        System.out.println("=== LISTA DE ACTIVIDADES ===");
        if (gestor.getLista().isEmpty()) {
            System.out.println("No hay actividades registradas.");
            return;
        }
        for (Actividad a : gestor.getLista()) {
            System.out.println(a);
        }
    }

    // getter para uso externo si hace falta
    public GestorActividades getGestor() {
        return gestor;
    }
}
