package Crud;

import Modelos.Actividad;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import ManejoJSON.gestionJSONActividad;

public class CrudActividades {
    // Si tenés un GestorActividades, usalo; acá mantengo una lista local simple
    private List<Actividad> actividades;
    private Scanner scanner;

    public CrudActividades() {
        this.actividades = new ArrayList<>();
        this.scanner = new Scanner(System.in);
    }

    public List<Actividad> getActividades() {
        return actividades;
    }

    public void alta() {
        System.out.println("=== ALTA DE ACTIVIDAD ===");
        System.out.print("Tipo actividad: ");
        String tipo = scanner.nextLine();
        System.out.print("Capacidad máxima: ");
        int cap = Integer.parseInt(scanner.nextLine());
        System.out.print("Precio: ");
        double precio = Double.parseDouble(scanner.nextLine());

        Actividad a = new Actividad(cap, precio, tipo);
        actividades.add(a);
        System.out.println("Actividad agregada.");
        gestionJSONActividad.guardarActividades(actividades);
    }

    public void baja() {
        System.out.println("=== ELIMINAR ACTIVIDAD ===");
        System.out.print("Tipo actividad a eliminar: ");
        String tipo = scanner.nextLine();
        Actividad toRemove = null;
        for (Actividad a : actividades) {
            if (a.getTipoActividad().equalsIgnoreCase(tipo)) {
                toRemove = a;
                break;
            }
        }
        if (toRemove != null) {
            actividades.remove(toRemove);
            System.out.println("Actividad eliminada.");
            gestionJSONActividad.guardarActividades(actividades);
        } else {
            System.out.println("No se encontró actividad con ese tipo.");
        }
    }

    public void modificacion() {
        System.out.println("=== MODIFICAR ACTIVIDAD ===");
        System.out.print("Tipo actividad a modificar: ");
        String tipo = scanner.nextLine();
        Actividad found = null;
        for (Actividad a : actividades) {
            if (a.getTipoActividad().equalsIgnoreCase(tipo)) {
                found = a;
                break;
            }
        }
        if (found != null) {
            System.out.print("Nueva capacidad (ENTER para mantener): ");
            String capStr = scanner.nextLine();
            if (!capStr.isBlank()) found.setCapacidadMaxima(Integer.parseInt(capStr));

            System.out.print("Nuevo precio (ENTER para mantener): ");
            String precioStr = scanner.nextLine();
            if (!precioStr.isBlank()) found.setPrecio(Double.parseDouble(precioStr));

            System.out.println("Actividad modificada.");
            gestionJSONActividad.guardarActividades(actividades);
        } else {
            System.out.println("Actividad no encontrada.");
        }
    }

    public void listar() {
        System.out.println("=== LISTA DE ACTIVIDADES ===");
        for (Actividad a : actividades) System.out.println(a);
    }
}
