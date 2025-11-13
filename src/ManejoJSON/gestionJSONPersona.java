package ManejoJSON;

import Gestores.GestorClientes;
import Modelos.Administrador;
import Modelos.Cliente;
import Modelos.Persona;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class gestionJSONPersona {

    private static final String ARCHIVO = "src/data/persona.json";

    public static Persona mapeoPersona(JSONObject jPersona) {
        Persona persona;

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
                persona = new Administrador(dni, nombre, apellido, email, password, usuario, tipo, esActivo);
            } else {
                persona = new Cliente(dni, nombre, apellido, email, password, usuario, tipo, esActivo);
            }

        } catch (JSONException e) {
            throw new RuntimeException("Error al mapear persona: " + e.getMessage());
        }

        return persona;
    }

    public static List<Persona> mapeoPersonas(JSONArray jPersonas) {
        List<Persona> personas = new ArrayList<>();

        for (int i = 0; i < jPersonas.length(); i++) {
            try {
                JSONObject jPers = jPersonas.getJSONObject(i);
                Persona persona = mapeoPersona(jPers);
                personas.add(persona);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }

        return personas;
    }

    public static List<Persona> leerPersonas() {
        String archivo = "src/data/persona.json";
        try {
            JSONObject json = new JSONObject(JSONUtiles.leer(archivo));
            JSONArray jPersonas = json.getJSONArray("persona");
            return mapeoPersonas(jPersonas);
        } catch (JSONException e) {
            throw new RuntimeException("Error al leer personas: " + e.getMessage());
        }
    }


    public static void grabarPersonas(List<Persona> personas) {
        JSONArray jPersonas = new JSONArray();

        for (Persona p : personas) {
            JSONObject jPers = new JSONObject();
            try {
                jPers.accumulate("dni", p.getDni());
                jPers.accumulate("nombre", p.getNombre());
                jPers.accumulate("apellido", p.getApellido());
                jPers.accumulate("email", p.getEmail());
                jPers.accumulate("password", p.getPassword());
                jPers.accumulate("usuario", p.getUsuario());
                jPers.accumulate("tipo", p.getTipo());
                jPers.accumulate("esActivo", p.getEsActivo());
                jPersonas.put(jPers);
            } catch (JSONException e) {
                throw new RuntimeException("Error al convertir persona a JSON: " + e.getMessage());
            }
        }

        JSONUtiles.grabarPersonas(jPersonas);
    }

}