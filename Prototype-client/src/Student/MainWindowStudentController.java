package Student;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Login.LoginController;
import Secretary.AskRequestFormController;
import Secretary.SecretaryMainController;
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

public class MainWindowStudentController extends QueryController implements Initializable{
	
	//-----------------------------------------------------------//
	
	public MainWindowStudentController (String controllerID)
	{
			super(controllerID);
	}
	
	//-----------------------------------------------------------// 

	Object nextController=null;	
	
    @FXML
    private Button logout;

    @FXML
    private Button WatchPersonalFolder;

    @FXML
    private Button SubmitHomeWork;

    @FXML
    private Button back;

    @FXML
    private Button ButtonNew;

    @FXML
    private Text userID;



    @FXML
    void WatchT(ActionEvent event) {
		 try {
			   FXMLLoader loader = new FXMLLoader(getClass().getResource("/student/SubmitHomeWork.fxml"));
			   loader.setController(new SubmitHomeWorkController("SubmitHomeWorkController"));
			   Pane login_screen_parent = loader.load();
			        Scene login_screen_scene=new Scene(login_screen_parent);
					Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();//the scene that the event came from.
					app_stage.hide();
					app_stage.setScene(login_screen_scene);
					app_stage.show(); 
		        } catch (IOException e) {
					System.err.println("Missing WatchTask.fxml file");
					e.printStackTrace();
				}
    }

    @FXML
    void SubmitHomeWork(ActionEvent event) {
		 try {
			   FXMLLoader loader = new FXMLLoader(getClass().getResource("/student/SubmitHomeWork.fxml"));
			   loader.setController(new SubmitHomeWorkController("SubmitHomeWorkController"));
			   Pane login_screen_parent = loader.load();
			        Scene login_screen_scene=new Scene(login_screen_parent);
					Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();//the scene that the event came from.
					app_stage.hide();
					app_stage.setScene(login_screen_scene);
					app_stage.show(); 
		        } catch (IOException e) {
					System.err.println("Missing SubmitHomeWork.fxml file");
					e.printStackTrace();
				}
    }

    @FXML
    void WatchPersonalFolder(ActionEvent event) {
		 try {
			   FXMLLoader loader = new FXMLLoader(getClass().getResource("/student/WatchPersonalFolder.fxml"));
			   loader.setController(new WatchPersonalFolderController("WatchPersonalFolderController"));
			   Pane login_screen_parent = loader.load();
			        Scene login_screen_scene=new Scene(login_screen_parent);
					Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();//the scene that the event came from.
					app_stage.hide();
					app_stage.setScene(login_screen_scene);
					app_stage.show(); 
		        } catch (IOException e) {
					System.err.println("Missing WatchPersonalFolder.fxml file");
					e.printStackTrace();
				}
    } 
	 //-----------------------------------------------------------// 
		//-----------------------------------------------------------//
		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {//this method perform when this controller scene is showing up.
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
	    



	 //-----------------------------------------------------------// 












