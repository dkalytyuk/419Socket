import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * This class implements java socket client
 * @author pankaj
 * 
 *
 */
public class Client {

    public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException, InterruptedException{
        //get the localhost IP address, if server is running on some other IP, you need to use that
        InetAddress host = InetAddress.getLocalHost();
        Socket socket = null;
        ObjectOutputStream oos = null; //gets sent to server
        ObjectInputStream ois = null; //received from server
        
        Scanner sc = new Scanner(System.in);
        while(true) {
            System.out.println("Please enter text to send to server: ");
            String text = sc.nextLine();
            if(text.equalsIgnoreCase("exit")) {
            	break;
            }
            //establish socket connection to server
            socket = new Socket(host.getHostName(), 9876);
            //write to socket using ObjectOutputStream
            oos = new ObjectOutputStream(socket.getOutputStream());
            System.out.println("Sending request to Socket Server");
            oos.writeObject(text);
            ois = new ObjectInputStream(socket.getInputStream());
            String message = (String) ois.readObject();
            System.out.println("Server: " + message);
            ois.close();
            oos.close();
            socket.close();
            Thread.sleep(100);
            
            System.out.println("Enter another test? y/n: ");
            String more = sc.nextLine();
            if ((more.contentEquals("n")) || (more.contentEquals("N")) || 
            	(more.contentEquals("no")) || (more.contentEquals("No")) ||
            	more.equalsIgnoreCase("exit"))
            	break;
        }
        sc.close();
        System.out.println("Exiting socket server.");
        //establish socket connection to server
        socket = new Socket(host.getHostName(), 9876);
        oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject("exit");
        ois.close();
        oos.close();
        socket.close();
        Thread.sleep(100);
        System.out.println("Done");
        
    }
}