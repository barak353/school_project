package Secretary;

import java.io.IOException;

import application.QueryController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AskRequestFormController extends QueryController {
    
	//------------------------------------------------//
	public AskRequestFormController(String controllerID)
	{
			super(controllerID);
	} 
	//------------------------------------------------// 
    @FXML
    private Button TeacherID;
    @FXML
    private Button DeleteID;
    @FXML
    private Button ADDID;
    
    @FXML
    void TeacherChangeForm(ActionEvent event)
    {
		 try {
			   FXMLLoader loader = new FXMLLoader(getClass().getResource("/Secretary/TeacherRequestForm.fxml"));
			   //loader.setController(new LoginController("LoginController"));
			   Pane login_screen_parent = loader.load();
			        Scene login_screen_scene=new Scene(login_screen_parent);
					Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();//the scene that the event came from.
					app_stage.hide();
					app_stage.setScene(login_screen_scene);
					app_stage.show(); 
		        } catch (IOException e) {//problem with the teacherWindow.xml file.
					System.err.println("Missing LoginWindows.fxml file");
					e.printStackTrace();
				}
    }
    //-----------------------------------------------------------------------------------------//
    @FXML
    void AddStudent(ActionEvent event)
    {
		 try {
			   FXMLLoader loader = new FXMLLoader(getClass().getResource("/Secretary/StudentRequestForm.fxml"));
			   //loader.setController(new LoginController("LoginController"));
			   Pane login_screen_parent = loader.load();
			        Scene login_screen_scene=new Scene(login_screen_parent);
					Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();//the scene that the event came from.
					app_stage.hide();
					app_stage.setScene(login_screen_scene);
					app_stage.show(); 
		        } catch (IOException e) {//problem with the teacherWindow.xml file.
					System.err.println("Missing LoginWindows.fxml file");
					e.printStackTrace();
				}
    }
    //-------------------------------------------------------------------------------------------// 
    @FXML
    void Exit(ActionEvent event)
    {
		 try {
			   FXMLLoader loader = new FXMLLoader(getClass().getResource("/Secretary/SecretaryMainWindow.fxml"));
			   loader.setController(new SecretaryMainController("SecretaryMainID"));
			   Pane login_screen_parent = loader.load();
			        Scene login_screen_scene=new Scene(login_screen_parent);
					Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();//the scene that the event came from.
					app_stage.hide();
					app_stage.setScene(login_screen_scene);
					app_stage.show(); 
		        } catch (IOException e) {//problem with the teacherWindow.xml file.
					System.err.println("Missing SecretaryMainWindow.fxml file");
					e.printStackTrace();
				}
    }
    //-------------------------------------------------------------------------------------------//
}


