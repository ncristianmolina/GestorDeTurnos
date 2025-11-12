package Gestores;

import Modelos.Actividad;
import Modelos.Turno;

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


    /*Método actividades con más inscriptos*/

    public static List<Actividad> actividadesConMasInscriptos(List<Actividad> actividades, List<Turno> turnos) {

        int maxInscriptos = 0;

        for (Actividad a : actividades) {
            int contador = 0;

            for (Turno t : turnos) {
                if (t.getIdActividad() == a.getIdActividad()) {
                    contador++;
                }
            }

            if (contador > maxInscriptos) {
                maxInscriptos = contador;
            }
        }

        List<Actividad> resultado = new ArrayList<>();

        for (Actividad a : actividades) {
            int contador = 0;

            for (Turno t : turnos) {
                if (t.getIdActividad() == a.getIdActividad()) {
                    contador++;
                }
            }

            if (contador == maxInscriptos) {
                resultado.add(a);
            }
        }

        return resultado;
    }

    @Override
    public String toString() {
        return "GestorActividades{" +
                "actividades=" + lista +
                '}';
    }
}
