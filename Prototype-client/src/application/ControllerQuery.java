package application;
import java.io.IOException;

import client.ClientGui;
public class ControllerQuery{ 

    public static boolean thereIsAnswer=false;

    private static ClientGui chat;

    static void connect(String host, int port) throws IOException{//in this method we connect to the server.
			chat = new ClientGui(host, port);
		
    }
    static void sendQueryToServer(String strQuery){//,char chooseEntity,char entityTable){
    	
    	chat.handleMessageFromClientUI(strQuery);
    	
    	

    }
}

