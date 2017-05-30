package Login;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Login.LoginController;
import User.User;
import application.QueryController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ControllerExampleUseThis extends QueryController implements Initializable{
	
	//-----------------------------------------------------------//
	
	public ControllerExampleUseThis (String controllerID)
	{
			super(controllerID);
	}
	
	//-----------------------------------------------------------// 

	 @FXML
	 private Button logout;
	 
	 @FXML
	 private Button back;
	 
	 @FXML
	 private Text userID;
	 
	 @FXML
	 private Button ButtonNew;	 
	 //-----------------------------------------------------------// 
		//-----------------------------------------------------------//
		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {//this method perform when this controller scene is showing up.
			User user = User.getCurrentLoggedIn();
			userID.setText(user.GetUserName());
		}
 }
	    



	 //-----------------------------------------------------------// 


