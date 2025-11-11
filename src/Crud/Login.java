package Crud;

import Gestores.GestorTurnos;
import Gestores.GestorClientes;
import java.util.Scanner;

/**
 * Clase Login
 * Controla el flujo principal del sistema de turnos.
 * Desde aquí se accede a los distintos CRUDs (Clientes, Actividades y Turnos).
 *
 * @author Cristian
 */
public class Login {

    private GestorClientes gestorClientes;
    private GestorTurnos gestorTurnos;
    private CrudClientes crudClientes;
    private CrudActividades crudActividades;
    private CrudTurnos crudTurnos;

    public Login() {
        // Inicializamos los gestores y CRUDs
        this.gestorClientes = new GestorClientes();
        this.gestorTurnos = new GestorTurnos();
        this.crudActividades = new CrudActividades();
        this.crudClientes = new CrudClientes(gestorClientes);
        this.crudTurnos = new CrudTurnos(gestorTurnos, gestorClientes, crudActividades);
    }

    /**
     * Método principal del menú de opciones del sistema.
     */
    public void iniciar() {
        Scanner sc = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n===================================");
            System.out.println("     🕒 SISTEMA DE TURNOS CRUD");
            System.out.println("===================================");
            System.out.println("1️⃣  - Alta de Cliente");
            System.out.println("2️⃣  - Baja de Cliente");
            System.out.println("3️⃣  - Modificación de Cliente");
            System.out.println("4️⃣  - Listar Clientes");
            System.out.println("-----------------------------------");
            System.out.println("5️⃣  - Alta de Actividad");
            System.out.println("6️⃣  - Modificación de Actividad");
            System.out.println("7️⃣  - Eliminación de Actividad");
            System.out.println("8️⃣  - Listar Actividades");
            System.out.println("-----------------------------------");
            System.out.println("9️⃣  - Reservar Turno");
            System.out.println("🔟  - Cancelar Turno");
            System.out.println("11️⃣ - Modificar Turno");
            System.out.println("12️⃣ - Listar Turnos");
            System.out.println("-----------------------------------");
            System.out.println("0️⃣  - Salir del sistema");
            System.out.println("===================================");
            System.out.print("Seleccione una opción: ");

            // Evitar errores por caracteres
            while (!sc.hasNextInt()) {
                System.out.print("Por favor, ingrese un número válido: ");
                sc.next();
            }
            opcion = sc.nextInt();
            sc.nextLine(); // limpiar buffer

            switch (opcion) {
                case 1 -> crudClientes.alta();
                case 2 -> crudClientes.baja();
                case 3 -> crudClientes.modificacion();
                case 4 -> crudClientes.listar();

                case 5 -> crudActividades.alta();
                case 6 -> crudActividades.modificacion();
                case 7 -> crudActividades.baja();
                case 8 -> crudActividades.listar();

                case 9 -> crudTurnos.alta();
                case 10 -> crudTurnos.cancelar();
                case 11 -> crudTurnos.modificacion();
                case 12 -> crudTurnos.listar();

                case 0 -> System.out.println("👋 Cerrando sesión... ¡Hasta luego!");
                default -> System.out.println("⚠️  Opción inválida, intente nuevamente.");
            }

        } while (opcion != 0);

        sc.close();
    }
}
