import java.io.*;

public class FilServidorXat extends Thread {
    private ObjectInputStream in;
    private String nom;
    
    public FilServidorXat(ObjectInputStream in, String nom) {
        this.in = in;
        this.nom = nom;
    }
    
    @Override
    public void run() {
        try {
            System.out.println("Fil de " + nom + " iniciat");
            
            while (true) {
                String missatge = (String) in.readObject();
                System.out.println("Missatge ('sortir' per tancar): Rebut: " + missatge);
                
                if (missatge.equalsIgnoreCase(ServidorXat.MSG_SORTIR)) {
                    System.out.println("Fil de xat finalitzat.");
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}