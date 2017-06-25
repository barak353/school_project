package application;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import org.apache.commons.io.FileUtils;

import Login.LoginController;
import Secretary.AddStudentToClassController;
import Secretary.SecretaryMainController;
import Secretary.TeacherPlacementController;
import Entity.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ocsf.client.AbstractClient;
public class QueryController{ 

	private static ClientGui connection;//connection will old the connection to the server.

    private HashMap <String ,Object> packaged;//This packaged will send to the server with a query and will return back to the client with ResultArray.
   
    public static HashMap <String ,QueryController> controllerHashMap = new HashMap <String ,QueryController>();;//This will hold all the controller by their ID.
    
    private String backScreen="";
        
    protected String controllerID;

    public QueryController(String controllerID){
    	this.controllerID = controllerID;
		//controllerHashMap=new HashMap <String ,QueryController>();
    	controllerHashMap.remove("controllerID");
    	controllerHashMap.remove(controllerID);
    	controllerHashMap.put(controllerID, this);//Save pointer to this controller in the HashMap.
		packaged=new HashMap <String ,Object>();
    	packaged.put("controllerID",controllerID);//Send this controller ID with the packaged.
    }
    
    /**
     * This method gets a String the represents the folder name and a String that represent the file name.
     * With this two Strings the method gets the file name in the folder name and delete the file and return 0 on failed and the deleted File on success.
     *      * @param event
     */ 
    protected Object deleteFileFromServer(String folder,String fileName){
    	String filePath = folder + "//" + fileName;
    	packaged.put("file","empty");
    	packaged.put("key","DELETE_FILE");
    	packaged.put("filePath",filePath);
    	connection.handleMessageFromClientUI((Object)packaged);
    	synchronized(connection){//wait for ResultArray from server.
    		try{
    			connection.wait();
    		}catch(InterruptedException e){
    			e.printStackTrace();
    		}
    	}
    	packaged.remove("key");    	
    	packaged.remove("filePath");
    	Object result = packaged.get("file");
    	packaged.remove("file");
    	return result;
    }
    /**
     * This method gets a String the represents the folder name and a String that represent the file name.
     * With this two Strings the method gets the file name in the folder name and return the file as a File.
     *      * @param event
     */ 
    protected File downloadFileFromServer(String folder,String fileName){
    	String filePath = folder + "//" + fileName;
    	packaged.put("file","empty");
    	packaged.put("key","DOWNLOAD");
    	packaged.put("filePath",filePath);
    	connection.handleMessageFromClientUI((Object)packaged);
    	synchronized(connection){//wait for ResultArray from server.
    		try{
    			connection.wait();
    		}catch(InterruptedException e){
    			e.printStackTrace();
    		}
    	}
    	packaged.remove("key");    	
    	byte[] bytes = (byte[]) packaged.get("file");
		try {
			FileUtils.writeByteArrayToFile(new File("download//"+packaged.get("filePath")), bytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
		packaged.remove("filePath");
    	File file = null;
    	packaged.remove("file");
    	return file;
    }
    /**
     * This method gets a File and a String that represent the folder name to save the File in the server.
     * @param event
     */ 
    protected Object uploadFileToServer(File file,String folderName){//Send packaged to server, and wait for answer. And then return the answer.
        // Get the size of the file
    	String fileName = file.getName();
    	String fileType = fileName.split("\\.")[1];
    	fileName = fileName.split("\\.")[0];
    	packaged.put("key","upload");
    	packaged.put("fileName",fileName);
    	packaged.put("fileType",fileType);
        byte[] bytes = null;
		try {
			bytes = Files.readAllBytes(file.toPath());
		} catch (IOException e1) {
			System.err.println("Unable to convert from file to byte[]");
			e1.printStackTrace();
		}
    	packaged.put("file",bytes);//Send file to be save in the server.
    	packaged.put("folderName",folderName);//the folder name that the file will be save.
    	connection.handleMessageFromClientUI((Object)packaged);
    	synchronized(connection){//wait for ResultArray from server.
    		try{
    			
    			connection.wait();
    		}catch(InterruptedException e){
    			e.printStackTrace();
    		}
    	}
    	packaged.remove("fileName");
    	packaged.remove("fileType");
    	Object result = packaged.get("file");//Get the result that returned from the server.
    	packaged.remove("file");//Remove result from packaged.
    	packaged.remove("folderName");
    	return result;
    }
    /**
     * A static method that connect the client to the server.
     * @param event
     */ 
    static void connect(String host, int port) throws IOException{//in this method we connect to the server.
			connection = new ClientGui(host, port);
    }
    /**
     * gets Query as a String to be excuted in the server, return Object as answer.
     * @param event
     */ 
    protected Object transfferQueryToServer(String strQuery){//Send packaged to server, and wait for answer. And then return the answer.
    	packaged.put("key","Query");
    	packaged.put("strQuery",strQuery);//Send the query to be executed in DB to the server.
    	connection.handleMessageFromClientUI((Object)packaged);
    	synchronized(connection){//wait for ResultArray from server.
    			try{
    				connection.wait();
    			}catch(InterruptedException e){
    				e.printStackTrace();
    			}
    			connection.setFlagFalse();
    	}
    	Object result = packaged.get("ResultArray");//Get the resultArray that returned from the server.
    	packaged.remove("strQuery");//Remove strQuery from packaged.
    	packaged.remove("ResultArray");//Remove ResultArray from packaged.
    	packaged.remove("key");
    	return result;
    }
    
    
    protected void setPackaged(HashMap <String ,Object> packaged){//set packaged.
    	this.packaged=packaged;
    }
 
 	void setbackScreen(String backScreen){
 		this.backScreen=backScreen;
 	}
 	
    /**
     * function that return to the log in screen
     * @param event
     */ 
	@FXML
	protected void LogOutScreen(ActionEvent event)
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
		ArrayList<ArrayList<String>> status = (ArrayList<ArrayList<String>>) transfferQueryToServer("UPDATE user SET status = 00 WHERE userID="+User.getCurrentLoggedIn().GetID());
		User.setCurrentLoggedIn(null);
	} 
	//-----------------------------------------------------------------------//
    /**
     * This method gets window name and controller Object and an event.
     * The method change the main Stage to show this window with his nextController. 
     *      * @param event
     */ 
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
	
    /**
     * This method finalize the QueryController class.
     *      * @param event
     */ 
	protected void finalize(){
    	controllerHashMap.remove("controllerID");
    	controllerHashMap.remove(controllerID);
	}
	

//-----------------------------------------------------------------------//
}