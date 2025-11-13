package Core;

import Crud.CrudActividades;
import Crud.CrudClientes;
import Crud.CrudTurnos;
import Gestores.GestorActividades;
import Gestores.GestorClientes;
import Gestores.GestorTurnos;
import Modelos.Persona;
import Modelos.Actividad;
import ManejoJSON.gestionJSONActividad;
import ManejoJSON.gestionJSONPersona;
import Modelos.Turno;
import java.util.List;
import java.util.Scanner;
import Enum.EstadoTurno;

public class SistemaDeTurnos {

    private final Scanner scanner = new Scanner(System.in);

    private final GestorActividades gestorActividades;
    private final GestorClientes gestorClientes;
    private final GestorTurnos gestorTurnos;

    private final CrudActividades crudActividades;
    private final CrudClientes crudClientes;
    private final CrudTurnos crudTurnos;

    public SistemaDeTurnos() {
        this.gestorActividades = new GestorActividades();
        this.gestorClientes = new GestorClientes();
        this.gestorTurnos = new GestorTurnos(gestorClientes, gestorActividades);

        cargarActividadesDesdeJson();
        cargarPersonasDesdeJson();

        this.crudActividades = new CrudActividades(scanner, gestorActividades);
        this.crudClientes = new CrudClientes(scanner, gestorClientes);
        this.crudTurnos = new CrudTurnos(scanner, gestorTurnos, gestorClientes, gestorActividades);
    }

    private void cargarActividadesDesdeJson() {
        try {
            List<Actividad> actividades = gestionJSONActividad.leerActividades();
            if (actividades != null && !actividades.isEmpty()) {
                gestorActividades.getLista().addAll(actividades);
                System.out.println("Cargadas " + actividades.size() + " actividades desde JSON.");
            }
        } catch (Exception e) {
            System.out.println("No se pudieron cargar actividades desde JSON: " + e.getMessage());
        }
    }

    private void cargarPersonasDesdeJson() {
        try {
            List<Persona> personas = gestionJSONPersona.leerPersonas();
            if (personas != null && !personas.isEmpty()) {
                gestorClientes.getLista().addAll(personas);
                System.out.println("Cargadas " + personas.size() + " personas desde JSON.");
            }
        } catch (Exception e) {
            System.out.println("No se pudieron cargar personas desde JSON: " + e.getMessage());
        }
    }

