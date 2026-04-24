import java.net.*;
import java.io.*;

public class Client {

    public static final int PORT = Servidor.getPort();
    public static final String HOST = Servidor.getHost();

    public static Socket socket;
    public static PrintWriter out;

    public static void conecta() {
        try {
            socket = new Socket(HOST, PORT);
            out = new PrintWriter(socket.getOutputStream(), true);
            System.out.println("Connectat a servidor en localhost: " + PORT);
        } catch(IOException ex) {
            System.out.println(ex);
        }
    }
    public static void tanca() {
        try {
            out.close();
            socket.close();
        } catch(IOException ex) {
            System.out.println(ex);
        }
    }
    public static void envia(String mensaje) {
        out.println(mensaje);
        System.out.println("Enviat al servidor: " + mensaje);
    }
    
    public static void main(String[] args) throws Exception {
        conecta();
        envia("Prova d'enviament 1");
        envia("Prova d'enviament 2");
        envia("Adeu!");
        System.out.println("Prem Enter per tancar el client...");
        //Esperar hasta que se pulse ENTER
        BufferedReader enter = new BufferedReader(new InputStreamReader(System.in));
        enter.readLine();
        tanca();
        System.out.println("Client tancat");
    }
}
