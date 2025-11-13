package ManejoJSON;

import Modelos.Turno;
import org.json.*;
import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import Enum.EstadoTurno;
import Util.IdGenerator;


public class gestionJSONTurnos {

    private static final String ARCHIVO = "src/data/turnos.json";

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

    public static List<Turno> leerTurnos() {
        try {
            JSONObject json = new JSONObject(JSONUtiles.leer(ARCHIVO));
            JSONArray jTurnos = json.getJSONArray("turnos");
            List<Turno> turnos = mapeoTurnos(jTurnos);

            // --- Sincronizar IdGenerator con el último ID más alto encontrado ---
            int maxId = 0;
            for (Turno t : turnos) {
                if (t.getIdTurno() > maxId) {
                    maxId = t.getIdTurno();
                }
            }
            IdGenerator.setTurnoCount(maxId);

            return turnos;
        } catch (JSONException e) {
            throw new RuntimeException("Error al leer turnos: " + e.getMessage());
        }
    }

    // ---- persistencia correcta: escribimos un objeto raíz { "turnos": [ ... ] } ----
    public static void grabarTurnos(List<Turno> turnos) {
        JSONArray jTurnos = new JSONArray();

        for (Turno t : turnos) {
            JSONObject jT = new JSONObject();
            try {
                jT.put("idTurno", t.getIdTurno());
                jT.put("fechaHora", t.getFechaHora().toString()); // formato ISO
                jT.put("estado", t.getEstado().name()); // enum → String
                jT.put("dniCliente", t.getDniCliente());
                jT.put("idActividad", t.getIdActividad());
                jTurnos.put(jT);
            } catch (JSONException e) {
                throw new RuntimeException("Error al convertir turno a JSON: " + e.getMessage());
            }
        }

        JSONObject root = new JSONObject();
        try {
            root.put("turnos", jTurnos);
        } catch (JSONException e) {
            throw new RuntimeException("Error al generar JSON raíz de turnos: " + e.getMessage());
        }

        try (FileWriter fw = new FileWriter(ARCHIVO)) {
            try {
                fw.write(root.toString(2)); // indentado = 2
            } catch (JSONException e) {
                throw new RuntimeException("Error al convertir JSON a texto (turnos): " + e.getMessage());
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al grabar turnos.json: " + e.getMessage(), e);
        }
    }
}
