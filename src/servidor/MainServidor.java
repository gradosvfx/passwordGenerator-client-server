package servidor;

import java.io.IOException;

//clase principal para iniciar el servidor

public class MainServidor {

    public static void main(String[] args) {
        try {
            //instancia del servidor
            Servidor servidor = new Servidor();
            
            //bucle de aceptación de clientes
            servidor.iniciarServer();
            
        } catch (IOException e) {
            //manejo de error
            System.err.println("ERROR: No se pudo iniciar el Servidor.");
            e.printStackTrace();
        }
    }
}