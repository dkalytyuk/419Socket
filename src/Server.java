import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.ClassNotFoundException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This class implements java Socket server
 * Checks a message passed from a client for palindrome property (
 * 	- ignores non alphanumeric chars
 * @author David Kalytyuk
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
        if(args.length != 0) {
	    	port = Integer.parseInt(args[0].substring(args[0].length() - 4));
	    	if(port < 1024) {
	    		System.out.println("\nCannot use ports below 1024, try again.");
	    		System.exit(1);
	    	} //specified port must be greater than 1024
        } //specified port condition
        System.out.println("\nPort Number: " + port + "\n");
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
            } //server terminate condition
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
        } //listening for input
        System.out.println("Shutting down Socket server!");
        server.close();
    } //end main
    
    private static boolean isPalindrome(String text) {
    	int start = 0;
    	int end = text.length()-1;
    	boolean palindrome = true;
    	while (start < end) {
    		while(!Character.isLetterOrDigit(text.charAt(start)))
    			start++;
    		while(!Character.isLetterOrDigit(text.charAt(end)))
    			end--;
    		if(text.charAt(start) != text.charAt(end)) {
    			palindrome = false;
    			break;
    		}
    		start++;
    		end--;
    	} //end loop through text
    	return palindrome;
    } //end isPalindrome
    
} //end Class