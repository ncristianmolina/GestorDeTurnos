package ManejoJSON;

import Modelos.Turno;
import org.json.*;
        import java.io.*;
        import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import Enum.EstadoTurno;

public class gestionJSONTurnos {

    private static final String ARCHIVO = "turnos.json";

    // 🔹 Mapea un objeto JSON a un Turno
    public static Turno mapeoTurno(JSONObject jTurno) {
        Turno turno = new Turno();

        try {
            turno.setIdTurno(jTurno.getInt("idTurno"));
            turno.setFechaHora(LocalDateTime.parse(jTurno.getString("fechaHora")));
            String estadoStr = jTurno.getString("estado");
            turno.setEstado(EstadoTurno.valueOf(estadoStr.toUpperCase()));
            turno.setDniCliente(jTurno.optString("dniCliente", null));
            turno.setIdActividad(jTurno.getInt("idActividad"));
        } catch (JSONException e) {
            throw new RuntimeException("Error al mapear turno: " + e.getMessage());
        }

        return turno;
    }

    // 🔹 Mapea un array JSON a una lista de Turnos
    public static List<Turno> mapeoTurnos(JSONArray jTurnos) {
        List<Turno> turnos = new ArrayList<>();

        for (int i = 0; i < jTurnos.length(); i++) {
            try {
                JSONObject jT = jTurnos.getJSONObject(i);
                Turno turno = mapeoTurno(jT);
                turnos.add(turno);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }

        return turnos;
    }

    // 🔹 Lee todos los turnos del archivo
    public static List<Turno> leerTurnos() {
        try {
            JSONObject json = new JSONObject(JSONUtiles.leer(ARCHIVO));
            JSONArray jTurnos = json.getJSONArray("turnos");
            return mapeoTurnos(jTurnos);
        } catch (JSONException e) {
            throw new RuntimeException("Error al leer turnos: " + e.getMessage());
        }
    }
}
