package ar.com.ada.creditos.excepciones;

import ar.com.ada.creditos.entities.*;

public class ClienteDNIException extends ClienteInfoException {

    public ClienteDNIException(Cliente cliente, String mensaje) {
        super(cliente, mensaje);
    }

}
