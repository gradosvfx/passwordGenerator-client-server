package cliente;

import java.io.IOException;

//clase principal cliente
public class MainCliente {

    public static void main(String[] args) {
        try {
            //instancia del cliente e inicio de comunicación
            Cliente cliente = new Cliente();
            cliente.iniciarConexion();

        } catch (IOException e) {
            //captura el error si el servidor no está disponible o si la comunicación falla
            System.err.println("ERROR DE CONEXIÓN: No se pudo establecer la comunicación con el servidor.");
            System.err.println("Inicia el servidor.");
        }
    }
}