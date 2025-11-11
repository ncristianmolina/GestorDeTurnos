package ManejoJSON;

import Modelos.Turno;
import org.json.*;
        import java.io.*;
        import java.time.LocalDateTime;
import java.util.ArrayList;

public class gestionJSONTurnos {

    private static final String ARCHIVO = "turnos.json";

    /**
     * Carga todos los turnos desde el archivo turnos.json
     */
    public static ArrayList<Turno> leerTurnos() {
        ArrayList<Turno> lista = new ArrayList<>();

        try {
            // Se obtiene el JSONArray directamente desde JSONUtiles
            JSONArray array = JSONUtiles.leerTurnos();
            if (array == null) {
                System.err.println("⚠ No se pudo abrir el archivo JSON: " + ARCHIVO);
                return lista;
            }

            // Recorremos el arreglo "turnos"
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);

                int idTurno = obj.getInt("idTurno");
                String fechaHoraStr = obj.getString("fechaHora");
                String estado = obj.getString("estado");
                String dniCliente = obj.optString("dniCliente", null); // puede ser null
                int idActividad = obj.getInt("idActividad");

                // Convertimos la fecha-hora del string ISO a LocalDateTime
                LocalDateTime fechaHora = LocalDateTime.parse(fechaHoraStr);

                Turno t = new Turno(idTurno, fechaHora, estado, dniCliente, idActividad);
                lista.add(t);
            }

            System.out.println("✔ Se cargaron " + lista.size() + " turnos desde " + ARCHIVO);

        } catch (Exception e) {
            System.err.println("⚠ Error al leer " + ARCHIVO + ": " + e.getMessage());
        }

        return lista;
    }

    /**
     * Guarda una lista de turnos en turnos.json
     */
    public static void guardarTurnos(ArrayList<Turno> turnos) throws JSONException {
        JSONArray array = new JSONArray();

        for (Turno t : turnos) {
            JSONObject obj = new JSONObject();
            obj.put("idTurno", t.getIdTurno());
            obj.put("fechaHora", t.getFechaHora().toString()); // formato ISO 8601
            obj.put("estado", t.getEstado());
            obj.put("dniCliente", t.getDniCliente()); // puede ser null
            obj.put("idActividad", t.getIdActividad());
            array.put(obj);
        }


        JSONUtiles.grabar(array);

        System.out.println("✔ Archivo " + ARCHIVO + " actualizado correctamente.");
    }


}
