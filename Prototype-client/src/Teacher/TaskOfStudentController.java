package Teacher;

import application.QueryController;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.ComboBox;
import Login.LoginController;
import Entity.User;
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

public class TaskOfStudentController extends QueryController implements Initializable {

	Object nextController=null;	
//-----------------------------------------------------------//

public TaskOfStudentController(String controllerID)
{
		super(controllerID);
}

		@FXML
		private Button logout;
		
		@FXML
		private Button openFile;
		
		@FXML
		private ComboBox<String> StudentList;
		
		@FXML
		private Button save;
		
		@FXML
		private Button back;
		
		@FXML
		private Text userID;
		
		 @FXML
		 private Text textMSG;
		 
		 @FXML
		 private Text courseName;
		 
		  private String courseID;
		  private String courseN;
		  private String idtask;
		  private File file;
		    
		 

void TurningBack(ActionEvent event) {
	this.nextController = new ChecksHomeworkController("ChecksHomeworkController");
	this.Back("/Teacher/ChecksHomework.fxml",nextController, event);
}

public void initialize(URL arg0, ResourceBundle arg1) {//this method perform when this controller scene is showing up.
	User user = User.getCurrentLoggedIn();
	userID.setText(user.GetUserName());
	courseName.setText(courseN);
}

@FXML
void saveB(ActionEvent event) {
	textMSG.setVisible(true);
	
}

@FXML
void LogOut(ActionEvent event) {
	 try 
	 {
		
		    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login/LoginWindow.fxml"));
	        loader.setController(new LoginController("LoginController"));
		    Pane login_screen_parent = loader.load();
		        Scene login_screen_scene=new Scene(login_screen_parent);
				Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();//the scene that the event came from.
				app_stage.hide();
				app_stage.setScene(login_screen_scene);
				app_stage.show(); 
	        } 
	 catch (IOException e) {
			System.err.println("Missing LoginWindow.fxml file");
			e.printStackTrace();
			}
}

public void setCourseID(String courseID) {
	this.courseID = courseID;
	// TODO Auto-generated method stub
	
}

public void setCourseN(String courseN) {
	this.courseN = courseN;
	// TODO Auto-generated method stub
	
}

public void setTaskID(String idtask) {
	this.idtask = idtask;
	// TODO Auto-generated method stub
	
}



	
}

