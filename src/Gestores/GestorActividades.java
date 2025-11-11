package Gestores;

import Modelos.Actividad;

import java.util.ArrayList;
import java.util.List;

public class GestorActividades {
    List<Actividad> actividades  = new ArrayList;

    public GestorActividades(List<Actividad> actividades) {
        this.actividades = actividades;
    }

    public List<Actividad> getActividades() {
        return actividades;
    }

    public void setActividades(List<Actividad> actividades) {
        this.actividades = actividades;
    }

    public Actividad buscarPorNombre(String nombreActividad) {
        for (Actividad a : actividades) {
            if (a.getTipoActividad().equalsIgnoreCase(nombreActividad)) {
                return a;
            }
        }
        return null; // No se encontró
    }

    public Actividad buscarPorId(int idActividad) {
        for (Actividad a : actividades) {
            if (a.getIdActividad() == idActividad) {
                return a;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "GestorActividades{" +
                "actividades=" + actividades +
                '}';
    }
}
