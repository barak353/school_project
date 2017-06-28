package application;

import java.io.IOException;

import Entity.Log;
import Login.LoginController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PortController {//Second window: connect to server...
	private final String DEFUALT_HOST="localhost";
	private final String DEFUALT_PORT="5555";


    @FXML
    private TextField hostID;

    @FXML
    private TextField portID;

    @FXML
    private Button connectID;

    @FXML
    private Text errorTextID;
    
    private boolean showNextWindow = false;//this flag will say if we stay in this scene or if go to the next scene.
    
    /**
     * This method connect with the server using port and op.
     *      * @param event
     *      @return void
     */ 
    @FXML
    void connect(ActionEvent event) {//Handler of the connect button.
    	String hostID;
    	String portID;
    	new Log();
	    	showNextWindow=true;
    		hostID=this.hostID.getText();//get host from User.
	    	portID=this.portID.getText();//get port from User.
	    	if(hostID.equals("") && portID.equals("")){//If User didn't enter port and host, then use the default host and default port..
		    		hostID=DEFUALT_HOST;
		    		portID=DEFUALT_PORT;
	    	}
	    	try {
				QueryController.connect(hostID, Integer.parseInt(portID));
			}catch(NumberFormatException e){
				errorTextID.setText("Port must contain just numbers. Please try again.");
				showNextWindow=false;
	    	} catch (IOException e1) {
				errorTextID.setText("Invalid port or host details or server is not available. Please try again.");
				showNextWindow=false;
			}//create connection with the sever.
	    if(showNextWindow==true){
		        try {//change to login scene.
			        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login/LoginWindow.fxml"));
			        loader.setController(new LoginController("LoginController"));
			        Pane login_screen_parent = loader.load();
			        Scene login_screen_scene=new Scene(login_screen_parent);
					Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();//the scene that the event came from.
					app_stage.hide();
					app_stage.setScene(login_screen_scene);
					app_stage.show(); 
		        } catch (IOException e) {//problem with the teacherWindow.xml file.
					System.err.println("Missing LoginWindows.fxml file");
					e.printStackTrace();
				}
	    }
    }
}


