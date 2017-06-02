package student;

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

	Object nextController=null;	
	
    @FXML
    private Button logout;

    @FXML
    private Button WatchPersonalFolder;

    @FXML
    private Button SubmitHomeWork;

    @FXML
    private Button back;

    @FXML
    private Button ButtonNew;

    @FXML
    private Text userID;



    @FXML
    void WatchT(ActionEvent event) {

    }

    @FXML
    void SubmitHomeWork(ActionEvent event) {

    }

    @FXML
    void WatchPersonalFolder(ActionEvent event) {

    } 
	 //-----------------------------------------------------------// 
		//-----------------------------------------------------------//
		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {//this method perform when this controller scene is showing up.
			User user = User.getCurrentLoggedIn();
			userID.setText(user.GetUserName());
		}
		@FXML
		void TurningBack(ActionEvent event)
		{
			this.nextController = new LoginController("SecretaryLoginController");
			this.Back("/Login/LoginWindow.fxml",nextController, event);
		}
 }