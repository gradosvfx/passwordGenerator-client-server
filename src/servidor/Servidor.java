package servidor;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

//servidor atiende peticiones de clientes
 
public class Servidor {

    private final int PUERTO = 4321; //puerto
    private ServerSocket serverSocket;
    private final ServicioPass servicioPass = new ServicioPass();

    //constructor del Servidor. Inicializa el ServerSocket
     
    public Servidor() throws IOException {
        this.serverSocket = new ServerSocket(PUERTO);
    }

    //inicia el servidor
    public void iniciarServer() {
        System.out.println("Servidor iniciado. Puerto " + PUERTO + "...");

        //bucle para seguir aceptando clientes
        while (true) {
            Socket socket = null;
            try {
                System.out.println("\n-------------------------------------------");
                System.out.println("Esperando nueva conexión de cliente...");
                
               
                socket = serverSocket.accept(); 
                System.out.println("LOG INFO: Nuevo cliente conectado desde: " + socket.getInetAddress().getHostAddress());

                //manejo de las interacciones con el cliente
                manejarCliente(socket);
                
            } catch (IOException e) {
                System.err.println("Error en el servidor: " + e.getMessage());
            } finally {
                //asegura cierre del socket del cliente
                try {
                   
                    if (socket != null && !socket.isClosed()) {
                        socket.close();
                    }
                } catch (IOException e) {
                    System.err.println("Error al cerrar el socket.");
                }
            }
        }
    }

    //flujo interacción con el cliente
    
    private void manejarCliente(Socket clientSocket) {
        String clientName = "Desconocido"; 
        boolean conexionRota = false; 
        
        try (
            
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
        ) {
            //interaccion inicial
            out.println("SERVIDOR: Conexión establecida");
            out.println("SERVIDOR: Por favor, introduce tu nombre:");
            
            //servidor recibe el nombre
            String inputLine = in.readLine();
            if (inputLine == null) return; 
            clientName = inputLine.trim();
            System.out.println("LOG INFO: Nombre nuevo cliente: " + clientName); 
            
            //mensaje saludo
            out.println("SERVIDOR: Bienvenido/a, " + clientName);
            out.println("SERVIDOR: Vamos a generar una contraseña. Dime la cantidad de cada tipo de carácter que deseas.");
            
            //recogida de requisitos
            RequisitosPass req = new RequisitosPass();

            // solicitud y validación de los requisitos
            
            if (!solicitarRequisito(out, in, "mayúsculas", req::setNumMayusculas, clientName)) { conexionRota = true; return; }
            if (!solicitarRequisito(out, in, "minúsculas", req::setNumMinusculas, clientName)) { conexionRota = true; return; }
            if (!solicitarRequisito(out, in, "dígitos (numeros)", req::setNumDigitos, clientName)) { conexionRota = true; return; }
            if (!solicitarRequisito(out, in, "caracteres especiales (" + ServicioPass.ESPECIALES + ")", req::setNumCaractEspeciales, clientName)) { conexionRota = true; return; }

            
            System.out.println("LOG INFO: Los requisitos del cliente " + clientName + " son: ");
            System.out.println("Mayusculas: " + req.getNumMayusculas() + 
                               ", Minúsculas: " + req.getNumMinusculas() + 
                               ", Dígitos: " + req.getNumDigitos() + 
                               ", Especiales: " + req.getNumCaractEspeciales());

            //longitud y confirmacion
            int longitud = servicioPass.obtenerLongitud(req);
            
            out.println("SERVIDOR: La longitud total de la contraseña sería de " + longitud + " caracteres.");
            System.out.println("LOG INFO: Longitud de contraseña (" + longitud + ") enviada al cliente.");             
            out.println("SERVIDOR: ¿Quieres generar esta contraseña ahora? (sí/no)"); 

            //respuesta cliente
            String confirmacion = in.readLine();
            if (confirmacion == null) return;
            System.out.println("LOG INFO: Confirmación recibida del cliente: " + confirmacion.trim()); 
            
            if (confirmacion.trim().equalsIgnoreCase("sí") || confirmacion.trim().equalsIgnoreCase("si")) {
                //generacion contraseña
                String password = servicioPass.generarPass(req); //random
                out.println("SERVIDOR: Tu nueva contraseña segura es: " + password);
                System.out.println("LOG INFO: Contraseña generada y enviada al cliente.");
            } else {
                //no generar
                out.println("SERVIDOR: No se generará ninguna contraseña. Adios");
                System.out.println("LOG INFO: Cliente declinó la generación de contraseña. Despedida enviada."); 
            }

        } catch (IOException e) {
            
            if (!conexionRota) {
                System.err.println("Error de comunicación con el cliente " + clientName + ": " + e.getMessage());
            }
        } finally {
            
            System.out.println("LOG INFO: Conexión con " + clientName + " finalizada.");
        }
    }

    
    private boolean solicitarRequisito(PrintWriter out, BufferedReader in, String tipo, RequisitoSetter setter, String clientName) throws IOException {
       
        out.println("SERVIDOR: Introduce el número de " + tipo + " que debe tener la contraseña:");
        String input = in.readLine();

        if (input == null) {
            return false; 
        }

        try {
            
            int num = Integer.parseInt(input.trim());
            
            if (num < 0) {
                // ERROR por valor negativo
                out.println("SERVIDOR: ERROR. El número de caracteres debe ser 0 o positivo. Conexión cerrada.");
                System.err.println("LOG ERROR: Parámetro inválido de " + clientName + " para " + tipo + ": valor negativo (" + num + ")");
                return false;
            }
            setter.set(num);
            System.out.println("LOG INFO: Cantidad de " + tipo + " recibida correctamente: " + num); 
            return true;
        } catch (NumberFormatException e) {
            // ERROR por valor no numérico
            out.println("SERVIDOR: ERROR. El valor introducido no es un número válido");
            System.err.println("LOG ERROR: Parámetro inválido de " + clientName + " para " + tipo + ": no numérico (" + input.trim() + ")");
            return false;
        }
    }

    
    @FunctionalInterface
    private interface RequisitoSetter {
        void set(int value);
    }
}