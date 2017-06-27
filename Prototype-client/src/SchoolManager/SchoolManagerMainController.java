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
import SystemManager.SystemManagerAddCourseController;

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
    private Button GenerateReports;

	// -----------------------------------------------------------//
	
    @FXML
    void blockParentalAccess(ActionEvent event)
    {
   	 try 
	 {
		    FXMLLoader loader = new FXMLLoader(getClass().getResource("/SchoolManager/BlockParentalAccessWindow.fxml"));
	        loader.setController(new BlockParentalAccessController ("BlockParentalAccessController "));
		    Pane login_screen_parent = loader.load();
		        Scene login_screen_scene=new Scene(login_screen_parent);
				Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();//the scene that the event came from.
				app_stage.hide();
				app_stage.setScene(login_screen_scene);
				app_stage.show(); 
	        } 
	 catch (IOException e) {
			System.err.println("Missing LoginWindow.fxml file");
			e.printStackTrace();
			}
    }

    
    @FXML
    void GenerateReports(ActionEvent event) {
		 try 
		 {
			    FXMLLoader loader = new FXMLLoader(getClass().getResource("/SchoolManager/GenerateReportsWindow.fxml"));
		        loader.setController(new GenerateReportsController("GenerateReportsController"));
			    Pane login_screen_parent = loader.load();
			        Scene login_screen_scene=new Scene(login_screen_parent);
					Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();//the scene that the event came from.
					app_stage.hide();
					app_stage.setScene(login_screen_scene);
					app_stage.show(); 
		        } 
		 catch (IOException e) {
				System.err.println("Missing LoginWindow.fxml file");
				e.printStackTrace();
				}
    }
    
    @FXML
    void viewAllInformation(ActionEvent event) {
		 try {
			   FXMLLoader loader = new FXMLLoader(getClass().getResource("/SchoolManager/viewAllInformationWindow.fxml"));
			   loader.setController(new viewAllInformationController("viewAllInformationControllerID"));
			   Pane login_screen_parent = loader.load();
			        Scene login_screen_scene=new Scene(login_screen_parent);
					Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();//the scene that the event came from.
					app_stage.hide();
					app_stage.setScene(login_screen_scene);
					app_stage.show(); 
		        } catch (IOException e) {//problem with the teacherWindow.xml file.
					System.err.println("Missing viewAllInformationWindow.fxml file");
					e.printStackTrace();
				}
    }

    @FXML
    void viewMessage(ActionEvent event)
    {
		 try {
			   FXMLLoader loader = new FXMLLoader(getClass().getResource("/SchoolManager/ViewMessage.fxml"));
			   loader.setController(new ViewMessageController("ViewMessageController"));
			   Pane login_screen_parent = loader.load();
			        Scene login_screen_scene=new Scene(login_screen_parent);
					Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();//the scene that the event came from.
					app_stage.hide();
					app_stage.setScene(login_screen_scene);
					app_stage.show(); 
		        } catch (IOException e) {//problem with the teacherWindow.xml file.
					System.err.println("Missing ViewMessage.fxml file");
					e.printStackTrace();
				}
    }
}