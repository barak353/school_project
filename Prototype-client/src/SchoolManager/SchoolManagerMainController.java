package SchoolManager;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.JFileChooser;

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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import Student.WatchTaskController;

public class SchoolManagerMainController extends QueryController {

	// -----------------------------------------------------------//

	public SchoolManagerMainController(String controllerID) {
		super(controllerID);
	}

	// -----------------------------------------------------------//

	Object nextController = null;

	@FXML
	private Button logout;

	@FXML
	private Button back;

	@FXML
	private Text userID;
	
    @FXML
    void blockParentalAccess(ActionEvent event) {

    }

    @FXML
    void generateReports(ActionEvent event) {

    }

    @FXML
    void viewAllInformation(ActionEvent event) {

    }

    @FXML
    void viewMessages(ActionEvent event) {

    }

}