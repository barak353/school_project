package Parent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.Timer;

import Login.LoginController;
import Entity.User;
//import Parent.ChiidDetails;
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


public class ChildDetailsController extends QueryController implements Initializable{

	//-----------------------------------------------------------//

	public ChildDetailsController (String controllerID)
	{
			super(controllerID);
	} 

	//-----------------------------------------------------------//
	Object nextController=null;	

    @FXML
    private Button logout;

    @FXML
    private Text childname;

    @FXML
    private Button back;

    @FXML
    private Text gpa;

    @FXML
    private Text childid;

    @FXML
    private Text userID;	
	//-----------------------------------------------------------//

	  
	    @FXML
	    void TurningBack(ActionEvent event)
	    {
	    	this.nextController = new ChoiceChildController("ChoiceChildController");
	    	this.Back("/Parent/ChoiceChild.fxml",nextController, event);
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
	    
		//-----------------------------------------------------------//
		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {//this method perform when this controller scene is showing up.
			User user = User.getCurrentLoggedIn();
			userID.setText(user.GetUserName());
		}
    
    
}

