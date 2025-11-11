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

    @Override
    public String toString() {
        return "GestorActividades{" +
                "actividades=" + actividades +
                '}';
    }
}
