package Parent;

import java.io.IOException;
import Login.LoginController;
//import Parent.ChiidDetails;
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


public class ChildDetailsController extends QueryController {

	//------------------------------------------------//
	public ChildDetailsController (String controllerID)
	{
			super(controllerID);
	} 
	//------------------------------------------------// 

    @FXML
    void Exit(ActionEvent event){
    	try {
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
			System.err.println("Missing StudentChange.fxml file");
			e.printStackTrace();
		}
	}  

	
}
