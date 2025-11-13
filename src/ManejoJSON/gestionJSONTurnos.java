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

    // --- Convierte un JSONObject a Turno ---
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

    // --- Convierte JSONArray a lista ---
    public static List<Turno> mapeoTurnos(JSONArray jTurnos) {
        List<Turno> turnos = new ArrayList<>();
        for (int i = 0; i < jTurnos.length(); i++) {
            try {
                JSONObject jT = jTurnos.getJSONObject(i);
                turnos.add(mapeoTurno(jT));
            } catch (JSONException e) {
                throw new RuntimeException("Error al mapear turnos: " + e.getMessage());
            }
        }
        return turnos;
    }

    // --- Lee todos los turnos existentes ---
    public static List<Turno> leerTurnos() {
        File file = new File(ARCHIVO);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try {

            StringBuilder contenido = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO))) {
                String linea;
                while ((linea = br.readLine()) != null) {
                    contenido.append(linea);
                }
            }

            if (contenido.isEmpty()) return new ArrayList<>();

            JSONObject json = new JSONObject(contenido.toString());
            JSONArray jTurnos = json.optJSONArray("turnos");
            if (jTurnos == null) return new ArrayList<>();

            List<Turno> turnos = mapeoTurnos(jTurnos);

            // sincroniza ID generator
            int maxId = 0;
            for (Turno t : turnos) {
                if (t.getIdTurno() > maxId) maxId = t.getIdTurno();
            }
            IdGenerator.setTurnoCount(maxId);

            return turnos;

        } catch (JSONException e) {
            System.out.println("Advertencia: error al leer JSON: " + e.getMessage());
            return new ArrayList<>();
        } catch (IOException e) {
            System.out.println("Error al leer turnos: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // --- Agrega un turno sin borrar los anteriores ---
    public static void grabarTurno(Turno nuevoTurno) {
        List<Turno> turnos = leerTurnos();
        turnos.add(nuevoTurno);
        grabarTurnos(turnos);
    }

    // --- Graba toda la lista (tu estilo, sin errores de put/toString) ---
    public static void grabarTurnos(List<Turno> turnos) {
        JSONArray jTurnos = new JSONArray();

        for (Turno t : turnos) {
            JSONObject jT = new JSONObject();
            try {
                jT.put("idTurno", t.getIdTurno());
                jT.put("fechaHora", t.getFechaHora().toString());
                jT.put("estado", t.getEstado().name());
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
            throw new RuntimeException("Error al generar JSON raíz: " + e.getMessage());
        }

        // --- Escritura idéntica a tu formato original ---
        try (FileWriter fw = new FileWriter(ARCHIVO)) {
            try {
                fw.write(root.toString(2)); // indentación de 2 espacios
            } catch (JSONException e) {
                throw new RuntimeException("Error al convertir JSON a texto: " + e.getMessage());
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al grabar turnos.json: " + e.getMessage(), e);
        }
    }
}
