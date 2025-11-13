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
import ManejoJSON.gestionJSONTurnos;
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
        // crear gestores
        this.gestorActividades = new GestorActividades();
        this.gestorClientes = new GestorClientes();
        this.gestorTurnos = new GestorTurnos(gestorClientes, gestorActividades);

        // intentar cargar datos desde JSON si existen los mappers
        cargarActividadesDesdeJson();
        cargarPersonasDesdeJson();
        //cargarTurnosDesdeJson(); // <-- AÑADIDO: carga los turnos en memoria al arrancar

        // crear CRUDs que usan los gestores
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

    // NUEVO: carga turnos desde src/data/turnos.json
    private void cargarTurnosDesdeJson() {
        try {
            List<Turno> turnos = gestionJSONTurnos.leerTurnos();
            if (turnos != null && !turnos.isEmpty()) {
                gestorTurnos.getLista().addAll(turnos);
                System.out.println("Cargados " + turnos.size() + " turnos desde JSON.");
            }
        } catch (Exception e) {
            System.out.println("No se pudieron cargar turnos desde JSON: " + e.getMessage());
        }
    }

    // Inicio principal: login simplificado por usuario/clave desde persona.json
    public void iniciar() {
        System.out.println("=== SISTEMA DE TURNOS ===");

        // loop principal: pedir usuario/clave y entrar en menu adecuado
        System.out.print("Usuario: ");
        String usuario = scanner.nextLine().trim();
        System.out.print("Contraseña: ");
        String pass = scanner.nextLine().trim();

        // busco persona en gestorClientes (que tiene la lista cargada desde JSON)
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

        // si es admin o cliente según tipo
        if (encontrado.getTipo() != null && encontrado.getTipo().name().equalsIgnoreCase("ADMIN")) {
            int nivelAcceso = 1;
            if (encontrado instanceof Modelos.Administrador) {
                nivelAcceso = ((Modelos.Administrador) encontrado).getNivelAcceso();
            }
            MenuAdmin menuAdmin = new MenuAdmin(scanner, crudClientes, crudActividades, crudTurnos, nivelAcceso);
            menuAdmin.run();
        } else {
            // cliente: crear menú cliente
            MenuCliente menuCliente = new MenuCliente(scanner, crudTurnos, (Modelos.Cliente) encontrado);
            menuCliente.run();
        }
    }

    // Menús internos
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

        MenuCliente(Scanner scanner, CrudTurnos ct, Modelos.Cliente cliente) {
            this.scanner = scanner;
            this.crudTurnos = ct;
            this.cliente = cliente;
        }

        void run() {
            int opcion;
            do {
                System.out.println("\n=== PANEL CLIENTE (" + cliente.getNombre() + " " + cliente.getApellido() + ") ===");
                System.out.println("1 - Ver mis turnos");
                System.out.println("2 - Reservar turno");
                System.out.println("3 - Cancelar mi turno");
                System.out.println("4 - Desactivar mi cuenta");
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
                                t.setEstado(EstadoTurno.CANCELADO); // si usás la import, reemplazar por EstadoTurno.CANCELADO
                                // Persistir inmediatamente
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
                        // persistir personas también
                        try {
                            ManejoJSON.gestionJSONPersona.grabarPersonas(crudTurnos.getGestorTurnos().gestor.getLista());
                        } catch (Exception ignored) {}
                        System.out.println("Cuenta desactivada.");
                        opcion = 0;
                    }
                    case 0 -> System.out.println("Cerrando sesión...");
                    default -> System.out.println("Opción inválida.");
                }

            } while (opcion != 0);
        }
    }

    // getters
    public CrudClientes getCrudClientes() { return crudClientes; }
    public CrudActividades getCrudActividades() { return crudActividades; }
    public CrudTurnos getCrudTurnos() { return crudTurnos; }
}
