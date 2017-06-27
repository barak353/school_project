package SchoolManager;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Login.LoginController;
import Secretary.AskRequestFormController;
import Secretary.SecretaryMainController;
import Student.MainWindowStudentController;
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
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * 
 * This controller handles the school manger actions and is responsible for Block Parental Access
 *
 */
public class BlockParentalAccessController extends QueryController implements Initializable{
	
	//-----------------------------------------------------------//
	
	public BlockParentalAccessController (String controllerID)
	{
			super(controllerID);
	}
	
	//-----------------------------------------------------------// 

	Object nextController=null;	
	
    @FXML
    private Button logout;

    @FXML
    private Button WatchPersonalFolder;

    @FXML
    private Button SubmitHomeWork;


    @FXML
    private Button BlockButton;

    @FXML
    private Text userID;
    @FXML
    private Text ErrorMSG;
    
    private boolean IsChooseSudent=false;
    
    private boolean IsChooseParent=false;
    
    private ArrayList<ArrayList<String>> ParentIdList;
    
//ChooseStudent
    
    @FXML
    private ComboBox<String> ChooseStudent;

		@Override
		public void initialize(URL arg0, ResourceBundle arg1) 
		{//this method perform when this controller scene is showing up.
			User user = User.getCurrentLoggedIn();
			userID.setText(user.GetUserName());
			
		    //put the names of the child in the ComboBox
		    ArrayList<ArrayList<String>> StudentdList = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT StudentID FROM student ");
	       
	        if( StudentdList == null)
	        {
	        	ErrorMSG.setText("Student not found");//show error message.
	        	return;
	        }
	        else
	        {
	        	 //save list of the names of the courses of the student
	        	
	        	ArrayList<String> studentNameList = new ArrayList<String>();
	        	ArrayList<ArrayList<String>> StudentNameList;	    	
	        	for(ArrayList<String> row:StudentdList){
	                // put the course list at the comboBoxChooseCourse//
	        		StudentNameList = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT userID,userName FROM user WHERE userID="+row.get(0));
	        		if (StudentNameList==null)
	        		{
	        			ErrorMSG.setText("Student not found");//show error message
	        		}
	        		
	        		else{
	        			ErrorMSG.setText("");//show error message
		        		// show the student's name 
	        			studentNameList.add((StudentNameList.get(0)).get(1)+" ("+(StudentNameList.get(0)).get(0)+")");
		                System.out.println(studentNameList);
		                ObservableList obList= FXCollections.observableList(studentNameList);
		                ChooseStudent.setItems(obList);

	        		}
	        		ErrorMSG.setText(" ");
	        	}
	        }
		}
		
		
		
		/**
		 * After pressing the user choose a student un the combo box, the id of  student's parent saved    
		 * @param event
		 */
		
		
		@FXML
		void AfterChooseStudent(ActionEvent event)
		{
			ErrorMSG.setText(" ");
		// save the student's choise
	    
		String chooseStudent1 = ChooseStudent.getValue();
		if(chooseStudent1 !=null)
			IsChooseSudent=true;
		
		if (IsChooseSudent)
		{
			//save the id of the choosen student
			String idStudent = chooseStudent1.substring(chooseStudent1.indexOf("(") + 1, chooseStudent1.indexOf(")"));//get the idcourses that is inside a ( ).
			if(idStudent != null)
			{
				
				ParentIdList=(ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT PID FROM childparent WHERE studenid="+idStudent);
				IsChooseParent=true;
				System.out.println (ParentIdList);
				if(ParentIdList==null)
				{
					IsChooseParent=false;
					ErrorMSG.setText("Parent not fount");//show error message
				}
				
			}
			else
			{
				ErrorMSG.setText(" students  not found");//show error message
			}
		}
	
		else
		{
			ErrorMSG.setText("Please choose a student!");//show error message
		}
		
		}
		
		/**
		 * After pressing the appropriate button, this function set the parent block's status    
		 * @param event
		 */
		
		  @FXML
		  	 void AftherBlock(ActionEvent event)
		  	    {
			     ErrorMSG.setText(" ");
		  	    //set the stasus
			     int BlockStatus=0;
			     if (IsChooseSudent &&IsChooseParent)
			     {
			     BlockStatus= 1;
			     String parentID=ParentIdList.get(0).get(0);
			     Object obj =transfferQueryToServer("UPDATE parent SET Block="+BlockStatus+" WHERE ParentID="+parentID);	
			     ErrorMSG.setText(" ");
			     }
			     else 
			     {
			    	 ErrorMSG.setText("couldnt block parent , please choose student!");//show error message
			     }
			   
			  
		  	    }
		
		   /**
		     * function that return to the last screen
		     * @param event
		     */
		
		   @FXML
		  	 void TurningBack(ActionEvent event)
		  	    {
		  	    	this.nextController = new SchoolManagerMainController ("SchoolManagerMainController ");
		  	    	this.Back("/SchoolManager/SchoolManagerMainWindow.fxml",nextController, event);
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

 }
	    



	 //-----------------------------------------------------------// 












