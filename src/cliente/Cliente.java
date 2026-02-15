package cliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

//cliente que se conecta al servidor
public class Cliente {

    //datos de la conexion
    private final String HOST = "localhost";
    private final int PUERTO = 4321;
    private Socket socket;

    //constructor de Cliente y intenta establecer la conexión
    
    public Cliente() throws IOException {
        this.socket = new Socket(HOST, PUERTO);
    }

    //inicia la conexión y gestiona la interacción con el servidor

    public void iniciarConexion() throws IOException {
        
        BufferedReader consoleIn = new BufferedReader(new InputStreamReader(System.in));
        
        
        try (
            BufferedReader serverIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter serverOut = new PrintWriter(socket.getOutputStream(), true)
        ) {
            System.out.println("CLIENTE: Conectado. Iniciando conversación...");

            //inicia la conversación con el servidor 
            manejarInteraccion(consoleIn, serverIn, serverOut);

        } catch (UnknownHostException e) {
            System.err.println("CLIENTE ERROR: Host desconocido " + HOST);
            throw new IOException("Host desconocido", e);
        } catch (IOException e) {
     
            System.err.println("CLIENTE ERROR: Fallo durante la comunicación.");
            
        } finally {
            
            if (socket != null && !socket.isClosed()) {
                socket.close();
                System.out.println("CLIENTE: Conversación terminada y conexión cerrada.");
            }
        }
    }

    
    private void manejarInteraccion(BufferedReader consoleIn, BufferedReader serverIn, PrintWriter serverOut) throws IOException {
        String serverResponse;
        String clientInput;

        //interaccion inicial 
        
        while ((serverResponse = serverIn.readLine()) != null) {
            System.out.println(serverResponse);
            
            if (serverResponse.contains("introduce tu nombre")) {
                break;
            }
        }

        //cliente envia el nombre
        System.out.print("Tu nombre: ");
        clientInput = consoleIn.readLine();
        serverOut.println(clientInput);
        
        System.out.println(serverIn.readLine()); 
        System.out.println(serverIn.readLine());

        //recoge requisitos 
       
        for (int i = 0; i < 4; i++) {
            
            
            serverResponse = serverIn.readLine();
            if (serverResponse == null) return; 
            
            
            if (serverResponse.startsWith("SERVIDOR: ERROR")) {
                 System.out.println(serverResponse);
                 System.err.println("CLIENTE: Desconexión forzada por parámetro inválido enviado.");
                 return;
            }
            
            
            System.out.println(serverResponse);
            
            
            System.out.print("Cantidad (número entero positivo): ");
            clientInput = consoleIn.readLine();
            serverOut.println(clientInput);
        }
        
        //longitud y confirmacion

        while ((serverResponse = serverIn.readLine()) != null) {
            System.out.println(serverResponse);
            if (serverResponse.contains("generar esta contraseña ahora")) {
                break;
            }
        }

        //respuesta cliente
        System.out.print("Respuesta (sí/no): ");
        clientInput = consoleIn.readLine();
        serverOut.println(clientInput);

        while ((serverResponse = serverIn.readLine()) != null) {
            System.out.println(serverResponse);
        
            if (serverResponse.contains("Tu nueva contraseña segura es:") || serverResponse.contains("generará ninguna contraseña")) {
                break;
            }
        }
        
        
    }
}