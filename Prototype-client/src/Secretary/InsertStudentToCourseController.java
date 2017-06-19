package Secretary;

import java.net.URL;
import java.util.ResourceBundle;

import Entity.User;
import application.QueryController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class InsertStudentToCourseController extends QueryController implements Initializable{
		
	@FXML
	private Button logout;

	@FXML
	private Text userID;

	@FXML
	private Button back;

	Object nextController=null;
	//----------------------------------------------------------------------//
	public InsertStudentToCourseController(String controllerID)
	{
			super(controllerID);
	} 
    //----------------------------------------------------------------------//
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		User user = User.getCurrentLoggedIn();
		userID.setText(user.GetUserName());
	}
	//----------------------------------------------------------------------//
	@FXML
	void TurningBack(ActionEvent event)
	{
		this.nextController = new SecretaryMainController("SecretaryMainController");
		this.Back("/Secretary/SecretaryMainWindow.fxml",nextController, event);
	} 
	//----------------------------------------------------------------------//
	
}
