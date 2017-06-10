package Student;
import  Entity.Student;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import Entity.User;
import application.QueryController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

public class WatchPersonalFolderController extends QueryController implements Initializable


{

	public WatchPersonalFolderController(String controllerID)
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
    private Text userID;
    //----------------------------------------------------------//
    @FXML
    private Text StudentName;
    
    @FXML
    private Text StudentId;
    
    @FXML
    private Text GPA;

	 //-----------------------------------------------------------//
    @FXML
    private Text Sname;
    
    @FXML
    private Text Sid;

    @FXML
    private Text Sgps;
    
    
    /* @FXML
    private Text StudentName1;
    
   @FXML
    private Text StudentId1;
    
    @FXML
    private Text GPA1; 
    
    @FXML
    private TextArea comments;*/
    //----------------------------------------------------------//
    
    
    
    
    @FXML
  	 void TurningBack(ActionEvent event)
  	    {
  	    	this.nextController = new MainWindowStudentController("StudentMainController");
  	    	this.Back("/student/MainWindowStudent.fxml",nextController, event);
  	    }  
	  
		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {//this method perform when this controller scene is showing up.
			User user = User.getCurrentLoggedIn();
			userID.setText(user.GetUserName());
		}
		
/** when the user press on  the button "Watch details" he will watch the student personal info on the screen **/
		
@FXML
void WatchStudentDetails(ActionEvent event)
{
//public Student(String userId, String userName, String userPSW, String Type, String Email,float GPA)	 
		



User user = User.getCurrentLoggedIn();

//get student details from the user
String userId1=user.GetID();
String userName1=user.GetUserName();
String userPSW=user.GetUserPassword();
String Email1= user.GetEmail();
float GPA1=100;

//get the table with the student details
ArrayList<ArrayList<String>> resultArray= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM student WHERE studentID=" + userId1 );
System.out.println(resultArray);

//print the student detailes to the screen

Sname.setText(user.GetUserName());
Sid.setText(resultArray.get(0).get(0));
Sgps.setText(resultArray.get(0).get(1));//GPA

//create student object
//float GPATest=(float) Double.parseDouble(resultArray.get(0).get(2));
//Student student1 = new Student ( userId1,userName1,userPSW,"4",Email1,GPATest)




			 
			 
			 
			 
			 
			 
				 

}
}
