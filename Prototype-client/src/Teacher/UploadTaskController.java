package Teacher;

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


public class UploadTaskController extends QueryController implements Initializable {

		Object nextController=null;	
	//-----------------------------------------------------------//
	
	public UploadTaskController(String controllerID)
	{
			super(controllerID);
	}
	//-----------------------------------------------------------//
    @FXML
    private Button logout;

    @FXML
    private Button back;

    @FXML
    private Button save;

    @FXML
    private Text userID;
    
    @FXML
    private TextField TaskName;
    
    @FXML
    private Text courseName;
    
    @FXML
    private Text textMSG;
    
    private String courseN;

	//-----------------------------------------------------------//
    @FXML
	void TurningBack(ActionEvent event) {
    	this.nextController = new SetUpTaskController("SetUpTaskController");
    	this.Back("/Teacher/SetUpTask.fxml",nextController, event);
    }
	
    public void initialize(URL arg0, ResourceBundle arg1) {//this method perform when this controller scene is showing up.
    	User user = User.getCurrentLoggedIn();
    	userID.setText(user.GetUserName());
    	courseName.setText(courseN);
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
    
    public void setCourseN(String courseN){
    	this.courseN = courseN;
    }
    
    @FXML
    void saveB(ActionEvent event) {
    	textMSG.setVisible(true);
    
    	
    	
    }

}
