package Parent;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.Timer;

import Login.LoginController;
import Entity.User;
//import Parent.ChiidDetails;
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


public class ChildDetailsController extends QueryController implements Initializable{

	//-----------------------------------------------------------//

	public ChildDetailsController (String controllerID)
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
    private Text gpa;

    @FXML
    private Text Sname;
    
    @FXML
    private Text semester;

    @FXML
    private Text userID;

    @FXML
    private Text Sid;
    
    @FXML
    private ComboBox<String> courselist;

    
    private String chooseChild;

    private String childID;
    
    private String choosecourse;

	//-----------------------------------------------------------//

	  
	    @FXML
	    void TurningBack(ActionEvent event)
	    {
	    	this.nextController = new ChoiceChildController("ChoiceChildController");
	    	this.Back("/Parent/ChoiceChild.fxml",nextController, event);
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
			ArrayList<ArrayList<String>> res = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM student WHERE studentID="+chooseChild);
			ArrayList<String> row = res.get(0);
			Sid.setText(row.get(0));
			gpa.setText(row.get(1));
			semester.setText(row.get(2));
			
			ArrayList<ArrayList<String>> res2 = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT userName FROM user WHERE userID="+chooseChild);
			ArrayList<String> row2 = res2.get(0);
			Sname.setText(row2.get(0));
			
			
			
			ArrayList<ArrayList<String>> res3 = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT identityCourse FROM studentincourse WHERE identityStudent="+chooseChild);
		    
		    ArrayList<String> childNameList = new ArrayList<String>();
		    
	    	for(ArrayList<String> row3:res3){
	        	childNameList.add(row3.get(0));
	    	}
	    	
		    ObservableList obList= FXCollections.observableList(childNameList);
		    courselist.setItems(obList);
		}
		//-----------------------------------------------------------//


		public void setChooseChild(String chooseChild) {
			this.chooseChild = chooseChild;
		}
		

	    @FXML
	    void chooseCourse(ActionEvent event) {
			choosecourse = courselist.getValue();
	    }

    
}

