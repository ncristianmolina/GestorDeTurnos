package ManejoJSON;

import Modelos.Actividad;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    public static List <Actividad> leerActividades(){
        try{
            JSONObject json= new JSONObject(JSONUtiles.leer("actividades.json"));
            JSONArray jActvidades = json.getJSONArray("actividades");
            return mapeoActividades(jActvidades);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}



