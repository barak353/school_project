package SystemManager;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javax.swing.Timer;
import Login.LoginController;
import SystemManager.SystemManagerAddCourseController;
import Parent.ParentMainController;
import Parent.ChoiceChildController;
import Secretary.AskRequestFormController;
import Secretary.SecretaryMainController;
import Secretary.TeacherRequestFormController;
import Teacher.TeacherMainController;
import Entity.User;
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
import Student.MainWindowStudentController;

/**
 * 
 * This controller handles the System Manager actions and is responsible for presenting the System Manager main screen
 *
 */

public class SystemManagerMainController extends QueryController implements Initializable{
	
	//-----------------------------------------------------------//
	
	public SystemManagerMainController (String controllerID)
	{
			super(controllerID);
	}
	
	//-----------------------------------------------------------// 

	Object nextController=null;	
	
    @FXML
    private Button logout;
    @FXML
    private Button addNewCourseButton;
    @FXML
    private Text userID;
    

    //-----------------------------------------------------------//
    
   /**
     *  After pressing the addNewCourse button
     *  the system Continue to the next screen -  Add New Course
     * @param event
     */   
    
    @FXML
    void addNewCourse(ActionEvent event) {
		 try {
			   FXMLLoader loader = new FXMLLoader(getClass().getResource("/SystemManager/SystemManagerAddCourseWindow.fxml"));
			   loader.setController(new SystemManagerAddCourseController("SystemManagerAddCourseControllerID"));
			   Pane login_screen_parent = loader.load();
			        Scene login_screen_scene=new Scene(login_screen_parent);
					Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();//the scene that the event came from.
					app_stage.hide();
					app_stage.setScene(login_screen_scene);
					app_stage.show(); 
		        } catch (IOException e) {//problem with the teacherWindow.xml file.
					System.err.println("Missing SystemManagerAddCourseWindow.fxml file");
					e.printStackTrace();
				}
    }
    
	 //-----------------------------------------------------------// 
    
    /**
     * this function initialize the screen whit the name of the user.
     */

    	@Override
		public void initialize(URL arg0, ResourceBundle arg1) {//this method perform when this controller scene is showing up.
			User user = User.getCurrentLoggedIn();
			userID.setText(user.GetUserName());
		}
    	
    	//-----------------------------------------------------------//
    	   
        /**
         * function that return to the last screen
         * @param event
         */
 }