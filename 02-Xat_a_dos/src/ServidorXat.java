import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ServidorXat {
    static final int PORT = 9999;
    static final String HOST = "localhost";
    public static final String MSG_SORTIR = "sortir";
    
    ServerSocket serverSocket = null;
    Socket clientSocket = null; 
    
    public void iniciarServidor() throws IOException {
        serverSocket = new ServerSocket(PORT);
        System.out.println("Servidor iniciat a " + HOST + ":" + PORT);
    }
    
    public void pararServidor() throws IOException {
        if (serverSocket != null && !serverSocket.isClosed()) {
            serverSocket.close();
        }
    }
    
    public String getNom(ObjectInputStream in) throws Exception {
        String nom = (String) in.readObject();
        System.out.println("Nom rebut: " + nom);
        return nom;
    }
    
    public static void main(String[] args) {
        ServidorXat servidor = new ServidorXat();
        
        try {
            servidor.iniciarServidor();
            servidor.clientSocket = servidor.serverSocket.accept();
            System.out.println("Client connectat: /" + servidor.clientSocket.getInetAddress().getHostAddress());
            
            ObjectOutputStream out = new ObjectOutputStream(servidor.clientSocket.getOutputStream());
            out.flush(); 
            ObjectInputStream in = new ObjectInputStream(servidor.clientSocket.getInputStream());
            
            out.writeObject("Escriu el teu nom:");
            out.flush();
            
            String nomClient = servidor.getNom(in);
            System.out.println("Fil de xat creat.");
            
            FilServidorXat filServidor = new FilServidorXat(in, nomClient);
            filServidor.start();
            
            Scanner scanner = new Scanner(System.in);
            String missatge;
            
            while (true) {
                missatge = scanner.nextLine();
                out.writeObject(missatge);
                out.flush();
                
                if (missatge.equalsIgnoreCase(MSG_SORTIR)) {
                    System.out.println("sortir");
                    break;
                }
            }
            
            filServidor.join();
            scanner.close();
            servidor.clientSocket.close(); 
            servidor.pararServidor();
            System.out.println("Servidor aturat.");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}