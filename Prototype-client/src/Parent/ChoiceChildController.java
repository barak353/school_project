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

/**
 * 
 * this controller handles the action: Choosing a child
 *
 */

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
    
    private String choosechild;
    
    private int flag=0;

	//-----------------------------------------------------------//
   
    /**
     *  After pressing the ViewChoiceChild button, this function check if the user choose from the ComboBox Child,
     *  if yes, the system Continue to the next screen -  Child Details
     *  and sends the chooseChild to the next Controller
     * @param event
     */   
	
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
    		controller.setChooseChild(choosechild);
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

	//-----------------------------------------------------------//
   
    /**
     * function that return to the last screen
     * @param event
     */

    @FXML
    void TurningBack(ActionEvent event)
    {
    	this.nextController = new ParentMainController("ParentMainController");
    	this.Back("/Parent/ParentMain.fxml",nextController, event);
    }
   
	//-----------------------------------------------------------//
    
    /**
     * function that return to the log in screen
     * @param event
     */ 

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
    
    /**
     * this function initialize the screen whit the name of the user ,parent id and the ComboBox child list of parent .
     */

    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {//this method perform when this controller scene is showing up.
		User user = User.getCurrentLoggedIn();
		userID.setText(user.GetUserName());
		
		parentID = user.GetID();

	    //put the names of the child in the ComboBox
	    ArrayList<ArrayList<String>> childList = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT studenid FROM childparent WHERE PID="+parentID);
        System.out.println(childList);
        if(childList == null){
        	ErrorMSG.setText("No students found");//show error message.
        	return;
        }
        else{
        	 //save list of the names of the courses of the student
        	ArrayList<String> childNameList = new ArrayList<String>();
        	ArrayList<ArrayList<String>> ChildNameList;	    	
        	for(ArrayList<String> row:childList){
                // put the course list at the comboBoxChooseCourse//
        		ChildNameList = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT userID,userName FROM user WHERE userID="+row.get(0));
        		if (ChildNameList==null){
        			ErrorMSG.setText("There is NO found child.");//show error message
        		}
        		
        		else{
        			ErrorMSG.setText("");//show error message
	        		// show the student's course  
        			childNameList.add((ChildNameList.get(0)).get(1)+" ("+(ChildNameList.get(0)).get(0)+")");
	                System.out.println(childNameList);
	                ObservableList obList= FXCollections.observableList(childNameList);
	                childlist.setItems(obList);

        		}
        	}
        }
        

	}

	//-----------------------------------------------------------//
    
    /**
     * this function check if the user choose child from the ComboBox
     * @param event
     */


    @FXML
    void chooseChild(ActionEvent event) {
		flag = 1;
		
		// save the student's choice//
		chooseChild = childlist.getValue();
    	
		//save the grade's list of the student in the choosen course
		choosechild = chooseChild.substring(chooseChild.indexOf("(") + 1, chooseChild.indexOf(")"));//get the idcourses that is inside a ( ).

    }

	//-----------------------------------------------------------//

}
