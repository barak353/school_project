package Login;

import java.net.URL;
import java.util.ResourceBundle;
import Login.LoginController;
import User.User;
import application.QueryController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class ControllerExampleUseThis extends QueryController implements Initializable{
	
	//-----------------------------------------------------------//
	
	public ControllerExampleUseThis (String controllerID)
	{
			super(controllerID);
			//hellooooooo
	}
	
	//-----------------------------------------------------------// 

	Object nextController=null;	
	
    @FXML
    private Button logout;


    @FXML
    private Button back;


    @FXML
    private Text userID;


    @FXML
    void Button1(ActionEvent event) {

    }
    
	 //-----------------------------------------------------------// 
		//-----------------------------------------------------------//
		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {//this method perform when this controller scene is showing up.
			User user = User.getCurrentLoggedIn();			//hellooooooo

			userID.setText(user.GetUserName());			//hellooooooo

			//hellooooooo

		}
		@FXML
		void TurningBack(ActionEvent event)
		{
			//hellooooooo

			this.nextController = new LoginController("SecretaryLoginController");
			this.Back("/Login/LoginWindow.fxml",nextController, event);
		}
 }