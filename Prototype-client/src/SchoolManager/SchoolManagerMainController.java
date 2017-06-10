package SchoolManager;

import java.io.File;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import Student.WatchTaskController;

public class SchoolManagerMainController extends QueryController implements Initializable {

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
	void upload(ActionEvent event) {
		
		JFileChooser chooser= new JFileChooser();

		int choice = chooser.showOpenDialog(chooser);

		if (choice != JFileChooser.APPROVE_OPTION) return;

		File file = chooser.getSelectedFile();
		
		//File file = new File(filePath.getText());
		if (file.exists())
			System.out.println("file or directory denoted by this abstract pathname exists.");
		else
			System.out.println("file or directory denoted by this abstract pathname is not exists.");
		Object ans = transfferFileToServer(file);
		System.out.println("arrived");
	}
	
	@FXML
	void download(ActionEvent event) {
		System.out.println("in download");
		super.download();
	}

	// -----------------------------------------------------------//
	// -----------------------------------------------------------//
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {// this method
															// perform when this
															// controller scene
															// is showing up.
		User user = User.getCurrentLoggedIn();
		userID.setText(user.GetUserName());
	}

	@FXML
	void TurningBack(ActionEvent event) {
		this.nextController = new LoginController("StudentLoginController");
		this.Back("/Login/LoginWindow.fxml", nextController, event);
	}

}