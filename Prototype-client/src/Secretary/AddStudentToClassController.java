package Secretary;

import java.net.URL;
import java.util.ResourceBundle;

import User.User;
import application.QueryController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class AddStudentToClassController extends QueryController  implements Initializable{

	Object nextController=null;
	@FXML
	private Button logout;
	@FXML
	private Button back;
    @FXML
    private Text userID;
    @FXML
	private TextField StudentID;
	@FXML
	private Button SaveAndExit;
	@FXML
	private Button AddMore;
	//------------------------------------------------//
	public AddStudentToClassController(String controllerID)
	{
			super(controllerID);
	} 
	//------------------------------------------------// 
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {//this method perform when this controller scene is showing up.
		User user = User.getCurrentLoggedIn();
		userID.setText(user.GetUserName());
	}
	//------------------------------------------------// 
    @FXML
    void TurningBack(ActionEvent event)
    {
    	System.out.println();
    	this.nextController = new ClassDefineController("ClassDefineController");
    	this.Back("/Secretary/ClassDefine.fxml",nextController, event);
    } 
	//------------------------------------------------// 
    @FXML
    void AddMore(ActionEvent event)
    {
    	StudentID.clear();
    } 
	//------------------------------------------------// 
}
