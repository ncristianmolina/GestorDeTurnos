package Gestores;

import Exceptions.ClienteNoEncontradoException;
import Modelos.Cliente;
import java.util.ArrayList;

public class GestorClientes {
    private ArrayList<Cliente> clientes;

    public GestorClientes(ArrayList<Cliente> clientes) {
        this.clientes = clientes;
    }

    public Cliente buscarClientePorDNI(String DNI) throws ClienteNoEncontradoException {
        for (Cliente c : clientes) {
            if (c.getDni().equals(DNI)) {
                return c;
            }
        }
        throw new ClienteNoEncontradoException("No se ha encontrado el cliente con el DNI: " + DNI);
    }


}





