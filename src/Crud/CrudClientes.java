package Crud;

import Gestores.GestorGenerico;
import Modelos.Cliente;
import Enum.TipoUsuario;
import java.util.Scanner;

public class CrudClientes extends GestorGenerico<Cliente> {

    private final Scanner scanner;

    public CrudClientes(Scanner scanner) {
        super();
        this.scanner = scanner;
    }

    public void alta() {
        System.out.println("=== ALTA DE CLIENTE ===");
        System.out.print("DNI: ");
        String dni = scanner.nextLine();

        for (Cliente c : lista) {
            if (c.getDni().equalsIgnoreCase(dni)) {
                System.out.println("Ya existe un cliente con ese DNI.");
                return;
            }
        }

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Apellido: ");
        String apellido = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Usuario: ");
        String usuario = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        System.out.print("Teléfono: ");
        String telefono = scanner.nextLine();

        Cliente nuevo = new Cliente(dni, nombre, apellido, email, password, usuario, true, telefono);
        nuevo.setTipo(TipoUsuario.CLIENTE);
        agregarPersona(nuevo);

        System.out.println("✔ Cliente agregado correctamente.");
    }

    public void baja() {
        System.out.println("=== BAJA LÓGICA DE CLIENTE ===");
        System.out.print("Ingrese DNI del cliente: ");
        String dni = scanner.nextLine();

        for (Cliente c : lista) {
            if (c.getDni().equalsIgnoreCase(dni)) {
                c.setEsActivo(false);
                System.out.println("✔ Cliente desactivado correctamente.");
                return;
            }
        }

        System.out.println("⚠ No se encontró un cliente con ese DNI.");
    }

    public void modificacion() {
        System.out.println("=== MODIFICAR CLIENTE ===");
        System.out.print("Ingrese DNI del cliente: ");
        String dni = scanner.nextLine();

        for (Cliente c : lista) {
            if (c.getDni().equalsIgnoreCase(dni)) {
                System.out.print("Nuevo email (ENTER para mantener): ");
                String email = scanner.nextLine();
                if (!email.isBlank()) c.setEmail(email);

                System.out.print("Nuevo teléfono (ENTER para mantener): ");
                String tel = scanner.nextLine();
                if (!tel.isBlank()) c.setTelefono(tel);

                System.out.println("✔ Cliente modificado correctamente.");
                return;
            }
        }
        System.out.println("⚠ Cliente no encontrado.");
    }

    public void listarClientes() {
        System.out.println("=== LISTA DE CLIENTES ===");
        for (Cliente c : lista) {
            System.out.println(c.getDni() + " - " + c.getNombre() + " " + c.getApellido() +
                    " - Activo: " + c.getEsActivo());
        }
    }
}
