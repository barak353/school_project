package student;

import application.QueryController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Secretary.AskRequestFormController;
import Secretary.SecretaryMainController;
import User.User;

public class SubmitHomeWorkController extends QueryController implements Initializable {

	//-----------------------------------------------------------//
	public SubmitHomeWorkController(String controllerID) 
	{
		super(controllerID);
		
	}
	//-------------------------------------------------------------
	
	Object nextController=null;	
	
    @FXML
    private Button logout;


    @FXML
    private Button back;


    @FXML
    private Text userID;
    
    @FXML
    private Button OK;
    
    @FXML
    private ComboBox<String> comboBoxID1;
    
    @FXML
    private ComboBox<String> comboBoxID2;
    
    
    @FXML
    private Button Next ;


    @FXML
    void Button1(ActionEvent event) {

    }

    
    //SpecificTaskWindowController
    @FXML
    void Specific(ActionEvent event) 
    {
    	 try {
			   FXMLLoader loader = new FXMLLoader(getClass().getResource("/student/SpecificTaskWindow.fxml"));
			   loader.setController(new SpecificTaskWindowController("SpecificTaskWindowController"));
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
	 void TurningBack(ActionEvent event)
	    {
	    	this.nextController = new MainWindowStudentController("StudentMainController");
	    	this.Back("/student/MainWindowStudent.fxml",nextController, event);
	    } 
	  
		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {//this method perform when this controller scene is showing up.
			User user = User.getCurrentLoggedIn();
			userID.setText(user.GetUserName());
		}

	

}
