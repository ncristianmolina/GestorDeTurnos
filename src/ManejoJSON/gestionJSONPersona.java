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

public class gestionJSONPersona {

    private static final String ARCHIVO = "persona.json";

    /**
     * Carga todas las personas desde el archivo persona.json
     */
    public static ArrayList<Persona> leerPersonas() {
        ArrayList<Persona> lista = new ArrayList<>();

        try {
            JSONTokener tokener = JSONUtiles.leer(ARCHIVO);
            if (tokener == null) {
                System.err.println("⚠ No se pudo abrir el archivo JSON: " + ARCHIVO);
                return lista;
            }

            JSONObject raiz = new JSONObject(tokener);
            JSONArray array = raiz.getJSONArray("personas");

            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);

                String dni = obj.getString("dni");
                String nombre = obj.getString("nombre");
                String apellido = obj.getString("apellido");
                String email = obj.getString("email");
                String password = obj.getString("password");
                String usuario = obj.getString("usuario");
                String tipo = obj.getString("tipo");
                boolean esActivo = obj.getBoolean("esActivo");

                Persona p;
                if (tipo.equalsIgnoreCase("ADMIN")) {
                    p = new Administrador(dni, nombre, apellido, email, password, usuario, tipo, esActivo);
                } else {
                    p = new Cliente(dni, nombre, apellido, email, password, usuario, tipo, esActivo);
                }
                lista.add(p);
            }

            System.out.println("✔ Se cargaron " + lista.size() + " personas desde " + ARCHIVO);

        } catch (Exception e) {
            System.err.println("⚠ Error al leer " + ARCHIVO + ": " + e.getMessage());
        }

        return lista;
    }

    /**
     * Guarda una lista de personas en persona.json
     */
    public static void guardarPersonas(ArrayList<Persona> personas) throws JSONException {
        JSONArray array = new JSONArray();

        for (Persona p : personas) {
            try {
                JSONObject obj = new JSONObject();
                obj.put("dni", p.getDni());
                obj.put("nombre", p.getNombre());
                obj.put("apellido", p.getApellido());
                obj.put("email", p.getEmail());
                obj.put("password", p.getPassword());
                obj.put("usuario", p.getUsuario());
                obj.put("tipo", p.getTipo());
                obj.put("esActivo", p.getEsActivo());
                array.put(obj);
            } catch(JSONException e)
            {
                throw new RuntimeException(e);
            }


            JSONObject persona = new JSONObject();
            persona.put("personas", array);
            JSONUtiles.grabarPersonas(persona.getJSONArray("personas"));
        }

        System.out.println("Archivo persona.json actualizado correctamente.");
    }
}