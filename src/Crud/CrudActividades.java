package Crud;

import Modelos.Actividad;
import Gestores.GestorGenerico;
import java.util.Scanner;

public class CrudActividades extends GestorGenerico<Actividad> {

    private final Scanner scanner;

    public CrudActividades(Scanner scanner) {
        super();
        this.scanner = scanner; // reutiliza el scanner de SistemaDeTurnos
    }

    // === ALTA ===
    public void alta() {
        System.out.println("=== ALTA DE ACTIVIDAD ===");

        // 1️⃣ capacidadMaxima
        System.out.print("Capacidad máxima: ");
        int capacidad = Integer.parseInt(scanner.nextLine());

        // 2️⃣ precio
        System.out.print("Precio: ");
        double precio = Double.parseDouble(scanner.nextLine());

        // 3️⃣ tipoActividad
        System.out.print("Tipo de actividad: ");
        String tipo = scanner.nextLine();

        // 4️⃣ idActividad (autoincremental)
        int id = lista.size() + 1;

        // Crear actividad con setters
        Actividad nueva = new Actividad();
        nueva.setCapacidadMaxima(capacidad);
        nueva.setPrecio(precio);
        nueva.setTipoActividad(tipo);
        nueva.setIdActividad(id);

        agregar(nueva);

        System.out.println("✔ Actividad agregada correctamente.");
    }

    // === BAJA ===
    public void baja() {
        System.out.println("=== ELIMINAR ACTIVIDAD ===");
        System.out.print("Ingrese el ID de la actividad a eliminar: ");
        int id = Integer.parseInt(scanner.nextLine());

        Actividad eliminar = null;
        for (Actividad a : lista) {
            if (a.getIdActividad() == id) {
                eliminar = a;
                break;
            }
        }

        if (eliminar != null) {
            lista.remove(eliminar);
            System.out.println("✔ Actividad eliminada correctamente.");
        } else {
            System.out.println("⚠ No se encontró una actividad con ese ID.");
        }
    }

    // === MODIFICACIÓN ===
    public void modificacion() {
        System.out.println("=== MODIFICAR ACTIVIDAD ===");
        System.out.print("Ingrese el ID de la actividad: ");
        int id = Integer.parseInt(scanner.nextLine());

        for (Actividad a : lista) {
            if (a.getIdActividad() == id) {
                System.out.println("Actividad actual: " + a);

                System.out.print("Nueva capacidad (ENTER para mantener): ");
                String capStr = scanner.nextLine();
                if (!capStr.isBlank()) a.setCapacidadMaxima(Integer.parseInt(capStr));

                System.out.print("Nuevo precio (ENTER para mantener): ");
                String precioStr = scanner.nextLine();
                if (!precioStr.isBlank()) a.setPrecio(Double.parseDouble(precioStr));

                System.out.print("Nuevo tipo de actividad (ENTER para mantener): ");
                String tipoStr = scanner.nextLine();
                if (!tipoStr.isBlank()) a.setTipoActividad(tipoStr);

                System.out.println("✔ Actividad modificada correctamente.");
                return;
            }
        }

        System.out.println("⚠ No se encontró una actividad con ese ID.");
    }

    // === LISTAR ===
    public void listarActividades() {
        System.out.println("=== LISTA DE ACTIVIDADES ===");
        if (lista.isEmpty()) {
            System.out.println("⚠ No hay actividades registradas.");
            return;
        }
        for (Actividad a : lista) {
            System.out.println(a);
        }
    }
}
