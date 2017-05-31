package Teacher;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Login.LoginController;
import User.User;
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



public class TeacherMainController extends QueryController implements Initializable{

	//-----------------------------------------------------------//
	
	public TeacherMainController(String controllerID)
	{
			super(controllerID);
	}
	
	//-----------------------------------------------------------// 
	Object nextController=null;	
	
	    @FXML
	    private Button logout;

	    @FXML
	    private Button FillFinalEvaluation;

	    @FXML
	    private Button SetUpTask;

	    @FXML
	    private Button ChecksHomework;

	    @FXML
	    private Button back;

	    @FXML
	    private Text userID;

	
	//-----------------------------------------------------------// 
	    
	    void FillFinalEvaluation(ActionEvent event)
	    {
	    	try {
	     			    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Teacher/FillFinalEvaluation.fxml"));
	     		        loader.setController(new FillFinalEvaluationController("FillFinalEvaluationController"));
	     		        Pane login_screen_parent = loader.load();
	     				Scene login_screen_scene=new Scene(login_screen_parent);
	     				Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();//the scene that the event came from.
	     				app_stage.hide();
	     				app_stage.setScene(login_screen_scene);
	     				app_stage.show(); 
	     				} catch (IOException e) {
	     					System.err.println("Missing FillFinalEvaluation.fxml file");
	     					e.printStackTrace();
	     				}
	    } 
  
	    void SetUpTask(ActionEvent event)
	    {
	    	try {
	     			    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Teacher/SetUpTask.fxml"));
	     		        loader.setController(new SetUpTaskController("SetUpTaskController"));
	     		        Pane login_screen_parent = loader.load();
	     				Scene login_screen_scene=new Scene(login_screen_parent);
	     				Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();//the scene that the event came from.
	     				app_stage.hide();
	     				app_stage.setScene(login_screen_scene);
	     				app_stage.show(); 
	     				} catch (IOException e) {
	     					System.err.println("Missing SetUpTask.fxml file");
	     					e.printStackTrace();
	     				}
	    } 


	    
	    void ChecksHomework(ActionEvent event)
	    {
	    	try {
	     			    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Teacher/ChecksHomework.fxml"));
	     		        loader.setController(new ChecksHomeworkController("ChecksHomeworkController"));
	     		        Pane login_screen_parent = loader.load();
	     				Scene login_screen_scene=new Scene(login_screen_parent);
	     				Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();//the scene that the event came from.
	     				app_stage.hide();
	     				app_stage.setScene(login_screen_scene);
	     				app_stage.show(); 
	     				} catch (IOException e) {
	     					System.err.println("Missing ChecksHomework.fxml file");
	     					e.printStackTrace();
	     				}
	    } 
	  //-------------------------------------------------------------------------------------------------//
	    @FXML
	    void TurningBack(ActionEvent event)
	    {
	    	this.nextController = new LoginController("LoginController");
	    	this.Back("/Login/LoginWindow.fxml",nextController, event);
	    }
	   
	    public void initialize(URL arg0, ResourceBundle arg1) {//this method perform when this controller scene is showing up.
	    	User user = User.getCurrentLoggedIn();
	    	userID.setText(user.GetUserName());
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
}
