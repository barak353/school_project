package application;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import ocsf.client.AbstractClient;
public class QueryController{ 
	
    private static ClientGui connection;//connection will old the connection to the server.

    private HashMap <String ,Object> packaged;//This packaged will send to the server with a query and will return back to the client with ResultArray.
   
    public static HashMap <String ,QueryController> controllerHashMap;//This will hold all the controller by their ID.
    
    public QueryController(String controllerID){
		controllerHashMap=new HashMap <String ,QueryController>();
    	controllerHashMap.put(controllerID, this);//Save pointer to this controller in the HashMap.
		packaged=new HashMap <String ,Object>();
    	packaged.put("controllerID",controllerID);//Send this controller ID with the packaged.
    }
    
    static void connect(String host, int port) throws IOException{//in this method we connect to the server.
			connection = new ClientGui(host, port);
    }
    
    protected ArrayList<ArrayList<String>> transfferQueryToServer(String strQuery){//Send packaged to server, and wait for answer. And then return the answer.
    	packaged.put("strQuery",strQuery);//Send the query to be executed in DB to the server.
    	connection.handleMessageFromClientUI((Object)packaged);
    	synchronized(connection){//wait for ResultArray from server.
    			try{
    				connection.wait();
    			}catch(InterruptedException e){
    				e.printStackTrace();
    			}
    	}
    	ArrayList<ArrayList<String>> resultArray=(ArrayList<ArrayList<String>>) packaged.get("ResultArray");//Get the resultArray that returned from the server.    
    	packaged.remove("ResultArray");//Remove ResultArray from packaged.
    	return resultArray;
    }
    
    protected void setPackaged(HashMap <String ,Object> packaged){//set packaged.
    	this.packaged=packaged;
    }
}

