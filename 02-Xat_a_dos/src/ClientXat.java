import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClientXat {
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    
    public void connecta() throws IOException {
        socket = new Socket("localhost", 9999);
        out = new ObjectOutputStream(socket.getOutputStream());
        out.flush(); 
        in = new ObjectInputStream(socket.getInputStream());
    }
    
    public void enviarMissatge(String missatge) throws IOException {
        out.writeObject(missatge);
        out.flush();
    }
    
    public void tancarClient() throws IOException {
        if (socket != null && !socket.isClosed()) {
            socket.close();
        }
    }
    
    public static void main(String[] args) {
        ClientXat client = new ClientXat();
        
        try {
            System.out.println("Client connectat a localhost:9999");
            client.connecta();
            System.out.println("Flux d'entrada i sortida creat.");
            
            FilLectorCX filLector = new FilLectorCX(client.in);
            filLector.start();
            
            Scanner scanner = new Scanner(System.in);

            Thread.sleep(100); 
            
            String nom = scanner.nextLine();
            client.enviarMissatge(nom);
            System.out.println("Enviant missatge: " + nom);
            
            while (true) {
                System.out.print("Missatge ('sortir' per tancar): ");
                String missatge = scanner.nextLine();
                System.out.println("Enviant missatge: " + missatge);
                
                client.enviarMissatge(missatge);
                
                if (missatge.equalsIgnoreCase("sortir")) {
                    System.out.println("Tancant client...");
                    break;
                }
            }
            
            filLector.join();
            scanner.close();
            client.tancarClient();
            System.out.println("Client tancat.");
            System.out.println("El servidor ha tancat la connexió.");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}