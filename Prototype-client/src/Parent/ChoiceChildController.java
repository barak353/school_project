package Parent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Login.LoginController;
import User.User;
//import Parent.ParentMainController;
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

public class ChoiceChildController extends QueryController implements Initializable{
	
	//-----------------------------------------------------------//

	public ChoiceChildController (String controllerID)
	{
			super(controllerID);
	} 

	//-----------------------------------------------------------//

    @FXML
    private Button logout;
	//hellooooooo2
	//hellooooooo2
	//hellooooooo2
	//hellooooooo2	//hellooooooo2
	//hellooooooo2
	//hellooooooo2
	//hellooooooo2
    @FXML
    private Button view;

    @FXML
    private Button back;

    @FXML
    private Text userID;
    
	//-----------------------------------------------------------//

	
    @FXML			//hellooooooo

    void ViewChoiceChild(ActionEvent event){
    	try {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/Parent/ChildDetails.fxml"));
	        loader.setController(new ChildDetailsController("ChildDetailsController"));
			Pane login_screen_parent = loader.load();
			Scene login_screen_scene=new Scene(login_screen_parent);
			Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();//the scene that the event came from.
			app_stage.hide();
			app_stage.setScene(login_screen_scene);
			app_stage.show(); 
		} 
    	catch (IOException e) {
			System.err.println("Missing ChildDetails.fxml file");
			e.printStackTrace();
		}
	}  

    
    @FXML
    void Back(ActionEvent event) {
		 try 
		 {
			
			    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Parent/ParentMain.fxml"));
		        loader.setController(new ParentMainController("ParentMainController"));
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

    

    @FXML
    void LogOut(ActionEvent event) {
		 try 
		 {
			
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
    
	//-----------------------------------------------------------//
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {//this method perform when this controller scene is showing up.
		User user = User.getCurrentLoggedIn();
		userID.setText(user.GetUserName());
	}

}
