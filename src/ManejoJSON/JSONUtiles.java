package ManejoJSON;

import org.json.JSONArray;
import org.json.JSONTokener;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class JSONUtiles {

    // ----------------------------
    // MÉTODO GENÉRICO PARA LEER
    // ----------------------------
    private static JSONTokener leerArchivo(String archivo) {
        JSONTokener tokener = null;
        try {
            tokener = new JSONTokener(new FileReader(archivo));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return tokener;
    }

    // ----------------------------
    // MÉTODOS ESPECÍFICOS
    // ----------------------------

    public static JSONArray leerActividades() {
        return new JSONArray(leerArchivo("actividades.json"));
    }

    public static JSONArray leerClientes() {
        return new JSONArray(leerArchivo("clientes.json"));
    }

    public static JSONArray leerTurnos() {
        return new JSONArray(leerArchivo("turnos.json"));
    }

    // ----------------------------
    // MÉTODO PARA GRABAR
    // ----------------------------
    public static void grabar(String archivo, JSONArray array) {
        try (FileWriter file = new FileWriter(archivo)) {
            file.write(array.toString(2));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
