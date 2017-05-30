package Login;

import java.io.IOException;
import Login.LoginController;
import application.QueryController;
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

public class screenController extends QueryController{
	
	//-----------------------------------------------------------//
	
	public screenController (String controllerID)
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

 }
	    



	 //-----------------------------------------------------------// 


