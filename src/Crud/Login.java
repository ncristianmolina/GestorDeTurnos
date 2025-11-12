package Crud;

import java.util.Scanner;
import Modelos.*;
import Enum.TipoUsuario;

public class Login {

    private final Scanner scanner;
    private final CrudClientes crudClientes;
    private final CrudActividades crudActividades;
    private final CrudTurnos crudTurnos;

    public Login(Scanner scanner, CrudClientes crudClientes, CrudActividades crudActividades, CrudTurnos crudTurnos) {
        this.scanner = scanner;
        this.crudClientes = crudClientes;
        this.crudActividades = crudActividades;
        this.crudTurnos = crudTurnos;
    }

    public void iniciar() {
        System.out.println("=== INICIO DE SESIÓN ===");
        System.out.print("Usuario: ");
        String usuario = scanner.nextLine();
        System.out.print("Contraseña: ");
        String pass = scanner.nextLine();

        if (usuario.equalsIgnoreCase("admin")) {
            Administrador admin = new Administrador("1", "Admin", "Root", "admin@mail.com", "123", "admin", true, 2);
            menuAdministrador(admin);
        } else {
            Cliente cliente = new Cliente("123", "Juan", "Pérez", "juan@mail.com", "123", usuario, true, "223456789");
            menuCliente(cliente);
        }
    }

    private void menuAdministrador(Administrador admin) {
        int opcion;
        do {
            System.out.println("\n=== PANEL ADMINISTRADOR (Nivel " + admin.getNivelAcceso() + ") ===");
            System.out.println("1️⃣ Alta de cliente");
            System.out.println("2️⃣ Baja de cliente");
            System.out.println("3️⃣ Modificación de cliente");
            System.out.println("4️⃣ Listar clientes");
            System.out.println("5️⃣ Alta de actividad");
            System.out.println("6️⃣ Modificación de actividad");
            if (admin.getNivelAcceso() == 2) System.out.println("7️⃣ Baja de actividad");
            System.out.println("8️⃣ Listar actividades");
            System.out.println("9️⃣ Reservar turno");
            System.out.println("🔟 Cancelar turno");
            System.out.println("11️⃣ Listar turnos");
            System.out.println("0️⃣ Salir");
            System.out.print("Opción: ");
            opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion) {
                case 1 -> crudClientes.alta();
                case 2 -> crudClientes.baja();
                case 3 -> crudClientes.modificacion();
                case 4 -> crudClientes.listarClientes();
                case 5 -> crudActividades.alta();
                case 6 -> crudActividades.modificacion();
                case 7 -> { if (admin.getNivelAcceso() == 2) crudActividades.baja(); }
                case 8 -> crudActividades.listarActividades();
                case 9 -> crudTurnos.alta();
                case 10 -> crudTurnos.cancelar();
                case 11 -> crudTurnos.listarTurnos();
                case 0 -> System.out.println("👋 Cerrando sesión...");
                default -> System.out.println("⚠ Opción inválida.");
            }
        } while (opcion != 0);
    }

    private void menuCliente(Cliente cliente) {
        int opcion;
        do {
            System.out.println("\n=== PANEL CLIENTE ===");
            System.out.println("1️⃣ Ver mis turnos");
            System.out.println("2️⃣ Reservar turno");
            System.out.println("3️⃣ Cancelar turno");
            System.out.println("4️⃣ Desactivar cuenta");
            System.out.println("0️⃣ Salir");
            System.out.print("Opción: ");
            opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion) {
                case 1 -> crudTurnos.listarTurnos();
                case 2 -> crudTurnos.alta();
                case 3 -> crudTurnos.cancelar();
                case 4 -> {
                    cliente.setEsActivo(false);
                    System.out.println("✔ Cuenta desactivada.");
                    opcion = 0;
                }
                case 0 -> System.out.println("👋 Saliendo...");
                default -> System.out.println("⚠ Opción inválida.");
            }
        } while (opcion != 0);
    }
}