    public void iniciar() {
        int opcion;
        do {
            System.out.println("\n=== SISTEMA DE TURNOS ===");
            System.out.println("1 - Iniciar sesión");
            System.out.println("2 - Registrarse");
            System.out.println("3 - Salir");
            System.out.print("Opción: ");

            String linea = scanner.nextLine().trim();
            opcion = linea.isEmpty() ? -1 : Integer.parseInt(linea);

            switch (opcion) {
                case 1 -> iniciarSesion();
                case 2 -> registrarYEntrarCliente();
                case 3 -> System.out.println("Saliendo del sistema...");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 3);
    }

    private void iniciarSesion() {
        System.out.println("\n=== INICIO DE SESIÓN ===");
        System.out.print("Usuario: ");
        String usuario = scanner.nextLine().trim();
        System.out.print("Contraseña: ");
        String pass = scanner.nextLine().trim();

        Persona encontrado = null;
        for (Persona p : gestorClientes.getLista()) {
            if (p.getUsuario() != null && p.getUsuario().equalsIgnoreCase(usuario)) {
                encontrado = p;
                break;
            }
        }

        if (encontrado == null) {
            System.out.println("Usuario no encontrado.");
            return;
        }
        if (!encontrado.getEsActivo()) {
            System.out.println("Usuario desactivado.");
            return;
        }
        if (!encontrado.getPassword().equals(pass)) {
            System.out.println("Contraseña incorrecta.");
            return;
        }

        if (encontrado.getTipo() != null && encontrado.getTipo().name().equalsIgnoreCase("ADMIN")) {
            int nivelAcceso = 1;
            if (encontrado instanceof Modelos.Administrador) {
                nivelAcceso = ((Modelos.Administrador) encontrado).getNivelAcceso();
            }
            MenuAdmin menuAdmin = new MenuAdmin(scanner, crudClientes, crudActividades, crudTurnos, nivelAcceso);
            menuAdmin.run();
        } else {
            MenuCliente menuCliente = new MenuCliente(scanner, crudTurnos, (Modelos.Cliente) encontrado, gestorTurnos, gestorActividades);
            menuCliente.run();
        }
    }

    private void registrarYEntrarCliente() {
        System.out.println("\n=== REGISTRO DE NUEVO CLIENTE ===");
        crudClientes.alta();

        if (gestorClientes.getLista().isEmpty()) {
            System.out.println("No se pudo crear el cliente.");
            return;
        }

        Modelos.Cliente nuevo = null;
        for (Persona p : gestorClientes.getLista()) {
            if (p instanceof Modelos.Cliente)
                nuevo = (Modelos.Cliente) p;
        }

        if (nuevo != null) {
            System.out.println("\nRegistro exitoso. Iniciando sesión automáticamente...");
            MenuCliente menuCliente = new MenuCliente(scanner, crudTurnos, nuevo, gestorTurnos, gestorActividades);
            menuCliente.run();
        } else {
            System.out.println("Error: no se encontró el nuevo cliente para iniciar sesión.");
        }
    }

    private static class MenuAdmin {
        private final Scanner scanner;
        private final CrudClientes crudClientes;
        private final CrudActividades crudActividades;
        private final CrudTurnos crudTurnos;
        private final int nivelAcceso;

        MenuAdmin(Scanner scanner, CrudClientes cc, CrudActividades ca, CrudTurnos ct, int nivelAcceso) {
            this.scanner = scanner;
            this.crudClientes = cc;
            this.crudActividades = ca;
            this.crudTurnos = ct;
            this.nivelAcceso = nivelAcceso;
        }

        void run() {
            int opcion;
            do {
                System.out.println("\n=== PANEL ADMINISTRADOR (Nivel " + nivelAcceso + ") ===");
                System.out.println("1 - Alta de cliente");
                System.out.println("2 - Baja de cliente");
                System.out.println("3 - Modificación de cliente");
                System.out.println("4 - Listar clientes");
                System.out.println("5 - Alta de actividad");
                System.out.println("6 - Modificación de actividad");
                if (nivelAcceso == 2) System.out.println("7 - Baja de actividad");
                System.out.println("8 - Listar actividades");
                System.out.println("9 - Reservar turno");
                System.out.println("10 - Cancelar turno");
                System.out.println("11 - Listar turnos");
                System.out.println("12 - Filtrar turnos por cliente");
                System.out.println("13 - Ver actividades con turnos disponibles");
                System.out.println("14 - Crear nuevo administrador");
                System.out.println("0 - Cerrar sesión");
                System.out.print("Opción: ");
                String linea = scanner.nextLine().trim();
                opcion = linea.isEmpty() ? -1 : Integer.parseInt(linea);

                switch (opcion) {
                    case 1 -> crudClientes.alta();
                    case 2 -> crudClientes.baja();
                    case 3 -> crudClientes.modificacion();
                    case 4 -> crudClientes.listarClientes();
                    case 5 -> crudActividades.alta();
                    case 6 -> crudActividades.modificacion();
                    case 7 -> {
                        if (nivelAcceso == 2) crudActividades.baja();
                        else System.out.println("No tiene permiso para eliminar actividades.");
                    }
                    case 8 -> crudActividades.listarActividades();
                    case 9 -> crudTurnos.alta();
                    case 10 -> {
                        if (nivelAcceso == 2) crudTurnos.cancelar();
                        else System.out.println("Solo administradores nivel 2 pueden cancelar turnos ajenos.");
                    }
                    case 11 -> crudTurnos.listarTurnos();
                    case 12 -> {
                        System.out.print("Ingrese DNI del cliente: ");
                        String dni = scanner.nextLine();
                        List<Turno> turnosCliente = GestorTurnos.turnosPorCliente(crudTurnos.getGestorTurnos().getLista(), dni);
                        if (turnosCliente.isEmpty()) {
                            System.out.println("No se encontraron turnos para el cliente con DNI: " + dni);
                        } else {
                            System.out.println("Turnos del cliente " + dni + ":");
                            for (Turno t : turnosCliente) {
                                System.out.println("ID: " + t.getIdTurno() + " | Fecha: " + t.getFechaHora() + " | Estado: " + t.getEstado());
                            }
                        }
                    }
                    case 13 -> {
                        List<Actividad> disponibles = GestorTurnos.listarTurnosDisponibles(
                                crudTurnos.getGestorActividades().getLista(),
                                crudTurnos.getGestorTurnos().getLista()
                        );
                        if (disponibles.isEmpty()) {
                            System.out.println("No hay actividades con cupos disponibles.");
                        } else {
                            System.out.println("Actividades con turnos disponibles:");
                            for (Actividad a : disponibles) {
                                System.out.println(a.getTipoActividad() + " (Cupos totales: " + a.getCapacidadMaxima() + ")");
                            }
                        }
                    }

                    // 🔹 CAMBIO: ahora cualquier admin puede crear otros admins
                    case 14 -> {
                        System.out.println("\n=== CREAR NUEVO ADMINISTRADOR ===");
                        System.out.print("DNI: ");
                        String dni = scanner.nextLine().trim();
                        System.out.print("Nombre: ");
                        String nombre = scanner.nextLine().trim();
                        System.out.print("Apellido: ");
                        String apellido = scanner.nextLine().trim();
                        System.out.print("Email: ");
                        String email = scanner.nextLine().trim();
                        System.out.print("Usuario: ");
                        String usuario = scanner.nextLine().trim();
                        System.out.print("Contraseña: ");
                        String password = scanner.nextLine().trim();

                        int nivel;
                        try {
                            System.out.print("Nivel de acceso (1 o 2): ");
                            nivel = Integer.parseInt(scanner.nextLine());
                            if (nivel != 1 && nivel != 2) {
                                System.out.println("Error: el nivel de acceso debe ser 1 o 2.");
                                break;
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Error: ingrese un número válido (1 o 2).");
                            break;
                        }

                        Modelos.Administrador nuevoAdmin = new Modelos.Administrador(
                                dni, nombre, apellido, email, password, usuario, true, nivel
                        );

                        crudClientes.getGestor().getLista().add(nuevoAdmin);
                        try {
                            ManejoJSON.gestionJSONPersona.grabarPersonas(crudClientes.getGestor().getLista());
                            System.out.println("Administrador creado exitosamente.");
                        } catch (Exception e) {
                            System.out.println("Administrador creado, pero falló al guardar en JSON: " + e.getMessage());
                        }
                    }

                    case 0 -> System.out.println("Cerrando sesión de administrador...");
                    default -> System.out.println("Opción inválida.");
                }

            } while (opcion != 0);
        }
    }

    private static class MenuCliente {
        private final Scanner scanner;
        private final CrudTurnos crudTurnos;
        private final Modelos.Cliente cliente;
        private final GestorTurnos gestorTurnos;
        private final GestorActividades gestorActividades;

        MenuCliente(Scanner scanner, CrudTurnos ct, Modelos.Cliente cliente, GestorTurnos gestorTurnos, GestorActividades gestorActividades) {
            this.scanner = scanner;
            this.crudTurnos = ct;
            this.cliente = cliente;
            this.gestorTurnos = gestorTurnos;
            this.gestorActividades = gestorActividades;
        }

        void run() {
            int opcion;
            do {
                System.out.println("\n=== PANEL CLIENTE (" + cliente.getNombre() + " " + cliente.getApellido() + ") ===");
                System.out.println("1 - Ver mis turnos");
                System.out.println("2 - Reservar turno");
                System.out.println("3 - Cancelar mi turno");
                System.out.println("4 - Desactivar mi cuenta");
                System.out.println("5 - Ver actividades con turnos disponibles");
                System.out.println("0 - Cerrar sesión");
                System.out.print("Opción: ");
                String linea = scanner.nextLine().trim();
                opcion = linea.isEmpty() ? -1 : Integer.parseInt(linea);

                switch (opcion) {
                    case 1 -> {
                        System.out.println("Turnos del cliente:");
                        for (Turno t : crudTurnos.getGestorTurnos().getLista()) {
                            if (t.getDniCliente() != null && t.getDniCliente().equalsIgnoreCase(cliente.getDni())) {
                                System.out.println("ID:" + t.getIdTurno() + " | Fecha:" + t.getFechaHora() + " | Estado:" + t.getEstado());
                            }
                        }
                    }
                    case 2 -> crudTurnos.alta();
                    case 3 -> {
                        System.out.print("ID del turno a cancelar: ");
                        int id = Integer.parseInt(scanner.nextLine());
                        boolean ok = false;

                        for (Turno t : crudTurnos.getGestorTurnos().getLista()) {
                            if (t.getIdTurno() == id && t.getDniCliente() != null && t.getDniCliente().equalsIgnoreCase(cliente.getDni())) {
                                t.setEstado(EstadoTurno.CANCELADO);
                                try {
                                    ManejoJSON.gestionJSONTurnos.grabarTurnos(crudTurnos.getGestorTurnos().getLista());
                                } catch (Exception ex) {
                                    System.out.println("Turno cancelado en memoria, pero falló persistencia: " + ex.getMessage());
                                }
                                System.out.println("Turno cancelado.");
                                ok = true;
                                break;
                            }
                        }

                        if (!ok) System.out.println("Turno no encontrado o no te pertenece.");
                    }
                    case 4 -> {
                        cliente.setEsActivo(false);
                        try {
                            ManejoJSON.gestionJSONPersona.grabarPersonas(crudTurnos.getGestorTurnos().gestor.getLista());
                        } catch (Exception ignored) {}
                        System.out.println("Cuenta desactivada.");
                        opcion = 0;
                    }
                    case 5 -> {
                        List<Actividad> disponibles = GestorTurnos.listarTurnosDisponibles(
                                gestorActividades.getLista(),
                                gestorTurnos.getLista()
                        );
                        if (disponibles.isEmpty()) {
                            System.out.println("No hay actividades con turnos disponibles.");
                        } else {
                            System.out.println("Actividades con cupos disponibles:");
                            for (Actividad a : disponibles) {
                                System.out.println(a.getTipoActividad() + " (Cupos totales: " + a.getCapacidadMaxima() + ")");
                            }
                        }
                    }
                    case 0 -> System.out.println("Cerrando sesión...");
                    default -> System.out.println("Opción inválida.");
                }

            } while (opcion != 0);
        }
    }

    public CrudClientes getCrudClientes() { return crudClientes; }
    public CrudActividades getCrudActividades() { return crudActividades; }
    public CrudTurnos getCrudTurnos() { return crudTurnos; }
}
