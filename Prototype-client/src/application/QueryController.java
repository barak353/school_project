package application;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import Login.LoginController;
import Secretary.TeacherPlacementController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import ocsf.client.AbstractClient;
public class QueryController{ 
	

	private static ClientGui connection;//connection will old the connection to the server.

    private HashMap <String ,Object> packaged;//This packaged will send to the server with a query and will return back to the client with ResultArray.
   
    public static HashMap <String ,QueryController> controllerHashMap;//This will hold all the controller by their ID.
    
    private String backScreen="";
        
    private String controllerID;
    
    public QueryController(String controllerID){
    	this.controllerID = controllerID;
		controllerHashMap=new HashMap <String ,QueryController>();
    	controllerHashMap.put(controllerID, this);//Save pointer to this controller in the HashMap.
		packaged=new HashMap <String ,Object>();
    	packaged.put("controllerID",controllerID);//Send this controller ID with the packaged.
    }
    
    static void connect(String host, int port) throws IOException{//in this method we connect to the server.
			connection = new ClientGui(host, port);
    }
    
    protected Object transfferQueryToServer(String strQuery){//Send packaged to server, and wait for answer. And then return the answer.
    	packaged.put("strQuery",strQuery);//Send the query to be executed in DB to the server.
    	connection.handleMessageFromClientUI((Object)packaged);
    	synchronized(connection){//wait for ResultArray from server.
    			try{
    				connection.wait();
    			}catch(InterruptedException e){
    				e.printStackTrace();
    			}
    	}
    	Object result = packaged.get("ResultArray");//Get the resultArray that returned from the server.
    	packaged.remove("ResultArray");//Remove ResultArray from packaged.
    	return result;
    }
    
    protected void setPackaged(HashMap <String ,Object> packaged){//set packaged.
    	this.packaged=packaged;
    }
 
 	void setbackScreen(String backScreen){
 		this.backScreen=backScreen;
 	}
	//-----------------------------------------------------------------------// 	
	@FXML
	void LogOutScreen(ActionEvent event)
	{
		try {
	 			    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login/LoginWindow.fxml"));
	 		        loader.setController(new LoginController("LoginController"));
	 		        Pane login_screen_parent = loader.load();
	 				Scene login_screen_scene=new Scene(login_screen_parent);
	 				Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();//the scene that the event came from.
	 				app_stage.hide();
	 				app_stage.setScene(login_screen_scene);
	 				app_stage.show(); 
	 				} catch (IOException e) {
	 					System.err.println("Missing StudentChange.fxml file");
	 					e.printStackTrace();
	 				}
	} 
	//-----------------------------------------------------------------------//
	protected void Back(String window, Object nextController,ActionEvent event)
	{
		 try {
			   FXMLLoader loader = new FXMLLoader(getClass().getResource(window));
			   loader.setController(nextController);
			   Pane login_screen_parent = loader.load();
			        Scene login_screen_scene=new Scene(login_screen_parent);
					Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();//the scene that the event came from.
					app_stage.hide();
					app_stage.setScene(login_screen_scene);
					app_stage.show(); 
		        } catch (IOException e) {
					System.err.println("Missing BackWindow.fxml file");
					e.printStackTrace();
				}
	} 
	
	protected void finalize(){
    	controllerHashMap.put(controllerID, this);//remove this key from the HashMap.
		packaged.remove("controllerID");//remove this key from the packaged.
	}
//-----------------------------------------------------------------------//
}