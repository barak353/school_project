// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

import java.io.*;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
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
	
	final protected static String DEFUALT_PASS="123456";//default DB password.
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
	  ResultSet rs = null;
	  HashMap <String ,Object> packaged=(HashMap <String ,Object>) msg;
	  String strQuery=(String) packaged.get("strQuery");//Get the query to be executed that send from the client.
	  packaged.remove("strQuery");
	    try {

			rs = (ResultSet) stmt.executeQuery((String) strQuery);//Execute the query from the client.
		} catch (SQLException e1) {
			System.out.println("Failed to execute query in handleMessageFromClient");
			e1.printStackTrace();
		}
		  ArrayList<String> resultArray=new ArrayList<String>();//Create result array to send to the client.
		  try {
			rs.next();
		} catch (SQLException e2) {
			System.out.println("empty result");
			e2.printStackTrace();
		}
		  try {
			  resultArray.add(rs.getString(1));
		} catch (SQLException e1) {
			System.out.println("empty rs.getString(1)");
			e1.printStackTrace();
		}
	    try{
	    	packaged.put("ResultArray", resultArray);
		client.sendToClient((Object)packaged);
	    }catch (IOException e) {
			System.out.println("Unable to send msg to client from EchoServer.");
			e.printStackTrace();
		}
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
	  int port=0;
	  boolean isOk=false;
	  while(isOk==false){
		 isOk=true;
	     Scanner scanner = new Scanner(System.in);
		 System.out.println("Welcome to the server console.");
		 System.out.println("Please enter DataBase host:");
		 String host =(scanner.next());//get the host from the user.
		 System.out.println("Please enter DataBase password:");
		 String password =(scanner.next());//get the port from the user.
		 if(host.equals(""))host=DEFUALT_HOST;
		 if(password=="")password=DEFUALT_PASS;
		 System.out.println("Please enter server port:");
		 try{
			 port =(scanner.nextInt());//get the port from the user.
		 }catch(InputMismatchException e){System.err.println("Port most contain only number's. try again.."); isOk=false;}
		 if(port==0)port=DEFAULT_PORT;
	
		  try 
			{
		        Class.forName("com.mysql.jdbc.Driver").newInstance();
		    } catch (Exception ex) {System.err.println("Failed to registering the jdbc driver. Register the jdbc driver and try again."); isOk=false;}
			try {
				if(isOk=true)
					conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/test",host,password);
			} catch (SQLException e1) {
				System.err.println("Unable to connect to SQL database. Please try other details or start your database.");
				isOk=false;
			}//create connection with mysql DB.
		try {
			if(isOk==true)
				stmt = (Statement) ((java.sql.Connection) conn).createStatement();
		} catch (SQLException e1) {
			System.err.println("failed to create Statment from Connection.");
			isOk=false;
		}
	    
	    try 
	    {
	      if(isOk==true){
			System.out.println("SQL connection succeed.");
			EchoServer sv = new EchoServer(port);
	        sv.listen(); //Start listening for connections
	        System.out.println("Server started listening to clients.");
	      }
	    } 
	    catch (Exception ex) 
	    {
	      System.out.println("ERROR - Could not listen for clients!");
	    }
  	}
  }
}
//End of EchoServer class
