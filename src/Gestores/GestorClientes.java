package Modelos;

import Exceptions.*;
import Gestores.GestorGenerico;

import java.util.ArrayList;


public class GestorClientes extends GestorGenerico<Persona> {
    private ArrayList<Cliente> lista;

    // Agregar persona validando que el DNI no exista
    public void agregarPersona(Cliente persona) throws DNIClienteDuplicadoException {
        for (Persona p : lista) {
            if (p.getDni().equalsIgnoreCase(persona.getDni())) {
                throw new DNIClienteDuplicadoException("Ya existe una persona con el DNI: " + persona.getDni());
            }
        }
        lista.add(persona);
        System.out.println("✅ Persona agregada: " + persona.getNombre() + " " + persona.getApellido());
    }

    // Buscar persona por DNI
    public Cliente buscarPorDni(String dni) throws ClienteNoEncontradoException {
        for (Cliente p : lista) {
            if (p.getDni().equalsIgnoreCase(dni)) {
                return p;
            }
        }
        throw new ClienteNoEncontradoException("No se encontró un cliente con DNI: " + dni);
    }

    // Eliminar persona por DNI
    public void eliminarPorDni(String dni) throws ClienteNoEncontradoException {
        Persona personaAEliminar = null;
        for (Persona p : lista) {
            if (p.getDni().equalsIgnoreCase(dni)) {
                personaAEliminar = p;
                break;
            }
        }
        if (personaAEliminar != null) {
            lista.remove(personaAEliminar);
            System.out.println("❌ Persona eliminada: " + personaAEliminar.getNombre());
        } else {
            throw new ClienteNoEncontradoException("No existe un cliente con DNI: " + dni);
        }
    }
}




