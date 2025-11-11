import ManejoJSON.JSONUtiles;
import ManejoJSON.gestionJSONActividad;
import Modelos.Actividad;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        gestionJSONActividad GestionJSONActividades = new gestionJSONActividad();
        List<Actividad> actividades = GestionJSONActividades.leerActividades();
    }
}
