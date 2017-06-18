package Student;
import  Entity.Student;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import Entity.User;
import application.QueryController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
/**
 * this controller handles the action: Viewing personal folder by student
 * @author Admin
 *
 */
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
    private Button WatchTasks;

   @FXML
    private Text userID;
    //----------------------------------------------------------//
    @FXML
    private Text StudentName;
    
    @FXML
    private Text StudentId;
    
    @FXML
    private Text GPA;
    
    @FXML
    private Text GradeInCourse;
    @FXML
    private ComboBox<String> ChooseCours;
    
  
    @FXML
    private Text   ErrorMSG;

	 //-----------------------------------------------------------//
    @FXML
    private Text Sname;
    
    @FXML
    private Text Sid;

    @FXML
    private Text Sgps;
    
    private	ArrayList<String> TaskNameList = new ArrayList<String>();
    
    private User user;
    

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
		
/**
 * 
 *  when the user press on  the button "Watch details" he will watch the student personal info on the screen 
 * 
 */
		
@FXML
void WatchStudentDetails(ActionEvent event)
{
//public Student(String userId, String userName, String userPSW, String Type, String Email,float GPA)	 

 user = User.getCurrentLoggedIn();

//get student details from the user
String userId1=user.GetID();
String userName1=user.GetUserName();
String userPSW=user.GetUserPassword();
String Email1= user.GetEmail();


//get the table with the student details
ArrayList<ArrayList<String>> resultArray= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM student WHERE studentID=" + userId1 );
System.out.println(resultArray);

//print the student detailes to the screen

Sname.setText(user.GetUserName());
Sid.setText(resultArray.get(0).get(0));
Sgps.setText(resultArray.get(0).get(1));//GPA

//----------------------------------------------------------------------------------------------------------------
//put the names of the courses in the combobox
ArrayList<ArrayList<String>> StudentInCourseList = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT identityCourse,Grade FROM studentincourse WHERE identityStudent="+userId1);	
System.out.println(StudentInCourseList);

if(StudentInCourseList == null)
{
	ErrorMSG.setText("Student is not in this course");//show error message.
	return;
}
else
{
	 //save list of the names of the courses of the student
	ArrayList<String> courseNameList = new ArrayList<String>();
	ArrayList<ArrayList<String>> CoursesNameList;	    	
	for(ArrayList<String> row:StudentInCourseList){
        // put the course list at the comboBoxChooseCourse//
		CoursesNameList = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT courseName,idcourses FROM courses WHERE idcourses="+row.get(0));
		if (CoursesNameList==null)
		{
			ErrorMSG.setText("There is NO courses.");//show error message
		}
		
		else
		{
		ErrorMSG.setText("");//show error message
       
		// show the student's course  
        courseNameList.add((CoursesNameList.get(0)).get(0)+"("+(CoursesNameList.get(0)).get(1)+")");
        System.out.println(courseNameList);
        ObservableList obList= FXCollections.observableList(courseNameList);
        ChooseCours.setItems(obList);
		}
	}
}


}



/** 
 * This function handle with choosing the specific course  of the student and presenting its grade
 * 
 */	
	
@FXML
void AfterChooseCourse(ActionEvent event)
{

// save the student's choise//
String chooseCourse = ChooseCours.getValue();

//save the grade's list of the student in the choosen course
String idcourses = chooseCourse.substring(chooseCourse.indexOf("(") + 1, chooseCourse.indexOf(")"));//get the idcourses that is inside a ( ).
ArrayList<ArrayList<String>> GradeOfStudentInCourse = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT Grade FROM studentincourse WHERE identityCourse="+idcourses+" AND identityStudent='"+user.GetID()+"'");

if( GradeOfStudentInCourse==null)
{
	ErrorMSG.setText("There is NO Grades in this course.");//show error message.
	return;
}
else
{
	ErrorMSG.setText("");//show error message.

    GradeInCourse.setText(GradeOfStudentInCourse.get(0).get(0).toString());

	
}
}
}



			 
			 
			 
			 
			 
			 
				 


