package Manager;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javax.swing.Timer;
import Login.LoginController;
import Manager.ManagerMainController;
import Parent.ParentMainController;
import Parent.ChoiceChildController;
import Secretary.AskRequestFormController;
import Secretary.SecretaryMainController;
import Teacher.TeacherMainController;
import User.User;
import application.QueryController;
import javafx.event.ActionEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import student.MainWindowStudentController;

public class ManagerMainController extends QueryController implements Initializable{
	
	//-----------------------------------------------------------//
	
	public ManagerMainController (String controllerID)
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
    private Button newID;
    
    @FXML
    private TextField courseID;


    @FXML
    private Text userID;


    @FXML
    void addNewCourse(ActionEvent event) {
    	ArrayList<ArrayList<String>> res = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT pre FROM precourses WHERE curr=1164");
    	if( res != null) System.out.println("res: "+res);
    }
    
	 //-----------------------------------------------------------// 
		//-----------------------------------------------------------//
		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {//this method perform when this controller scene is showing up.
			System.out.println("hey");
			User user = User.getCurrentLoggedIn();
			userID.setText(user.GetUserName());
		}
		
		@FXML
		void TurningBack(ActionEvent event)
		{
			this.nextController = new LoginController("StudentLoginController");
			this.Back("/Login/LoginWindow.fxml",nextController, event);
		}


	
 }