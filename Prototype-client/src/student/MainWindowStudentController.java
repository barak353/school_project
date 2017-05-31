package student;

import java.io.IOException;

import application.QueryController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainWindowStudentController extends QueryController{
	

	
	public MainWindowStudentController(String controllerID)
	{
		super(controllerID);
		
	}

	void WatchT (ActionEvent event)
	{
				 try {
					   FXMLLoader loader = new FXMLLoader(getClass().getResource("/student/WatchTast.fxml"));
					   loader.setController(new WatchTaskController("watch task "));
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
	
	void SubmitHomeWork (ActionEvent event)
	{
				 try {
					   FXMLLoader loader = new FXMLLoader(getClass().getResource("/student/SubmitHomeWork.fxml"));
					   loader.setController(new SubmitHomeWorkController("submit homework"));
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
	void WatchPersonalFolder (ActionEvent event)
	{
				 try {
					   FXMLLoader loader = new FXMLLoader(getClass().getResource("/student/WatchPersonalFolder.fxml"));
					   loader.setController(new WatchPersonalFolderController("submit homework"));
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
