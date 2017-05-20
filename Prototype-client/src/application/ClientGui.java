// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

package application;

import ocsf.client.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class overrides some of the methods defined in the abstract
 * superclass in order to give more functionality to the client.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;
 * @author Fran&ccedil;ois B&eacute;langer
 * @version July 2000
 */
public class ClientGui extends AbstractClient
{
  //Instance variables **********************************************
  
  /**
   * The interface type variable.  It allows the implementation of 
   * the display method in the client.
   */

  
  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the chat client.
   *
   * @param host The server to connect to.
   * @param port The port number to connect on.
   * @param clientUI The interface type variable.
   */
  
  public ClientGui(String host, int port) 
    throws IOException 
  {
    super(host, port); //Call the superclass constructor
    openConnection();
  }

  
  //Instance methods ************************************************
    
  /**
   * This method handles all data that comes in from the server.
   *
   * @param msg The message from the server.
   */
  public void handleMessageFromServer(Object msg) 
  {
	  	if(AbstractClient.ac.clientSocket==null)System.out.println("handleMessageFromServer: AbstractClient.clientSocket=null");
	  	else
	  		System.out.println("handleMessageFromServer: AbstractClient.clientSocket=not null");
	  HashMap <String ,Object> packaged=(HashMap <String ,Object>) msg;//the returned packaged from server.
	  String controllerID=(String)packaged.get("controllerID");//Get the controller ID that send this packaged to server.
	  QueryController cq = (QueryController)QueryController.controllerHashMap.get(controllerID);//Get the controller that send this packaged to server.
	  //ArrayList<ArrayList<String>> resultArray=(ArrayList<ArrayList<String>>) packaged.get("ResultArray");//Get the resultArray that returned from the server.
		System.out.println("handleMessageFromServer:1");
	  cq.setPackaged(packaged);
	  cq.setIsAnswered(true);	  
	  	if(AbstractClient.ac.clientSocket==null)System.out.println("handleMessageFromServer: AbstractClient.clientSocket=null");
	  	else
	  		System.out.println("handleMessageFromServer: AbstractClient.clientSocket=not null");
  }

  /**
   * This method handles all data coming from the UI            
   *
   * @param packaged The message from the UI.    
   */
  public void handleMessageFromClientUI(Object packaged)
  {
  	if(AbstractClient.ac.clientSocket==null)System.out.println("handleMessageFromClientUI:AbstractClient.clientSocket=null");
  	else
  		System.out.println("handleMessageFromClientUI:AbstractClient.clientSocket=not null");
    try
    {
    	sendToServer(packaged);
    }
    catch(IOException e)
    {
    	System.out.println("Error send packaged to server from handleMessageFromClientUI");
    	e.printStackTrace();
    }
  }
  
  /**
   * This method terminates the client.
   */
  public void quit()
  {
    try
    {
      closeConnection();
    }
    catch(IOException e) {}
    System.exit(0);
  }
}
//End of ClientGui class
