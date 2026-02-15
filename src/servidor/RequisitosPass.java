package servidor;


public class RequisitosPass {
    
    //atributos
    private int numMayusculas;
    private int numMinusculas;
    private int numDigitos;
    private int numCaractEspeciales;

    
    public RequisitosPass() {
    }

    //getters
    public int getNumMayusculas() {
        return numMayusculas;
    }

    public int getNumMinusculas() {
        return numMinusculas;
    }

    public int getNumDigitos() {
        return numDigitos;
    }

    public int getNumCaractEspeciales() {
        return numCaractEspeciales;
    }

    //setters
    public void setNumMayusculas(int numMayusculas) {
        this.numMayusculas = numMayusculas;
    }

    public void setNumMinusculas(int numMinusculas) {
        this.numMinusculas = numMinusculas;
    }

    public void setNumDigitos(int numDigitos) {
        this.numDigitos = numDigitos;
    }

    public void setNumCaractEspeciales(int numCaractEspeciales) {
        this.numCaractEspeciales = numCaractEspeciales;
    }

    
    @Override
    public String toString() {
        return "RequisitosPass{" +
                "Mayúsculas=" + numMayusculas +
                ", Minúsculas=" + numMinusculas +
                ", Dígitos=" + numDigitos +
                ", Especiales=" + numCaractEspeciales +
                '}';
    }
}