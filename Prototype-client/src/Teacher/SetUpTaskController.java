package Teacher;

import java.io.IOException;
import Login.LoginController;
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



public class SetUpTaskController extends QueryController {

	//-----------------------------------------------------------//
	
	public SetUpTaskController(String controllerID)
	{
			super(controllerID);
	}
}