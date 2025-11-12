package Gestores;

import Exceptions.*;
import Gestores.GestorGenerico;
import Modelos.Cliente;
import Modelos.Persona;

import java.util.ArrayList;
import java.util.List;


public class GestorClientes extends GestorGenerico<Persona> {
    public GestorClientes() {
        super();
    }

    public GestorClientes(List<Persona> persona) {
        this.lista = persona;
    }

    public void agregarPersona(Cliente persona) throws DNIClienteDuplicadoException {
        for (Persona p : lista) {
            if (p.getDni().equalsIgnoreCase(persona.getDni())) {
                throw new DNIClienteDuplicadoException("Ya existe una persona con el DNI: " + persona.getDni());
            }
        }
        lista.add(persona);
        System.out.println("Persona agregada: " + persona.getNombre() + " " + persona.getApellido());
    }

    public Persona buscarPorDni(String dni) throws ClienteNoEncontradoException {
        for (Persona p : lista) {
            if (p.getDni().equalsIgnoreCase(dni)) {
                return p;
            }
        }
        throw new ClienteNoEncontradoException("No se encontró un cliente con DNI: " + dni);
    }


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
            System.out.println("Persona eliminada: " + personaAEliminar.getNombre());
        } else {
            throw new ClienteNoEncontradoException("No existe un cliente con DNI: " + dni);
        }
    }
}




