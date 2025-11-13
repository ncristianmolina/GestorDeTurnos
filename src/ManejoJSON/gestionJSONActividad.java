package ManejoJSON;

import Modelos.Actividad;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class gestionJSONActividad {


    public static Actividad mapeoActividad(JSONObject jActividad) {
        Actividad actividad = new Actividad();

        try {
            actividad.setIdActividad(jActividad.getInt("idActividad"));
            actividad.setTipoActividad(jActividad.getString("tipoActividad"));
            actividad.setCapacidadMaxima(jActividad.getInt("capacidadMaxima"));
            actividad.setPrecio(jActividad.getDouble("precio"));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return actividad;

    }

    public static List <Actividad> mapeoActividades(JSONArray jActividades){
        List<Actividad> actividades = new ArrayList<>();

        for (int i=0; i < jActividades.length(); i++){
            try {
                JSONObject jAct= jActividades.getJSONObject(i);
                Actividad actividad = mapeoActividad(jAct);
                actividades.add(actividad);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
         return actividades;
    }

    public static List<Actividad> leerActividades(){
        String archivo = "src/data/actividad.json";
        try {
            JSONObject json = new JSONObject(JSONUtiles.leer(archivo));
            JSONArray jActividades = json.getJSONArray("actividades");
            return mapeoActividades(jActividades);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }


    public static void grabarActividades(List<Actividad> actividades) {
        JSONArray jActividades = new JSONArray();

        for (Actividad a : actividades) {
            JSONObject jAct = new JSONObject();
            try {
                jAct.put("idActividad", a.getIdActividad());
                jAct.put("tipoActividad", a.getTipoActividad());
                jAct.put("capacidadMaxima", a.getCapacidadMaxima());
                jAct.put("precio", a.getPrecio());
                jActividades.put(jAct);
            } catch (JSONException e) {
                throw new RuntimeException("Error al convertir actividad a JSON: " + e.getMessage());
            }
        }

        JSONObject root = new JSONObject();
        try {
            root.put("actividades", jActividades);
        } catch (JSONException e) {
            throw new RuntimeException("Error al generar JSON raíz de actividades: " + e.getMessage());
        }

        try (FileWriter fw = new FileWriter("src/data/actividades.json")) {
            try {
                fw.write(root.toString(2));
            } catch (JSONException e) {
                throw new RuntimeException("Error al convertir JSON a texto (actividades): " + e.getMessage());
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al grabar actividades.json: " + e.getMessage(), e);
        }
    }


}



