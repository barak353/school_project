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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import Student.WatchTaskController;

public class viewAllInformationController extends QueryController implements Initializable{

	// -----------------------------------------------------------//

	public viewAllInformationController(String controllerID) {
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
    private ComboBox<String> chooseInfo;

    @FXML
    private TextArea infoArea;

    @FXML
    void printInfo(ActionEvent event) {
    	switch(chooseInfo.getValue()){
    	case "Semesters":
    		ArrayList<ArrayList<String>> semester= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM semester");
    		
    	break;
    	case "Classes":
    		
    	break;
    	case "Courses":
    		
    	break;
    	case "Teachers":
    		
    	break;
    	case "Users":
    		
    	break;
    	case "Tasks":
    		
    	break;
    	case "Sudents":
    	break;
    	
    	}
    }
    
    
    
    
    
    /**
     * this function initialize the screen whit the name of the user.
     */
    	@Override
		public void initialize(URL arg0, ResourceBundle arg1) {//this method perform when this controller scene is showing up.
			User user = User.getCurrentLoggedIn();
			userID.setText(user.GetUserName());
			ArrayList<String> chooseInfo = new ArrayList<String>();
			chooseInfo.add("Semesters");
			chooseInfo.add("Classes");
			chooseInfo.add("Courses");
			chooseInfo.add("Teachers");
			chooseInfo.add("Users");
			chooseInfo.add("Tasks");
			chooseInfo.add("Sudents");
		    ObservableList obList= FXCollections.observableList(chooseInfo);;
		    this.chooseInfo.setItems(obList);
		}
}

