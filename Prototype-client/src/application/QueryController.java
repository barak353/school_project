package application;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
public class QueryController{ 
	

    private boolean isAnswered=false;

    private static ClientGui chat;//Chat will old the connection to the server.
    

    private HashMap <String ,Object> packaged;//This packaged will send to the server with a query and will return back to the client with ResultArray.
   
    public static HashMap <String ,QueryController> controllerHashMap;//This will hold all the controller by their ID.
    
    public QueryController(String controllerID){
		controllerHashMap=new HashMap <String ,QueryController>();
    	controllerHashMap.put(controllerID, this);//Save pointer to this controller in the HashMap.
		packaged=new HashMap <String ,Object>();
    	packaged.put("controllerID",controllerID);//Send this controller ID with the packaged.
    }
    
    static void connect(String host, int port) throws IOException{//in this method we connect to the server.
			chat = new ClientGui(host, port);
			

    }
    
    protected ArrayList<ArrayList<String>> transfferQueryToServer(String strQuery){//Send packaged to server, and wait for answer. And then return the answer.
    	packaged.put("strQuery",strQuery);//Send the query to be executed in DB to the server.
    	chat.handleMessageFromClientUI((Object)packaged);
    	
    	while(isAnswered==false){System.out.print("");}//wait for ResultArray from server.
    	
    	ArrayList<ArrayList<String>> resultArray=(ArrayList<ArrayList<String>>) packaged.get("ResultArray");//Get the resultArray that returned from the server.      
    	packaged.remove("ResultArray");//Remove ResultArray from packaged.
    	packaged.remove("strQuery");//Remove strQuery from packaged.
        //System.out.println("resultArray.get(0): "+resultArray.get(0));
    	return resultArray;
    }
    
    protected void setIsAnswered(boolean isAnswered){//set isAnswered.
    	this.isAnswered=isAnswered;
    }
    
    protected void setPackaged(HashMap <String ,Object> packaged){//set packaged.
    	this.packaged=packaged;
    }
}

