package Secretary;


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

public class TeacherPlacementController extends QueryController  implements Initializable{
	//------------------------------------------------//
	public TeacherPlacementController (String controllerID)
	{
	       super(controllerID);
	} 
	//------------------------------------------------// 
	Object nextController=null;
	@FXML
	private Button SaveID;
	@FXML
	private Text userID;
	@FXML
	private TextField TeacherField;
	@FXML
	private TextField ClassField;
	@FXML
	private TextField CourseField;
	@FXML
	private Button logout;
	@FXML
	private Button back;
	@FXML
	void TurningBack(ActionEvent event)
	{
		this.nextController = new SecretaryMainController("SecretaryMainController");
		this.Back("/Secretary/SecretaryMainWindow.fxml",nextController, event);
		System.out.println();

	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {//this method perform when this controller scene is showing up.
		User user = User.getCurrentLoggedIn();
		userID.setText(user.GetUserName());
		System.out.println();

	}
}
