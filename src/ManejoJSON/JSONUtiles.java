package ManejoJSON;

import org.json.JSONArray;
import org.json.JSONTokener;
import org.json.JSONException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class JSONUtiles {


    public static void grabar(JSONArray array, String nombreArchivo) {
        try {
            FileWriter file = new FileWriter(nombreArchivo);
            file.write(array.toString());
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void grabarActividades(JSONArray array) {
        grabar(array, "src/data/actividades.json");
    }

    public static void grabarPersonas(JSONArray array) {
        grabar(array, "src/data/clientes.json");
    }

    public static void grabarTurnos(JSONArray array) {
        grabar(array, "src/data/turnos.json");
    }


    public static JSONTokener leer(String archivo) {
        JSONTokener tokener = null;

        try {
            tokener = new JSONTokener(new FileReader(archivo));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return tokener;
    }

}