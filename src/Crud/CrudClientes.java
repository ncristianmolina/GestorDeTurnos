package Crud;

import Gestores.GestorClientes;
import ManejoJSON.gestionJSONPersona;
import Modelos.Cliente;
import Modelos.Persona;
import Enum.TipoUsuario;
import Exceptions.DNIClienteDuplicadoException;
import Exceptions.ClienteNoEncontradoException;

import java.util.Scanner;

public class CrudClientes {

    private final Scanner scanner;
    private final GestorClientes gestor;

    public CrudClientes(Scanner scanner, GestorClientes gestor) {
        this.scanner = scanner;
        this.gestor = gestor;
    }

    // === ALTA DE CLIENTE ===
    public void alta() {
        System.out.println("=== ALTA DE CLIENTE ===");
        System.out.print("DNI: ");
        String dni = scanner.nextLine();

        // chequeo duplicado
        for (Persona p : gestor.getLista()) {
            if (p.getDni() != null && p.getDni().equalsIgnoreCase(dni)) {
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

        try {
            gestor.agregarPersona(nuevo);
            // persistimos la lista actualizada
            gestionJSONPersona.grabarPersonas(gestor.getLista());
            System.out.println("Cliente agregado y guardado correctamente.");
        } catch (DNIClienteDuplicadoException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // === BAJA LÓGICA ===
    public void baja() {
        System.out.println("=== BAJA LÓGICA DE CLIENTE ===");
        System.out.print("Ingrese DNI del cliente: ");
        String dni = scanner.nextLine();

        try {
            Persona p = gestor.buscarPorDni(dni);
            if (p instanceof Cliente) {
                Cliente c = (Cliente) p;
                c.setEsActivo(false);
                gestionJSONPersona.grabarPersonas(gestor.getLista());
                System.out.println("Cliente desactivado y guardado correctamente.");
            } else {
                System.out.println("La persona encontrada no es un cliente.");
            }
        } catch (ClienteNoEncontradoException e) {
            System.out.println(e.getMessage());
        }
    }

    // === MODIFICACIÓN ===
    public void modificacion() {
        System.out.println("=== MODIFICAR CLIENTE ===");
        System.out.print("Ingrese DNI del cliente: ");
        String dni = scanner.nextLine();

        try {
            Persona p = gestor.buscarPorDni(dni);
            if (!(p instanceof Cliente)) {
                System.out.println("La persona encontrada no es un cliente.");
                return;
            }

            Cliente c = (Cliente) p;

            System.out.print("Nuevo email (ENTER para mantener): ");
            String email = scanner.nextLine();
            if (!email.isBlank()) c.setEmail(email);

            System.out.print("Nuevo teléfono (ENTER para mantener): ");
            String tel = scanner.nextLine();
            if (!tel.isBlank()) c.setTelefono(tel);

            // persistimos los cambios
            gestionJSONPersona.grabarPersonas(gestor.getLista());
            System.out.println("Cliente modificado y guardado correctamente.");
        } catch (ClienteNoEncontradoException e) {
            System.out.println(e.getMessage());
        }
    }

    // === LISTAR ===
    public void listarClientes() {
        System.out.println("=== LISTA DE CLIENTES ===");
        if (gestor.getLista().isEmpty()) {
            System.out.println("No hay clientes registrados.");
            return;
        }

        for (Persona p : gestor.getLista()) {
            if (p instanceof Cliente) {
                Cliente c = (Cliente) p;
                System.out.println(c.getDni() + " - " + c.getNombre() + " " + c.getApellido()
                        + " - Activo: " + c.getEsActivo());
            }
        }
    }

    public GestorClientes getGestor() {
        return gestor;
    }

}
