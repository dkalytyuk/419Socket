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
 * @author Cory Redinger
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
        
    	if(args.length == 2){ 
    		host = InetAddress.getByName(args[1]);
    		String [] argPort = args[0].split("=");
    		if((argPort[0].equals("-port")) && (argPort.length == 2) && (argPort[1].length() == 4))
    			port = Integer.parseInt(argPort[1]);
    		else { 
    			System.out.println("\nError: Unrecognized port argument.");
    			System.out.println("\nPort argument must be of the form: -port=####");
    			System.exit(1);
    		}		//Argument must be of the form -port=####
        } 		//specified port and host condition  
    	
    	else if(args.length == 1){ 
    		String [] argPort = args[0].split("="); 
    		if(argPort[0].equals("–port") && argPort.length == 2 && argPort[1].length() == 4) 
    			port = Integer.parseInt(argPort[1]);
    		else host = InetAddress.getByName(args[0]);
        } 		//specified port or host condition
    	
    	else if(args.length > 2) {
    		System.out.println("\nInvalid: Format input must be -port=#### <server address>");
    		System.exit(1);
    	}
    	
    	if(port < 1024) {
    		System.out.println("\nCannot use ports below 1024, try again.");
    		System.exit(1);
    	} 		//specified port must be greater than 1024
    	        
        //end argument and port checking

    	//-------------------------------------------------------------------------------------------
      
    	Socket socket = null;
        ObjectOutputStream oos = null; //gets sent to server
        ObjectInputStream ois = null; //received from server
        Scanner sc = new Scanner(System.in);
        
        System.out.println("\nServer Name: " + host.getHostName() + 
        					"\nServer Address: " + host.getHostAddress() + 
        					"\nPort Number: " + port + "\n");
        
        System.out.println("\n- Hit enter key or type 'exit' to terminate client"
        		+ "\n-Send 'terminate' to terminate both the client and the server\n");
        
        while(true) {
        	
            System.out.println("Enter text:");
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
        System.exit(0);
    	
    } //end main
   
} //end Class