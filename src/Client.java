import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * This class implements java Socket socket
 * Sends a message to a server to test for palindrome property (
 * @author David Kalytyuk
 * @author pankaj
 * original code from:
 * https://www.journaldev.com/741/java-socket-programming-server-client
 * modified for purposes of assignment
 */
public class Client {

    public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException, InterruptedException{
        //get the localhost IP address, if server is running on some other IP, you need to use that
        InetAddress host = InetAddress.getLocalHost();
        int port = 1200; //default port
        
        if(args.length != 0) {
	    	host = InetAddress.getByName(args[1]);
	    	port = Integer.parseInt(args[0].substring(args[0].length() - 4));
	    	if(port < 1024) {
	    		System.out.println("\nCannot use ports below 1024, try again.");
	    		System.exit(1);
	    	} //specified port must be greater than 1024
        } //specified port and address condition
    	
        System.out.println("\nServer Name: " + host.getHostName() + 
        					"\nServer Address: " + host.getHostAddress() + 
        					"\nPort Number: " + port + "\n");
        
        Socket socket = null;
        ObjectOutputStream oos = null; //gets sent to server
        ObjectInputStream ois = null; //received from server
        Scanner sc = new Scanner(System.in);
        
        while(true) {
            System.out.println("Please enter text to send to server: ");
            String text = sc.nextLine();
            if(text.equalsIgnoreCase("exit") || (text.equals("")))
            	break; //exit case
            //establish socket connection to server
            socket = new Socket(host.getHostName(), port);
            //write to socket using ObjectOutputStream
            oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(text);
            if(text.equalsIgnoreCase("terminate")) {
            	System.out.println("\nTerminating Server!");
            	oos.close();
            	Thread.sleep(100);
            	break;
            } //server terminate request
            else {
            	System.out.println("Sending request to Socket Server");
            	ois = new ObjectInputStream(socket.getInputStream());
            	String message = (String) ois.readObject();
            	System.out.println("Server: " + message + "\n");
            	ois.close();
            	oos.close();
            	socket.close();
            	Thread.sleep(100);
            } //normal message output
            
        } //loop for client send requests
        
        System.out.println("Terminating Client!");
        sc.close();
        if(socket != null) 
        	socket.close();
        
    } //end main
   
} //end Class