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
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class messageController extends QueryController {

	Object nextController=null;	
	
	public messageController(String controllerID)
	{
			super(controllerID);
	}
	
    @FXML
    private Button exit;

    @FXML
    private Text userID;

    @FXML
    void exit(ActionEvent event) {

    }
    @FXML
    void save(ActionEvent event)
    {
    	Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();//the scene that the event came from.
		app_stage.hide();
    }
   
}
