import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.ClassNotFoundException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This class implements java Socket server
 * @author pankaj
 *
 */
public class Server {
    
    //static ServerSocket variable
    private static ServerSocket server;
    //socket server port on which it will listen
    private static int port = 9876;
    
    public static void main(String args[]) throws IOException, ClassNotFoundException{
        //create the socket server object
        server = new ServerSocket(port);
        //keep listens indefinitely until receives 'exit' call or program terminates
        while(true){
            System.out.println("Waiting for the client request");
            //creating socket and waiting for client connection
            Socket socket = server.accept();
            //read from socket to ObjectInputStream object
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            //convert ObjectInputStream object to String
            String message = (String) ois.readObject();
            System.out.println("Message Received: " + message);
            //terminate the server if client sends exit request
            if(message.equalsIgnoreCase("exit")) 
            	break;
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
        }
        System.out.println("Shutting down Socket server!!");
        //close the ServerSocket object
        server.close();
    }
    
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
    
}