package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PortScreen {//Second window: connect to server...
	private final String host="localhost";
	private final String port="5555";


    @FXML
    private TextField hostID;

    @FXML
    private TextField portID;

    @FXML
    private Button connectID;

    @FXML
    private Text errorTextID;
    
    private boolean showNextWindow = true;//this flag will say if we stay in this scene or if go to the next scene.
    @FXML
    void connect(ActionEvent event) {//Handler of the connect button.
    	String hostID=this.hostID.getText();//get host from user.
    	String portID=this.portID.getText();//get port from user.
    	String[] args=new String[2];

    	if(hostID.equals("") || portID.equals("")){//If user didn't enter port or host, then use the default host and default port..
    		hostID=host;
    		portID=port;
    	}
    	
    	try {
			ControllerQuery.connect(hostID, Integer.parseInt(portID));
		}catch(NumberFormatException e){
			errorTextID.setText("Port most contain just numbers. Please try again.");
			showNextWindow=false;
    	} catch (IOException e1) {
			errorTextID.setText("Invalid port or host details or server is not available. Please try again.");
			showNextWindow=false;
		}//create connection with the sever.

    	if(showNextWindow){//if required field are ok then preform there code, else stay in these scene.
	        try {//change to login scene.
	        	Parent login_screen_parent=FXMLLoader.load(getClass().getResource("loginScreen.fxml"));
				Scene login_screen_scene=new Scene(login_screen_parent);
				Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();//the scene that the event came from.
				app_stage.hide();
				app_stage.setScene(login_screen_scene);
				app_stage.show(); 
	        } catch (IOException e) {//problem with the teacherWindow.xml file.
				System.out.println("Missing teacherWindow.fxml file");
				e.printStackTrace();
			}
    	}
    }


}

