import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    public static final int PORT = 7777;
    public static final String HOST = "localhost";

    public static int getPort() {
        return PORT;
    }

    public static String getHost() {
        return HOST;
    }
    static ServerSocket srvSocket;
    static Socket clientSocket;

    public static void connecta() {
        //Abre la conexión y acepta la conexión
        try {
            clientSocket = srvSocket.accept();
            System.out.println("Cliente connectat: /127.0.0.1" );
        } catch(IOException ex) {
            System.out.println(ex);
        }
    }

    public static void repDades() {
        try {
            BufferedReader inr = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String mensaje;
            while((mensaje = inr.readLine()) != null) {
                System.out.println("Rebut: " + mensaje);
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
    public static void tanca() {
        try {
            clientSocket.close();
            srvSocket.close();
            System.out.println("Servidor tancat.");
        } catch(IOException ex) {
            System.out.println(ex);
        }
    }
    public static void main(String[] args) throws Exception {
        System.out.println("Servidor en marxa a localhost: " + PORT);
        System.out.println("Esperant connexions a localhost: " + PORT);
        srvSocket = new ServerSocket(PORT);
        connecta();
        repDades();
        tanca();
    }
}