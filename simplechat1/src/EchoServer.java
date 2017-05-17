// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

import java.io.*;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Statement;

import ocsf.server.*;

/**
 * This class overrides some of the methods in the abstract 
 * superclass in order to give more functionality to the server.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;re
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Paul Holden
 * @version July 2000
 */
public class EchoServer extends AbstractServer 
{
    //Constant variables *************************************************
	final protected static String DEFUALT_HOST="root";//default DB host.
	
	final protected static int DEFUALT_PASS=123456;//default DB password.
    //Class variables *************************************************
	/**
	* stmt will be the Statment for creating SQL quering.
	*/
   static Statement stmt;//This will save the Statement created in the connection to the DB.
   /**
    * This is the server connection to the DB.
    */
   protected static Connection conn=null;//This will save the connection created to the DB.

  /**
   * The default port to listen on.
   */
  final public static int DEFAULT_PORT = 5555;
  
  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the echo server.
   *
   * @param port The port number to connect on.
   */
 
  
  public EchoServer(int port) 
  {
    super(port);
  }

  
  //Instance methods ************************************************
  
  /**
   * This method handles any messages received from the client.
   *
   * @param msg The message received from the client.
   * @param client The connection from which the message originated.
   */
  public void handleMessageFromClient
    (Object msg, ConnectionToClient client)
  {		
	  System.out.println("4");
	  ResultSet rs = null;
	    try {
	  	  System.out.println("4.1");

			rs = (ResultSet) stmt.executeQuery((String) msg);
		} catch (SQLException e1) {
			System.out.println("Failed to execute query in handleMessageFromClient");
			e1.printStackTrace();
		}
		  System.out.println("4.3");
		  ArrayList<String> arr=new ArrayList<String>();
		  try {
			rs.next();
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		  try {
			arr.add(rs.getString(1));
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    try{
		client.sendToClient(arr);
		  System.out.println("4.4");

	    }catch (IOException e) {
			System.out.println("Unable to send msg to client from EchoServer.");
			e.printStackTrace();
		}
	  //  this.sendToAllClients(msg);
  }

    
  /**
   * This method overrides the one in the superclass.  Called
   * when the server starts listening for connections.
   */
  protected void serverStarted()
  {
    System.out.println
      ("Server listening for connections on port " + getPort());
  }
  
  /**
   * This method overrides the one in the superclass.  Called
   * when the server stops listening for connections.
   */
  protected void serverStopped()
  {
    System.out.println
      ("Server has stopped listening for connections.");
  }
  
  //Class methods ***************************************************
  
  /**
   * This method is responsible for the creation of 
   * the server instance (there is no UI in this phase).
   *
   * @param args[0] The port number to listen on.  Defaults to 5555 
   *          if no argument is entered.
   */
  public static void main(String[] args) 
  {
     Scanner scanner = new Scanner(System.in);
	 System.out.println("Welcome to the server console.");
	 System.out.println("Please enter DataBase host:");
	 String host =(scanner.next());//get the host from the user.
	 System.out.println("Please enter DataBase password:");
	 int password =(scanner.nextInt());//get the port from the user.
	 if(host.equals(""))host=DEFUALT_HOST;
	 if(password==0)password=DEFUALT_PASS;
	 System.out.println("Please enter server port:");
	 int port =(scanner.nextInt());//get the port from the user.
	 if(password==0)password=DEFAULT_PORT;

	  try 
		{
	        Class.forName("com.mysql.jdbc.Driver").newInstance();
	    } catch (Exception ex) {System.err.println("Failed to registering the jdbc driver.");ex.printStackTrace();}
		try {
			conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/test","root","123456");
			
		} catch (SQLException e1) {
			System.err.println("Unable to connect to SQL database.");
			e1.printStackTrace();
		}//create connection with mysql DB.
	try {
		stmt = (Statement) ((java.sql.Connection) conn).createStatement();
	} catch (SQLException e1) {
		System.out.println("failed to create Statment from Connection.");
		e1.printStackTrace();
	}
   System.out.println("SQL connection succeed.");
	
    EchoServer sv = new EchoServer(port);
    
    try 
    {
      sv.listen(); //Start listening for connections
      System.out.println("Server started listening to clients.");
    } 
    catch (Exception ex) 
    {
      System.out.println("ERROR - Could not listen for clients!");
    }
  }
}
//End of EchoServer class
