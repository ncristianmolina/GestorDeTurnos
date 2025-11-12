package Gestores;

import Modelos.Actividad;

import java.util.ArrayList;
import java.util.List;

public class GestorActividades extends GestorGenerico<Actividad> {

    public GestorActividades() {
        super();
    }

    public GestorActividades(List<Actividad> actividades) {
        this.lista = actividades;
    }


    public Actividad buscarPorNombre(String nombreActividad) {
        for (Actividad a : lista) {
            if (a.getTipoActividad().equalsIgnoreCase(nombreActividad)) {
                return a;
            }
        }
        return null;
    }

    public Actividad buscarPorId(int idActividad) {
        for (Actividad a : lista) {
            if (a.getIdActividad() == idActividad) {
                return a;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "GestorActividades{" +
                "actividades=" + lista +
                '}';
    }
}
