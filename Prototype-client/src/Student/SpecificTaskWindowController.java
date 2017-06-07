package Student;

import java.net.URL;
import java.util.ResourceBundle;

import Login.LoginController;
import Entity.User;
import application.QueryController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class SpecificTaskWindowController extends QueryController implements Initializable {

	public SpecificTaskWindowController(String controllerID) 
	{
		super(controllerID);
		
	}
	

	Object nextController=null;	
	
    @FXML
    private Button logout;


    @FXML
    private Button back;

    @FXML
    private Button back2;

    @FXML
    private Text userID;
    
    @FXML
    private Button OK;
    
    
    @FXML
    private Button UploadFile;
    
    @FXML
    private Button WatchTask;
    
    @FXML
    void Button1(ActionEvent event) 
    {
    }
    
    
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {//this method perform when this controller scene is showing up.
		User user = User.getCurrentLoggedIn();
		userID.setText(user.GetUserName());
	}
	
	//back to watch task window
	@FXML
	void WatchBack(ActionEvent event)
	{
		
		this.nextController = new WatchTaskController("WatchTaskControllerID");
		this.Back("/student/WatchTask.fxml",nextController, event);
	}
	
	//back to submit task window
	
	@FXML
	void SubmitBack(ActionEvent event)
	{
		this.nextController = new SubmitHomeWorkController("SubmitHomeWorkController");
		this.Back("/student/SubmitHomeWork.fxml",nextController, event);
	}

}
