package serverlogin;


import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.LinkedBlockingQueue;



public class ServerLogIn {
    public static int port = 8000;
    static Socket socket;
    static ServerSocket server;
    
    private static final String username = "admintest";
    private static final String password = "admintest";
    private static final String conn = "jdbc:mysql://localhost/EventScheduler";
    public static Connection con = null;
    
    public static LinkedBlockingQueue<HandleClient> scheduleQueue = new LinkedBlockingQueue<>();
    
    
    public static void main(String[] args) throws IOException, SQLException {   
        con = DriverManager.getConnection(conn,username,password);
        server = new ServerSocket(port);
        new Thread(new SchedulingHandling()).start();
        while(true){
            socket = server.accept();
            InetAddress address = socket.getInetAddress();
            System.out.println("Server connected to client: " + address.getHostName() + " with IP address: "+address.getHostAddress());            
            new Thread(new HandleClient(socket)).start();
        }
        
        
    }
    
    
}

