package application;
// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

import java.io.*;
import java.nio.file.Files;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

import javax.swing.JFileChooser;

import org.apache.commons.io.FileUtils;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.ResultSetMetaData;
import com.mysql.jdbc.Statement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
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
public class SchoolServer extends AbstractServer 
{
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
 
  protected static PrintWriter writer;
  
  
  protected static Date date;// will use for showing date in the log.txt file.
  
  public SchoolServer(int port) 
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
  public void handleMessageFromClient(Object msg, ConnectionToClient client)
{		
	  HashMap <String ,Object> packaged=(HashMap <String ,Object>) msg;
	  String option = (String) packaged.get("key");
	  System.out.println("handleMessageFromClient");
	  Object result = null;
	  switch(option){
	  case "Query":
		  boolean isAlreadyInserted = false;
		  ResultSet rs = null;
		  boolean isUpdate=false;
		  String strQuery=(String) packaged.get("strQuery");//Get the query to be executed that sent from the client.
		  System.out.println("executing query: "+strQuery);
			writer.println("executing query: "+strQuery);
			StringWriter err = new StringWriter();
		    writer.println(err.toString());
		  packaged.remove("strQuery");
		  String queryType = strQuery.substring(0,6);
		  try {
			  System.out.println("queryType: "+queryType);
			  switch(queryType){
			  case "SELECT":
				  if(strQuery.substring(0,6).equals("SELECT"))
			    	{
			    		rs = (ResultSet) stmt.executeQuery(strQuery);//Execute the query from the client.
			    	}
			    	else if(strQuery.substring(0,6).equals("UPDATE"))
			    	{
			    		stmt.executeUpdate(strQuery);//Execute the query from the client.
									isUpdate=true;
			    	}
				  break;
			  case "INSERT":
			  try{
	    			System.out.println("insert");

		    		stmt.executeUpdate(strQuery);
		    		result = 0;//query succeed.
		    		}catch(SQLException e2){
		    			isAlreadyInserted = true ;
		    		}
		    		isUpdate=true;
			  break;
			  case "DELETE":
		    		try{
		    			System.out.println("delete");
		    		stmt.executeUpdate(strQuery);
		    		result = 0;//query succeed.
		    		}catch(SQLException e2){
		    			isAlreadyInserted = true ;
		    		}
		    		isUpdate=true;
			  break;
			  default:
					writer.println(date+": Query Type is not exist.");
					StringWriter errors = new StringWriter();
					writer.println(errors.toString());
					return;
			  }
		  }catch (SQLException e1) {
				writer.println(date+": Failed to execute query in handleMessageFromClient");
				StringWriter errors = new StringWriter();
				e1.printStackTrace(new PrintWriter(errors));
				writer.println(errors.toString());
				e1.printStackTrace();
			}
		      ArrayList<ArrayList<String>> resultArray;
			  if(isUpdate == false){
			  resultArray=new ArrayList<ArrayList<String>>();//Create result array to send to the client.
				  ResultSetMetaData rsmd;
				int columnsNumber;
				try {
					rsmd = (ResultSetMetaData) rs.getMetaData();
					columnsNumber = rsmd.getColumnCount();
				} catch (SQLException e3) {
					columnsNumber=-1;// there is no Columns.
					writer.println(date+": result is empty, there is no Columns.");
					StringWriter errors = new StringWriter();
					e3.printStackTrace(new PrintWriter(errors));
					writer.println(errors.toString());
				}
				 int columnIndex;
				 ArrayList<String> arr;
			     try {
				    while(rs.next()){//while there is still rows in the ResultSet.
					    ArrayList<String> resultRow=new ArrayList<String>();//create new resultRow
					    columnIndex=1;//set to point to the first column.
					    while(columnIndex<=columnsNumber){//while there is still column to copy in the row.
						    resultRow.add(rs.getString(columnIndex));//get the String of this row in column number columnIndex number.
						    columnIndex++;//go to the next column in this row.
					    }
					    resultArray.add(resultRow);//add the resultRow to the ResultArray		
					    result = resultArray;
				  }
				} catch (SQLException e1) {
					writer.println(date+": error in creating ResultArray");
					StringWriter errors = new StringWriter();
					e1.printStackTrace(new PrintWriter(errors));
					writer.println(errors.toString());
				}
			  }else{
				  result = null;
			  }
			  if( isAlreadyInserted == true){
				  result = new Integer(-1);
			  }
		    try{
		    	packaged.put("ResultArray", result);
		    	client.sendToClient((Object)packaged);
		    }catch (IOException e) {
				writer.println(date+": Unable to send Select msg to client from Server.");
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				writer.println(errors.toString());
			}
			writer.flush();
	  break;
	  case "upload":
		  byte[] bytes = (byte[]) packaged.get("file");		  
		  try {
			FileUtils.writeByteArrayToFile(new File("file//"+packaged.get("folderName")+"//"+packaged.get("fileName")+"."+packaged.get("fileType")), bytes);
		} catch (IOException e1) {
			writer.println(date+": Unable to save file from server to pc.");
			StringWriter errors = new StringWriter();
			e1.printStackTrace(new PrintWriter(errors));
			writer.println(errors.toString());
		}
	    try {
			client.sendToClient((Object)packaged);
		} catch (IOException e) {
			writer.println(date+": Can't send to client.");
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			writer.println(errors.toString());
		}
	  break;
	  case "download":
		 /* System.out.println("arrived download");
		  JFileChooser chooser = new JFileChooser();
		  File dir = new File("file");
		  chooser.setCurrentDirectory(dir);
		  File[] filesInDirectory = chooser.getCurrentDirectory().listFiles();
		  for ( File file : filesInDirectory ) {
		      System.out.println(file.getName());
		  }*/
	   break;
	  default:
			writer.println(date+": Option is not available.");
			StringWriter errors = new StringWriter();
			writer.println(errors.toString());  
	 break;  	
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

  protected static Stage stage;
  
  static void setStage(Stage stg){
  	stage = stg;
  }

  public static void startServer(PortController portController) {
    int port = 0;
	String DBuser = portController.DBuserID.getText();//get the DB user name.
	String DBpassword = portController.DBpasswordID.getText();//get the DB password.
	String DBhost = portController.DBhostID.getText();//get the DB host.
    String DBschema = portController.DBschemaID.getText();//get the DB schema.
    //set date and writer.
    date = new Date();
	try {
		writer = new PrintWriter("log.txt", "UTF-8");
	} catch (FileNotFoundException e) {
		System.out.println("FileNotFoundException");		
	} catch (UnsupportedEncodingException e) {
		System.out.println("UnsupportedEncodingException");
	}
    try{
	port = Integer.parseInt(portController.serverPortD.getText());//get the port of the server to be listening on.
	}catch(RuntimeException e){
		portController.errorTextID.setText("Server port most contain only digit's.");
		writer.println(date+": Error: **begining of crash** Server port most contain only digit's: ");
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));
		writer.println(errors.toString());
		writer.println(date+": Error: **end of crash**");
		return;
	}
	try 
	{
		Class.forName("com.mysql.jdbc.Driver").newInstance();
	} catch (Exception ex) {
		portController.errorTextID.setText("Failed to registering the jdbc driver. Register the jdbc driver and try again."); 
		writer.println(date+": Error: **begining of crash** Failed to registering the jdbc driver. Register the jdbc driver and try again: ");
		StringWriter errors = new StringWriter();
		ex.printStackTrace(new PrintWriter(errors));
		writer.println(errors.toString());
		writer.println(date+": Error: **end of crash**");	
		return;
	}
	try {
		conn = (Connection) DriverManager.getConnection("jdbc:mysql://"+DBhost+"/"+DBschema,DBuser,DBpassword);
	} catch (SQLException e1) {
		portController.errorTextID.setText("Unable to connect to SQL database. Please try other details or start your database.");
		writer.println(date+": Error: **begining of crash** Unable to connect to SQL database. Please try other details or start your database: ");
		StringWriter errors = new StringWriter();
		e1.printStackTrace(new PrintWriter(errors));
		writer.println(errors.toString());
		writer.println(date+": Error: **end of crash**");	
		return;
	}//create connection with mysql DB.
	try {
		stmt = (Statement) ((java.sql.Connection) conn).createStatement();
	} catch (SQLException e1) {
		portController.errorTextID.setText("failed to create Statment from Connection.");
		writer.println(date+": Error: **begining of crash** failed to create Statment from Connection: ");
		StringWriter errors = new StringWriter();
		e1.printStackTrace(new PrintWriter(errors));
		writer.println(errors.toString());
		writer.println(date+": Error: **end of crash**");		
		return;
	}
	try 
	{
		writer.println(date+": SQL connection succeed.");
		SchoolServer sv = new SchoolServer(5555	);
		sv.listen(); //Start listening for connections
		writer.println(date+": Server started listening to clients on port: "+port);
	} 
	catch (Exception ex) 
	{
		portController.errorTextID.setText("ERROR - Could not listen for clients!");
		return;
	}
	writer.flush();
	stage.close();
	}
}
//End of Server class
