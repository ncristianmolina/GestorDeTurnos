package ManejoJSON;

import org.json.JSONArray;
import org.json.JSONTokener;
import org.json.JSONException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class JSONUtiles {

    // ----------------------------
    // GRABAR (NO SE TOCA)
    // ----------------------------
    public static void grabar(JSONArray array) {
        try {
            FileWriter file = new FileWriter("persona.json");
            file.write(array.toString());
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ----------------------------
    // LEER (NO SE TOCA)
    // ----------------------------
    public static JSONTokener leer(String archivo) {
        JSONTokener tokener = null;

        try {
            tokener = new JSONTokener(new FileReader(archivo));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return tokener;
    }

    // ----------------------------
    // ADAPTACIÓN: métodos ESPECÍFICOS
    // ----------------------------

    public static JSONArray leerActividades() {
        try {
            return new JSONArray(leer("actividades.json"));
        } catch (JSONException e) {
            return new JSONArray();
        }
    }

    public static JSONArray leerClientes() {
        try {
            return new JSONArray(leer("clientes.json"));
        } catch (JSONException e) {
            return new JSONArray();
        }
    }

    public static JSONArray leerTurnos() {
        try {
            return new JSONArray(leer("turnos.json"));
        } catch (JSONException e) {
            return new JSONArray();
        }
    }
}