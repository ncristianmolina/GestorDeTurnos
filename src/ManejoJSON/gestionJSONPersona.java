package ManejoJSON;

import Modelos.Administrador;
import Modelos.Cliente;
import Modelos.Persona;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class gestionJSONPersona {

    private static final String ARCHIVO = "src/data/persona.json";


    public static Persona mapeoPersona(JSONObject jPersona) {
        try {
            String dni = jPersona.getString("dni");
            String nombre = jPersona.getString("nombre");
            String apellido = jPersona.getString("apellido");
            String email = jPersona.getString("email");
            String password = jPersona.getString("password");
            String usuario = jPersona.getString("usuario");
            String tipo = jPersona.getString("tipo");
            boolean esActivo = jPersona.getBoolean("esActivo");

            if (tipo.equalsIgnoreCase("ADMIN")) {
                return new Administrador(dni, nombre, apellido, email, password, usuario, tipo, esActivo);
            } else {
                return new Cliente(dni, nombre, apellido, email, password, usuario, tipo, esActivo);
            }

        } catch (JSONException e) {
            throw new RuntimeException("Error al mapear persona: " + e.getMessage());
        }
    }


    public static List<Persona> mapeoPersonas(JSONArray jPersonas) {
        List<Persona> personas = new ArrayList<>();

        for (int i = 0; i < jPersonas.length(); i++) {
            try {
                JSONObject jPers = jPersonas.getJSONObject(i);
                Persona persona = mapeoPersona(jPers);
                personas.add(persona);
            } catch (JSONException e) {
                throw new RuntimeException("Error al mapear lista de personas: " + e.getMessage());
            }
        }
        return personas;
    }


    public static List<Persona> leerPersonas() {
        try {
            JSONObject json = new JSONObject(JSONUtiles.leer(ARCHIVO));
            JSONArray jPersonas = json.getJSONArray("persona");
            return mapeoPersonas(jPersonas);
        } catch (JSONException e) {
            throw new RuntimeException("Error al leer personas: " + e.getMessage());
        }
    }


    public static void grabarPersonas(List<Persona> personas) {
        JSONArray jPersonas = new JSONArray();


        for (Persona p : personas) {
            try {
                JSONObject jPers = new JSONObject();
                jPers.put("dni", p.getDni());
                jPers.put("nombre", p.getNombre());
                jPers.put("apellido", p.getApellido());
                jPers.put("email", p.getEmail());
                jPers.put("password", p.getPassword());
                jPers.put("usuario", p.getUsuario());
                jPers.put("tipo", p.getTipo() != null ? p.getTipo().name() : JSONObject.NULL);
                jPers.put("esActivo", p.getEsActivo());

                if (p instanceof Cliente c) {
                    if (c.getTelefono() != null) jPers.put("telefono", c.getTelefono());
                }
                if (p instanceof Administrador a) {
                    jPers.put("nivelAcceso", a.getNivelAcceso());
                }

                jPersonas.put(jPers);

            } catch (JSONException e) {
                throw new RuntimeException("Error al convertir persona a JSON: " + e.getMessage());
            }
        }


        JSONObject root;
        try {
            root = new JSONObject();
            root.put("persona", jPersonas);
        } catch (JSONException e) {
            throw new RuntimeException("Error al generar JSON raíz: " + e.getMessage());
        }


        try (FileWriter fw = new FileWriter(ARCHIVO)) {
            try {
                fw.write(root.toString(2)); // 2 = indentación
            } catch (JSONException e) {
                // Capturamos el JSONException que lanza toString(2)
                throw new RuntimeException("Error al convertir JSON a texto: " + e.getMessage());
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al grabar persona.json: " + e.getMessage(), e);
        }
    }
}
