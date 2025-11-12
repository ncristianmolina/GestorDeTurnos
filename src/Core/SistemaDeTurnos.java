package Core;

import Crud.*;
import Modelos.*;
import Enum.EstadoTurno;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class SistemaDeTurnos {

    private final Scanner scanner = new Scanner(System.in);
    private final CrudClientes crudClientes;
    private final CrudActividades crudActividades;
    private final CrudTurnos crudTurnos;

    // Archivo donde se guardan los usuarios (personas)
    private static final String PERSONA_JSON = "src/data/persona.json";

    public SistemaDeTurnos() {
        this.crudClientes = new CrudClientes(scanner);
        this.crudActividades = new CrudActividades(scanner);
        this.crudTurnos = new CrudTurnos(scanner, crudClientes, crudActividades);
    }

    public void iniciar() {
        System.out.println("=== INICIO DE SESIÓN ===");

        JSONObject personasJson = cargarPersonasJson();
        if (personasJson == null) {
            System.out.println("No se pudo leer persona.json. Compruebe el archivo.");
            return;
        }

        System.out.print("Usuario: ");
        String usuario = scanner.nextLine().trim();
        System.out.print("Contraseña: ");
        String pass = scanner.nextLine().trim();

        JSONArray personas = personasJson.optJSONArray("personas");
        if (personas == null) {
            System.out.println("persona.json no contiene el array 'personas'.");
            return;
        }

        JSONObject personaEncontrada = null;
        for (int i = 0; i < personas.length(); i++) {
            JSONObject p = personas.optJSONObject(i);
            if (p != null && p.optString("usuario", "").equalsIgnoreCase(usuario)) {
                personaEncontrada = p;
                break;
            }
        }

        if (personaEncontrada == null) {
            System.out.println("Usuario no encontrado.");
            return;
        }

        if (!personaEncontrada.optBoolean("esActivo", false)) {
            System.out.println("Usuario desactivado. Contacte al administrador.");
            return;
        }

        if (!personaEncontrada.optString("password", "").equals(pass)) {
            System.out.println("Contraseña incorrecta.");
            return;
        }

        String tipo = personaEncontrada.optString("tipo", "CLIENTE").toUpperCase();
        String dni = personaEncontrada.optString("dni", "");
        String nombre = personaEncontrada.optString("nombre", "");
        String apellido = personaEncontrada.optString("apellido", "");
        String email = personaEncontrada.optString("email", "");
        String usuarioJson = personaEncontrada.optString("usuario", "");

        if (tipo.equals("ADMIN")) {
            int nivelAcceso = personaEncontrada.has("nivelAcceso")
                    ? personaEncontrada.optInt("nivelAcceso", 1)
                    : 1;

            Administrador admin = new Administrador(dni, nombre, apellido, email, pass, usuarioJson, true, nivelAcceso);
            System.out.println("Bienvenido Administrador " + nombre + " " + apellido + " (Nivel " + nivelAcceso + ")");
            menuAdministrador(admin);
        } else {
            Cliente cliente = new Cliente(dni, nombre, apellido, email, pass, usuarioJson, true, "");
            crudClientes.agregar(cliente);
            System.out.println("Bienvenido " + nombre + " " + apellido);
            menuCliente(cliente);
        }
    }

    private JSONObject cargarPersonasJson() {
        try {
            String contenido = new String(Files.readAllBytes(Paths.get(PERSONA_JSON)));
            String texto = contenido.strip();
            if (texto.startsWith("[")) {
                JSONObject root = new JSONObject();
                root.put("personas", new JSONArray(texto));
                return root;
            } else {
                return new JSONObject(texto);
            }
        } catch (IOException | JSONException e) {
            System.out.println("Error al leer o parsear persona.json: " + e.getMessage());
            return null;
        }
    }

    private void menuAdministrador(Administrador admin) {
        int opcion;
        do {
            System.out.println("\n=== PANEL ADMINISTRADOR (Nivel " + admin.getNivelAcceso() + ") ===");
            System.out.println("1 - Alta de cliente");
            System.out.println("2 - Baja de cliente (desactivar)");
            System.out.println("3 - Modificación de cliente");
            System.out.println("4 - Listar clientes");
            System.out.println("5 - Alta de actividad");
            System.out.println("6 - Modificación de actividad");
            if (admin.getNivelAcceso() == 2)
                System.out.println("7 - Baja de actividad (eliminar)");
            System.out.println("8 - Listar actividades");
            System.out.println("9 - Reservar turno");
            System.out.println("10 - Cancelar turno");
            System.out.println("11 - Listar turnos");
            System.out.println("0 - Cerrar sesión");
            System.out.print("Opción: ");
            String linea = scanner.nextLine().trim();
            opcion = linea.isEmpty() ? -1 : Integer.parseInt(linea);

            switch (opcion) {
                case 1:
                    crudClientes.alta();
                    break;
                case 2:
                    crudClientes.baja();
                    break;
                case 3:
                    crudClientes.modificacion();
                    break;
                case 4:
                    crudClientes.listarClientes();
                    break;
                case 5:
                    crudActividades.alta();
                    break;
                case 6:
                    crudActividades.modificacion();
                    break;
                case 7:
                    if (admin.getNivelAcceso() == 2) {
                        crudActividades.baja();
                    } else {
                        System.out.println("No tiene permiso para eliminar actividades.");
                    }
                    break;
                case 8:
                    crudActividades.listarActividades();
                    break;
                case 9:
                    crudTurnos.alta();
                    break;
                case 10:
                    if (admin.getNivelAcceso() == 2) {
                        crudTurnos.cancelar();
                    } else {
                        System.out.println("Solo los administradores de nivel 2 pueden cancelar turnos ajenos.");
                    }
                    break;
                case 11:
                    crudTurnos.listarTurnos();
                    break;
                case 0:
                    System.out.println("Cerrando sesión de administrador...");
                    break;
                default:
                    System.out.println("Opción inválida.");
                    break;
            }
        } while (opcion != 0);
    }

    private void menuCliente(Cliente cliente) {
        int opcion;
        do {
            System.out.println("\n=== PANEL CLIENTE (" + cliente.getNombre() + " " + cliente.getApellido() + ") ===");
            System.out.println("1 - Ver mis turnos");
            System.out.println("2 - Reservar turno");
            System.out.println("3 - Cancelar turno propio");
            System.out.println("4 - Desactivar mi cuenta");
            System.out.println("0 - Cerrar sesión");
            System.out.print("Opción: ");
            String linea = scanner.nextLine().trim();
            opcion = linea.isEmpty() ? -1 : Integer.parseInt(linea);

            switch (opcion) {
                case 1:
                    listarTurnosDelCliente(cliente.getDni());
                    break;
                case 2:
                    crudTurnos.alta();
                    break;
                case 3:
                    cancelarTurnoDelCliente(cliente);
                    break;
                case 4:
                    cliente.setEsActivo(false);
                    System.out.println("Cuenta desactivada. Se cerrará sesión.");
                    opcion = 0;
                    break;
                case 0:
                    System.out.println("Cerrando sesión...");
                    break;
                default:
                    System.out.println("Opción inválida.");
                    break;
            }
        } while (opcion != 0);
    }

    private void listarTurnosDelCliente(String dni) {
        System.out.println("=== TUS TURNOS ===");
        boolean hayTurnos = false;
        for (Turno t : crudTurnos.getLista()) {
            if (t.getDniCliente().equalsIgnoreCase(dni)) {
                System.out.println("ID:" + t.getIdTurno() + " | Fecha: " + t.getFechaHora() + " | Estado: " + t.getEstado());
                hayTurnos = true;
            }
        }
        if (!hayTurnos) {
            System.out.println("No tenés turnos registrados.");
        }
    }

    private void cancelarTurnoDelCliente(Cliente cliente) {
        System.out.print("ID del turno a cancelar: ");
        int id = Integer.parseInt(scanner.nextLine());
        for (Turno t : crudTurnos.getLista()) {
            if (t.getIdTurno() == id && t.getDniCliente().equalsIgnoreCase(cliente.getDni())) {
                t.setEstado(EstadoTurno.CANCELADO);
                System.out.println("Turno cancelado correctamente.");
                return;
            }
        }
        System.out.println("No se encontró un turno con ese ID o no te pertenece.");
    }
}
