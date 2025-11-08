package Crud;

import Modelos.Cliente;
import Modelos.GestorClientes;
import Enum.TipoUsuario;
import Exceptions.*;
import ManejoJSON.gestionJSONPersona;
import java.util.Scanner;

public class CrudClientes {
    private GestorClientes gestor;
    private Scanner scanner;

    public CrudClientes(GestorClientes gestor) {
        this.gestor = gestor;
        this.scanner = new Scanner(System.in);
    }

    public void alta() {
        System.out.println("=== ALTA DE CLIENTE ===");
        System.out.print("DNI: ");
        String dni = scanner.nextLine();
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Apellido: ");
        String apellido = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Usuario: ");
        String usuario = scanner.nextLine();
        System.out.print("Password: ");
        String pass = scanner.nextLine();
        System.out.print("Teléfono: ");
        String telefono = scanner.nextLine();

        try {
            Cliente nuevo = new Cliente(dni, nombre, apellido, email, pass, usuario, telefono);
            nuevo.setTipo(TipoUsuario.CLIENTE);
            nuevo.setEsActivo(true);
            gestor.agregarPersona(nuevo);
            // persistir
            gestionJSONPersona.guardarClientes(gestor.getLista());
        } catch (DNIClienteDuplicadoException e) {
            System.out.println(e.getMessage());
        }
    }

    public void baja() {
        System.out.println("=== BAJA DE CLIENTE (baja lógica) ===");
        System.out.print("Ingrese DNI del cliente: ");
        String dni = scanner.nextLine();

        try {
            Cliente c = gestor.buscarPorDni(dni);
            c.setEsActivo(false); // baja lógica
            System.out.println("Cliente desactivado correctamente.");
            gestionJSONPersona.guardarClientes(gestor.getLista());
        } catch (ClienteNoEncontradoException e) {
            System.out.println(e.getMessage());
        }
    }

    public void modificacion() {
        System.out.println("=== MODIFICAR CLIENTE ===");
        System.out.print("DNI del cliente a modificar: ");
        String dni = scanner.nextLine();

        try {
            Cliente c = gestor.buscarPorDni(dni);
            System.out.print("Nuevo email (ENTER para mantener): ");
            String nuevoEmail = scanner.nextLine();
            if (!nuevoEmail.isBlank()) c.setEmail(nuevoEmail);

            System.out.print("Nuevo teléfono (ENTER para mantener): ");
            String nuevoTel = scanner.nextLine();
            if (!nuevoTel.isBlank()) c.setTelefono(nuevoTel);

            System.out.println("Cliente modificado con éxito.");
            gestionJSONPersona.guardarClientes(gestor.getLista());
        } catch (ClienteNoEncontradoException e) {
            System.out.println(e.getMessage());
        }
    }

    public void listar() {
        System.out.println("=== LISTA DE CLIENTES ===");
        for (Cliente c : gestor.getLista()) {
            System.out.println(c.getDni() + " - " + c.getNombre() + " " + c.getApellido() + " - Activo: " + c.getEstado());
        }
    }
}
