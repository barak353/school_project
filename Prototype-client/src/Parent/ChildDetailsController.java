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

/**
 * 
 * this controller handles the action: Child Details
 *
 */

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
    @FXML
    private Text ErrorMSG;
    @FXML
    private Text Class;
    @FXML
    private Text GradeInCourse;
    
    boolean status = true;
    
    private String chooseChild;

    private String childID;
    
    private String choosecourse;
    
    private int flag=0;

	//-----------------------------------------------------------//

	    /**
	     * function that return to the last screen
	     * @param event
	     */
	  
	    @FXML
	    void TurningBack(ActionEvent event)
	    {
	    	this.nextController = new ChoiceChildController("ChoiceChildController");
	    	this.Back("/Parent/ChoiceChild.fxml",nextController, event);
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
	     * this function initialize the screen whit the name of the user, childID, child name, the class, GPA and the ComboBox course list.
	     */
	    
		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {//this method perform when this controller scene is showing up.
			User user = User.getCurrentLoggedIn();
			userID.setText(user.GetUserName());
			
			//get the table with the student details (Sid, gpa, Class)
			ArrayList<ArrayList<String>> res = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM student WHERE studentID="+chooseChild);
	        if(res==null){ //No records found
	        	ErrorMSG.setText("NO found Child");//show error message.
	        }
	        else{
				ArrayList<String> row = res.get(0);
				Sid.setText(row.get(0));
				gpa.setText(row.get(1));
				Class.setText(row.get(2));
	        }
	        
			//get the table with the student details (Sname)
			ArrayList<ArrayList<String>> res2 = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT userName FROM user WHERE userID="+chooseChild);
	        if(res2==null){//No records found
	        	ErrorMSG.setText("NO found Child name");//show error message.
	        }
	        else{
				ArrayList<String> row2 = res2.get(0);
				Sname.setText(row2.get(0));
	        }
	        
			//get the table with the curr semester (semester)
			ArrayList<ArrayList<String>> res3 = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT semID FROM semester WHERE status='" + status +"'");
	        if(res3==null){//No records found
	        	ErrorMSG.setText("NO found curr semester");//show error message.
	        }
	        else{
				ArrayList<String> row3 = res3.get(0);
				semester.setText(row3.get(0));
	        }
	  
	        
	      //put the names of the courses in the ComboBox
	        ArrayList<ArrayList<String>> StudentInCourseList = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT identityCourse,Grade FROM studentincourse WHERE identityStudent="+chooseChild);	
	        System.out.println(StudentInCourseList);
	        if(StudentInCourseList == null){
	        	ErrorMSG.setText("Student is not in this course");//show error message.
	        	return;
	        }
	        else{
	        	 //save list of the names of the courses of the student
	        	ArrayList<String> courseNameList = new ArrayList<String>();
	        	ArrayList<ArrayList<String>> CoursesNameList;	    	
	        	for(ArrayList<String> row:StudentInCourseList){
	                // put the course list at the comboBoxChooseCourse//
	        		CoursesNameList = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT courseName,idcourses FROM courses WHERE idcourses="+row.get(0));
	        		if (CoursesNameList==null){
	        			ErrorMSG.setText("There is NO courses.");//show error message
	        		}
	        		
	        		else{
	        			ErrorMSG.setText("");//show error message
		        		// show the student's course  
		                courseNameList.add((CoursesNameList.get(0)).get(0)+" ("+(CoursesNameList.get(0)).get(1)+")");
		                System.out.println(courseNameList);
		                ObservableList obList= FXCollections.observableList(courseNameList);
		                courselist.setItems(obList);
	        		}
	        	}
	        }
		}
		
		
		//-----------------------------------------------------------//
	    
		/**
		 * This function handle with choosing the specific course  of the student and presenting its grade
	     * @param event
	     */

	    @FXML
	    void chooseCourse(ActionEvent event) {
			flag=1;
			
			// save the student's choice//
			choosecourse = courselist.getValue();
	
			//save the grade's list of the student in the choosen course
			String idcourses = choosecourse.substring(choosecourse.indexOf("(") + 1, choosecourse.indexOf(")"));//get the idcourses that is inside a ( ).
			ArrayList<ArrayList<String>> GradeOfStudentInCourse = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT Grade FROM studentincourse WHERE identityCourse="+idcourses+" AND identityStudent='"+chooseChild+"'");

			if( GradeOfStudentInCourse==null){
				ErrorMSG.setText("There is NO Grades in this course.");//show error message.
				return;
			}
			else{
				ErrorMSG.setText("");//show error message.
			    GradeInCourse.setText(GradeOfStudentInCourse.get(0).get(0).toString());
			}
	    } 
	   
		//-----------------------------------------------------------//


		public void setChooseChild(String chooseChild) {
			this.chooseChild = chooseChild;
		}

}

