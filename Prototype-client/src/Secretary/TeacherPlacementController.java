package Secretary;


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
import javafx.stage.Stage;

public class TeacherPlacementController extends QueryController {
//------------------------------------------------//
public TeacherPlacementController (String controllerID)
{
       super(controllerID);
} 
//------------------------------------------------// 
Object nextController=null;
@FXML
private Button SaveID;
@FXML
private TextField TeacherField;
@FXML
private TextField ClassField;
@FXML
private TextField CourseField;
@FXML
private Button logout;
@FXML
private Button back;
@FXML
void TurningBack(ActionEvent event)
{
	this.nextController = new SecretaryMainController("SecretaryMainController");
	this.Back("/Secretary/SecretaryMainWindow.fxml",nextController, event);
} 
}
