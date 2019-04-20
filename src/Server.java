import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.ClassNotFoundException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This class implements java Socket server
 * Checks a message passed from a client for palindrome property (
 * 	- ignores non alphanumeric chars
 * @author David Kalytyuk
 * @author Cory Redinger
 * @author pankaj
 * original code from:
 * https://www.journaldev.com/741/java-socket-programming-server-client
 * modified for purposes of assignment
 */

public class Server {
    
    		//static ServerSocket variable
    private static ServerSocket server;
    		//socket server port on which it will listen
    private static int port = 1200; //default port number
    
    public static void main(String args[]) throws IOException, ClassNotFoundException{
    	
    	if(args.length == 1){ 
    		String [] argPort = args[0].split("="); 
    		if(argPort[0].equals("–port") && argPort.length == 2 && argPort[1].length() == 4) 
    			port = Integer.parseInt(argPort[1]);
    		else { 
    			System.out.println("\nError: Unrecognized Argument.");
    			System.out.println("\nArgument must be of the form: -port=####");
    			System.exit(1);
    		}		//Argument must be of the form -port=####
	    	if(port < 1024) {
	    		System.out.println("\nCannot use ports below 1024, try again.");
	    		System.exit(1);
	    	} 		//specified port must be greater than 1024
        } 		//specified port condition
        
        System.out.println("\nPort Number: " + port + "\n");
        		
        try { //try to create socket to check port
        	server = new ServerSocket(port);
        	server.close();
        } catch(Exception e) {
        	System.out.println("\nSpecified port is currently in use by another process, try again.");
           	System.exit(1);
        } //port is in use, terminating
        
        //end argument and port checking
        
    	//-------------------------------------------------------------------------------------------

        //create the socket server object
        server = new ServerSocket(port);
        
        //keep listens indefinitely until receives 'exit' call or program terminates
        while(true){
            System.out.println("Waiting for the client request...");
            		//creating socket and waiting for client connection
            Socket socket = server.accept();
            		//read from socket to ObjectInputStream object
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            		//convert ObjectInputStream object to String
            String message = (String) ois.readObject();
            System.out.println("Message Received: " + message + "\n");
            		//terminate the server if client sends terminate request
            if(message.equalsIgnoreCase("terminate")) { 
            	ois.close();
            	socket.close();
            	break;
            } 		//server terminate condition
            		//create ObjectOutputStream object
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());           
            		//write object to Socket
            if(isPalindrome(message))
            	oos.writeObject("Hi Client! " + message + " is a palindrome.");
            else 
            	oos.writeObject("Hi Client! " + message + " is not a palindrome.");
            		//close resources
            ois.close();
            oos.close();
            socket.close();
        } 		//listening for input
        
        System.out.println("Shutting down Socket server!");
        server.close();
        System.exit(0);
    } 		//end main
    
	//-------------------------------------------------------------------------------------------
    
    private static boolean isPalindrome(String text) {
    	text = text.toLowerCase();
    	int start = -1, end = text.length();
    	while (start < end){
    		while(!Character.isLetterOrDigit(text.charAt(++start))) {}
    		while(!Character.isLetterOrDigit(text.charAt(--end))) {}
    			
    		if(text.charAt(start) != text.charAt(end))
    			return false;
    	} 		//end loop through text
    	return true;
    } 		//end isPalindrome
    
	//-------------------------------------------------------------------------------------------

} 		//end Class
