package student;

import application.QueryController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

import Secretary.AskRequestFormController;

public class SubmitHomeWorkController extends QueryController  {

	//-----------------------------------------------------------//
	public SubmitHomeWorkController(String controllerID) 
	{
		super(controllerID);
		
	}
	void SubmitSpecificTask(ActionEvent event)
	{
				 try {
					   FXMLLoader loader = new FXMLLoader(getClass().getResource("/student/SubmissionOfAspecificTask.fxml"));
					   loader.setController(new SubmissionOfAspecificTask("SubmissionOfAspecificTask"));
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
	

	

}
