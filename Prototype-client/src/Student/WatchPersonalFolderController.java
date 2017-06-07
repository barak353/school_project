package Student;

import java.net.URL;
import java.util.ResourceBundle;

import Entity.User;
import application.QueryController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class WatchPersonalFolderController extends QueryController implements Initializable


{

	public WatchPersonalFolderController(String controllerID)
	{
		super(controllerID);
		
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
    private Text StudentName;
    
    
    @FXML
    private Text StudentId;
    
    @FXML
    private Text GPA;

    @FXML
    void Button1(ActionEvent event) 
    {

    }
    
	 //-----------------------------------------------------------//
    
    
    @FXML
  	 void TurningBack(ActionEvent event)
  	    {
  	    	this.nextController = new MainWindowStudentController("StudentMainController");
  	    	this.Back("/student/MainWindowStudent.fxml",nextController, event);
  	    }  
	  
		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {//this method perform when this controller scene is showing up.
			User user = User.getCurrentLoggedIn();
			userID.setText(user.GetUserName());
		}


}
