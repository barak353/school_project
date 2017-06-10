package Parent;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

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

public class ParentMainController extends QueryController implements Initializable{
	
	//-----------------------------------------------------------//

	public ParentMainController (String controllerID)
	{
			super(controllerID);
	} 
	
	//-----------------------------------------------------------//
	Object nextController=null;	

    @FXML
    private Button logout;

    @FXML
    private Button back;

    @FXML
    private Text textMSG;
    
    @FXML
    private Text userID;
    
    @FXML
    private Text ErrorMSG;

    @FXML
    private Button ViewChildPersonalDetails;
    

    private String parentID;

	//-----------------------------------------------------------//

    
    
    @FXML
    void ViewChildPersonalDetails(ActionEvent event){
    	
    	try {
    		System.out.println("parentID: "+parentID);
    	    ArrayList<ArrayList<String>> res = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT Block FROM parent WHERE parentID="+parentID);
    	    String block = (res.get(0)).get(0);
    		if( block.equals("1")){
        		ErrorMSG.setText("Blocked user, you cannot view child details!"); //show error message.
    		   // FXMLLoader loader = new FXMLLoader(getClass().getResource("/Parent/ParentMain.fxml"));
    	      //  loader.setController(new ParentMainController("ParentMainController"));
        	}
    		else{
			    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Parent/ChoiceChild.fxml"));
		        loader.setController(new ChoiceChildController("ChoiceChildController"));
				Pane login_screen_parent = loader.load();
		        Scene login_screen_scene=new Scene(login_screen_parent);
				Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();//the scene that the event came from.
				app_stage.hide();
				app_stage.setScene(login_screen_scene);
				app_stage.show(); 
				}
    		}
    	
    	catch (IOException e) {
			System.err.println("Missing ChoiceChild.fxml file");
			e.printStackTrace();
		}
	}  
    
    
    @FXML
    void TurningBack(ActionEvent event)
    {
    	this.nextController = new LoginController("LoginController");
    	this.Back("/Login/LoginWindow.fxml",nextController, event);
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
				System.err.println("Missing LoginWindow.fxml file");
				e.printStackTrace();
				}
    }
    
	//-----------------------------------------------------------//
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {//this method perform when this controller scene is showing up.
		User user = User.getCurrentLoggedIn();
		userID.setText(user.GetUserName());
		
		parentID = user.GetID();

		
	}
}
