package student;

import java.io.IOException;

import Secretary.SecretaryMainController;
import application.QueryController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class WatchTaskController extends QueryController {

	public WatchTaskController(String controllerID)
	{
		super(controllerID);
		
	}

	Object nextController=null;
	
	void WatchSpecificT (ActionEvent event)
	{
				 try {
					   FXMLLoader loader = new FXMLLoader(getClass().getResource("/student/SpecificTaskWindow.fxml"));
					   loader.setController(new SpecificTaskWindowController("watch  specific task "));
					   Pane login_screen_parent = loader.load();
					        Scene login_screen_scene=new Scene(login_screen_parent);
							Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();//the scene that the event came from.
							app_stage.hide();
							app_stage.setScene(login_screen_scene);
							app_stage.show(); 
				        } catch (IOException e) {
							System.err.println("SubmissionOfAspecificTask.fxml file");
							e.printStackTrace();
						}
	}
	
	  void TurningBack(ActionEvent event)
	    {
	    	this.nextController = new SecretaryMainController("StudentMainController");
	    	this.Back("/Secretary/MainWindowStudent.fxml",nextController, event);
	    } 

}




