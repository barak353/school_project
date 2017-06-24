package Secretary;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javax.swing.Timer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import Entity.Semester;
import Entity.Teacher;
import Entity.User;
import Entity.Course;
import application.QueryController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

/**
 * 
 * This controller handles the student change action- the secretary will choose what
 * action she wants to do- insert student to course or delete student from course.
 */
public class StudentChangeController extends QueryController implements Initializable{

	    @FXML
	    private Button logout;

	    @FXML
	    private Text userID;

	    @FXML
	    private Button back;

	    @FXML
	    private Button ADDID;

	    @FXML
	    private Button DeleteID;

	    Object nextController=null;
	//----------------------------------------------------------------------//
	public StudentChangeController(String controllerID)
	{
			super(controllerID);
	} 
    //----------------------------------------------------------------------//
	/**
	 * 
	 * Initialize function, shows the logged in user. 
	 * @param arg0
	 * @param arg1
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		User user = User.getCurrentLoggedIn();
		userID.setText(user.GetUserName());
	}
    //----------------------------------------------------------------------//
	/**
	 * 
	 * The function Add Student will show the Insert Student window.
	 * @param event
	 */
	@FXML
    void AddStudent(ActionEvent event)
    {
    	 try {
			   FXMLLoader loader = new FXMLLoader(getClass().getResource("/Secretary/InsertStudentToCourse.fxml"));
			   loader.setController(new InsertStudentToCourseController("InsertStudentToCourseController"));
			   Pane login_screen_parent = loader.load();
			        Scene login_screen_scene=new Scene(login_screen_parent);
					Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();//the scene that the event came from.
					app_stage.hide();
					app_stage.setScene(login_screen_scene);
					app_stage.show(); 
		        } catch (IOException e) {
					System.err.println("Missing InsertStudentToCourse.fxml file");
					e.printStackTrace();
				}
    }
    //----------------------------------------------------------------------//
	/**
	 * 
	 * The function Delete Student will show the Delete Student window.
	 * @param event
	 */
	@FXML
    void DeleteStudent(ActionEvent event)
    {
      	 try {
			   FXMLLoader loader = new FXMLLoader(getClass().getResource("/Secretary/DeleteStudentFromCourse.fxml"));
			   loader.setController(new DeleteStudentFromCourseController("DeleteStudentFromCourseController"));
			   Pane login_screen_parent = loader.load();
			        Scene login_screen_scene=new Scene(login_screen_parent);
					Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();//the scene that the event came from.
					app_stage.hide();
					app_stage.setScene(login_screen_scene);
					app_stage.show(); 
		        } catch (IOException e) {
					System.err.println("Missing DeleteStudentFromCourse.fxml file");
					e.printStackTrace();
				}
    }
    //-------------------------------------------------------------------------------------------// 
    /**
	 * 
	 * The function Turning back return's to the main screen of the secretary.
	 * @param event
	 */
    @FXML
	void TurningBack(ActionEvent event)
	{
		this.nextController = new SecretaryMainController("SecretaryMainController");
		this.Back("/Secretary/SecretaryMainWindow.fxml",nextController, event);
	} 
    //-------------------------------------------------------------------------------------------// 

}


