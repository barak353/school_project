package Secretary;

import java.net.URL;
import java.util.ResourceBundle;

import Login.*;
import application.QueryController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class SecretaryController extends QueryController implements Initializable{

    public SecretaryController(String controllerID) {
		super(controllerID);
	}
    
    @FXML
    private Button buttonGetDetailsID;
    
	@FXML
    private TextField textgetDetailsID;
	
    @FXML
    private Text userNameText;
    
    @FXML
    void getDetails(ActionEvent event) {

    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		userNameText.setText(LoginController.getUsernameID());
	}

}
