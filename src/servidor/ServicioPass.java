package servidor;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ServicioPass {

    //definicion de los caracteres que queremos en cada atributo
    public static final String MAYUSCULAS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String MINUSCULAS = "abcdefghijklmnopqrstuvwxyz";
    public static final String DIGITOS = "0123456789";
    public static final String ESPECIALES = "!@#$%^&*()_-+=.:?"; 

    private final SecureRandom random = new SecureRandom(); 

    // metodo calculo
     
    public int obtenerLongitud(RequisitosPass req) {
       
        return req.getNumMayusculas() + req.getNumMinusculas() + req.getNumDigitos() + req.getNumCaractEspeciales();
    }

    //metodo generacion contraseña random
     
    public String generarPass(RequisitosPass req) {
        List<Character> passwordChars = new ArrayList<>();

        
        agregarCaracteresAleatorios(passwordChars, MAYUSCULAS, req.getNumMayusculas());
        agregarCaracteresAleatorios(passwordChars, MINUSCULAS, req.getNumMinusculas());
        agregarCaracteresAleatorios(passwordChars, DIGITOS, req.getNumDigitos());
        agregarCaracteresAleatorios(passwordChars, ESPECIALES, req.getNumCaractEspeciales());

        //random
        Collections.shuffle(passwordChars);

        
        StringBuilder password = new StringBuilder(passwordChars.size());
        
        for (Character c : passwordChars) {
            password.append(c);
        }

        return password.toString();
    }

    
    private void agregarCaracteresAleatorios(List<Character> list, String source, int count) {
        
        for (int i = 0; i < count; i++) {
            int randomIndex = random.nextInt(source.length());
            list.add(source.charAt(randomIndex));
        }
    }
}