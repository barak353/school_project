package Secretary;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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
/**
 * 
 * This controller handles the action of showing to the user a choose form of a message.
 */
public class AskRequestFormController extends QueryController  implements Initializable{
    
	//------------------------------------------------//
	public AskRequestFormController(String controllerID)
	{
			super(controllerID);
	} 
	//------------------------------------------------// 
	Object nextController=null;
    @FXML
    private Text userID;
    @FXML
    private Button TeacherID;
    @FXML
    private Button DeleteID;
    @FXML
    private Button ADDID;
    @FXML
    private Button logout;
    @FXML
    private Button back;
    /**
	 * 
	 * The function TeacherChangeForm open the teacher change request form.
	 * @param event
	 */
    @FXML
    void TeacherChangeForm(ActionEvent event)
    {
		 try {
			   FXMLLoader loader = new FXMLLoader(getClass().getResource("/Secretary/TeacherRequestForm.fxml"));
			   loader.setController(new TeacherRequestFormController("TeacherRequestFormController"));
			   Pane login_screen_parent = loader.load();
			        Scene login_screen_scene=new Scene(login_screen_parent);
					Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();//the scene that the event came from.
					app_stage.hide();
					app_stage.setScene(login_screen_scene);
					app_stage.show(); 
		        } catch (IOException e) {//problem with the teacherWindow.xml file.
					System.err.println("Missing TeacherRequestForm.fxml file");
					e.printStackTrace();
				}
    }
    //-----------------------------------------------------------------------------------------//
    /**
  	 * 
  	 * The function AddStudent open the insert student form.
  	 * @param event
  	 */
    @FXML
    void AddStudent(ActionEvent event)
    {
		 try {
			   FXMLLoader loader = new FXMLLoader(getClass().getResource("/Secretary/InsertStudentRequestForm.fxml"));
			   loader.setController(new InsertStudentRequestFormController("StudentRequestFormController"));
			   Pane login_screen_parent = loader.load();
			        Scene login_screen_scene=new Scene(login_screen_parent);
					Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();//the scene that the event came from.
					app_stage.hide();
					app_stage.setScene(login_screen_scene);
					app_stage.show(); 
		        } catch (IOException e) {//problem with the teacherWindow.xml file.
					System.err.println("InsertStudentRequestForm.fxml file");
					e.printStackTrace();
				}
    }
    //-------------------------------------------------------------------------------------------// 
    /**
  	 * 
  	 * The function DeleteStudent open the delete student form.
  	 * @param event
  	 */
    @FXML
    void DeleteStudent (ActionEvent event)
    { 
		 try {
			   FXMLLoader loader = new FXMLLoader(getClass().getResource("/Secretary/DeleteStudentRequestForm.fxml"));
			   loader.setController(new DeleteStudentRequestFormController("DeleteStudentRequestFormController"));
			   Pane login_screen_parent = loader.load();
			        Scene login_screen_scene=new Scene(login_screen_parent);
					Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();//the scene that the event came from.
					app_stage.hide();
					app_stage.setScene(login_screen_scene);
					app_stage.show(); 
		        } catch (IOException e) {//problem with the teacherWindow.xml file.
					System.err.println("InsertStudentRequestForm.fxml file");
					e.printStackTrace();
				}
    }
    //-------------------------------------------------------------------------------------------// 
    /**
   	 * 
   	 * The function TurningBack return's to the main screen of the secretary.
   	 * @param event
   	 */
    @FXML
    void TurningBack(ActionEvent event)
    {
    	this.nextController = new SecretaryMainController("SecretaryMainController");
    	this.Back("/Secretary/SecretaryMainWindow.fxml",nextController, event);
    } 
    //-------------------------------------------------------------------------------------------//
	 /**
  	 * Initialize function, shows the logged in user.
  	 * @param arg0
  	 * @param arg1
  	 */
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {//this method perform when this controller scene is showing up.
		User user = User.getCurrentLoggedIn();
		userID.setText(user.GetUserName());
	}

}


