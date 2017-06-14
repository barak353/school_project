package Student;

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
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import Student.WatchTaskController;

public class SubmitTaskController extends QueryController implements Initializable{
	
	//-----------------------------------------------------------//
	
	public SubmitTaskController (String controllerID)
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
    void Button1(ActionEvent event) 
    {

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
		
		
		
		
		/*	 try {
		   FXMLLoader loader = new FXMLLoader(getClass().getResource("/student/WatchTask.fxml"));
		   loader.setController(new WatchTaskController("WatchTaskControllerID"));
		   Pane login_screen_parent = loader.load();
		        Scene login_screen_scene=new Scene(login_screen_parent);
				Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();//the scene that the event came from.
				app_stage.hide();
				app_stage.setScene(login_screen_scene);
				app_stage.show(); 
	        } catch (IOException e) {
				System.err.println("Missing WatchTask.fxml file");
				e.printStackTrace();
			}*/
 }