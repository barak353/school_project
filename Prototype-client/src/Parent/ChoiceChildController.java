package Parent;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Login.LoginController;
import Entity.User;
//import Parent.ParentMainController;
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
	Object nextController=null;	

    @FXML
    private Button logout;

    @FXML
    private Button view;

    @FXML
    private ComboBox<String> childlist;

    @FXML
    private Button back;
    
    @FXML
    private Text ErrorMSG;

    
    
    @FXML
    private Text userID;
    
    private String parentID;
    
    private String chooseChild;
    
    private int flag=0;

	//-----------------------------------------------------------//
	
    @FXML
    void ViewChoiceChild(ActionEvent event){

    	try {
    		
    		if (flag == 0)
        	{
        		ErrorMSG.setText("To continue, please choose a child! "); //show error message.
    		    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Parent/ChoiceChild.fxml"));
    	        loader.setController(new ChoiceChildController("ChoiceChildController"));
        	}
    		
    		else{
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/Parent/ChildDetails.fxml"));
    		ChildDetailsController controller = new ChildDetailsController("ChildDetailsController");
    		controller.setChooseChild(chooseChild);
	        loader.setController(controller);
			Pane login_screen_parent = loader.load();
			Scene login_screen_scene=new Scene(login_screen_parent);
			Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();//the scene that the event came from.
			app_stage.hide();
			app_stage.setScene(login_screen_scene);
			app_stage.show(); 
    		}
		} 
    	catch (IOException e) {		        	
			System.err.println("Missing ChildDetails.fxml file");
			e.printStackTrace();
		}
	}   

    
    @FXML
    void TurningBack(ActionEvent event)
    {
    	this.nextController = new ParentMainController("ParentMainController");
    	this.Back("/Parent/ParentMain.fxml",nextController, event);
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
		
	    ArrayList<ArrayList<String>> res = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT studenid FROM childparent WHERE PID="+parentID);
	    
	    ArrayList<String> childNameList = new ArrayList<String>();
    	for(ArrayList<String> row:res){
        	childNameList.add(row.get(0));
    	}
    	
	    ObservableList obList= FXCollections.observableList(childNameList);
	    childlist.setItems(obList);
	    
	}

    @FXML
    void chooseChild(ActionEvent event) {
		flag = 1;
    	chooseChild = childlist.getValue();
    }

}
