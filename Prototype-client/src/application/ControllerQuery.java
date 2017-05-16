package application;
import ocsf.client.*;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import ocsf.client.ObservableClient;

//All controllers that needs access to the DB, will extends this class.
public class ControllerQuery extends Observable{
    //this client will connect to the server in the LoginScreen class.
    private final static ObservableClient oc=new ObservableClient("127.0.0.2",5555);//connect to the server, that already start to listening to a client.
    @Override
    public synchronized void addObserver(Observer o) {
    	super.addObserver(o);
    }
    ControllerQuery(){
    }
    void sendQueryToServer(String strQuery,char chooseEntity,char entityTable){
    	try {
			if(oc.isConnected()){//do nothing.
			}else{
				System.out.println("Client connecting to server...");
				oc.openConnection();			}
		} catch (IOException e) {
			System.out.println("Unable to send Query to server.");
			e.printStackTrace();
		}
    	System.out.println("chooseEntity:"+chooseEntity);
    	switch(chooseEntity){
    	case 'U':
			try {
				oc.sendToServer(strQuery);//this message will pass trough AdaptableServer.handleMessageFromClient. And then will get to ObservableServer.handleMessageFromClient
			} catch (IOException e) {
				System.out.println("Unable to send query to server from sendQueryToServer");
				e.printStackTrace();
			}
			System.out.println("finish");
    	}
    }
}

