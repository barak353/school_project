// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

import java.io.*;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

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
    //Class variables *************************************************
	/**
	* stmt will be the Statment for creating SQL quering.
	*/
   static Statement stmt;
  //Class variables *************************************************
  
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
	  try 
	{
      Class.forName("com.mysql.jdbc.Driver").newInstance();
  } catch (Exception ex) {/* handle the error*/}
	    System.out.println("Message received: " + msg + " from " + client);
	    ResultSet s = null;
	    try {
	    	msg=msg+" server ";
			Statement stmt;
			try {
	            Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/test","root","123456");

				stmt = (Statement) con.createStatement();
		    	//s=(ResultSet) stmt.executeQuery("");

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			client.sendToClient(new ArrayList<String>().add("Barak"));
		} catch (IOException e) {
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
    int port = 0; //Port to listen on
	 Connection conn=null;
	  try 
		{
	        Class.forName("com.mysql.jdbc.Driver").newInstance();
	    } catch (Exception ex) {System.out.println("failed to registering the jdbc driver.");}
		try {
			conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/test","root","123456");
			
		} catch (SQLException e1) {
			System.out.println("Unable to connect to SQL database.");
			e1.printStackTrace();
		}//create connection with mysql DB.
	try {
		stmt = (Statement) ((java.sql.Connection) conn).createStatement();
	} catch (SQLException e1) {
		System.out.println("failed to create Statment from Connection.");
		e1.printStackTrace();
	}
   System.out.println("SQL connection succeed.");

    try
    {
      port = Integer.parseInt(args[0]); //Get port from command line
    }
    catch(Throwable t)
    {
      port = DEFAULT_PORT; //Set port to 5555
    }
	
    EchoServer sv = new EchoServer(port);
    
    try 
    {
      sv.listen(); //Start listening for connections
    } 
    catch (Exception ex) 
    {
      System.out.println("ERROR - Could not listen for clients!");
    }
  }
}
//End of EchoServer class
