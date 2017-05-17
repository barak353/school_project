package application;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;

import client.ClientGui;
public class ControllerQuery{ 

    public static boolean thereIsAnswer=false;

    private static ClientGui chat;
    
    private static int getNumberInQueue=0;//Get a number to rep

    public static ArrayBlockingQueue < HashMap< ArrayList<String> ,Integer> > querysResultBuffer;
   
    static void connect(String host, int port) throws IOException{//in this method we connect to the server.
			chat = new ClientGui(host, port);
		
   // }
    //static ArrayList<String> sendQueryToServer(String strQuery){//,char chooseEntity,char entityTable){
    //	getNumberInQueue++;
    	//chat.handleMessageFromClientUI(strQuery);
    	//if(querysResultBuffer.isEmpty()){Thread.sleep(3000);}//waiting for 10 second query to return from server.
    	//else {return null;}//query didn't returned for server for 10 seconds.
    	//return querysResultBuffer.take(0);//return the 	
    	System.out.println("1");
  	  System.out.println("6");
  	 // System.out.println(msg.getClass().getSimpleName());
  	  //ArrayList<String> arr=(ArrayList<String>)msg;
  	  //System.out.println(arr.get(0));//prints password to compare with. 
      //clientUI.display(msg.toString());
		
    	
    	

    }
}

