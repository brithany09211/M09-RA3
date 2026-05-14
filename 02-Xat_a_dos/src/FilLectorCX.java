import java.io.*;

public class FilLectorCX extends Thread {
    private ObjectInputStream in;
    
    public FilLectorCX(ObjectInputStream in) {
        this.in = in;
    }
    
    @Override
    public void run() {
        try {
            System.out.println("Missatge ('sortir' per tancar): Fil de lectura iniciat");
            
            while (true) {
                String msg = (String) in.readObject();
                
                if (msg.equalsIgnoreCase("sortir")) {
                    break;
                }
                System.out.println("Rebut: " + msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}