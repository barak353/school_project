package Student;

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
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import Secretary.AskRequestFormController;
import Secretary.SecretaryMainController;
import Entity.Course;
import Entity.Task;
import Entity.User;



public class SubmitHomeWorkController extends QueryController implements Initializable {

	//-----------------------------------------------------------//
	public SubmitHomeWorkController(String controllerID) 
	{
		super(controllerID);
		
	}
	//-------------------------------------------------------------
	
	Object nextController=null;	
	
    @FXML
    private Button logout;


    @FXML
    private Button back;


    @FXML
    private Text userID;
    
    @FXML
    private Button OK;
    
    @FXML
    private ComboBox<String> comboBoxChooseCourse;
    
    @FXML
    private ComboBox<String> comboBoxChooseTask;
    @FXML
    private Button Next ;

    private	ArrayList<String> TaskNameList = new ArrayList<String>();


    @FXML
    private Text ErrorMSGG ;
    
    private Task task;

  /**This function is enabled after the user has chosen a course and a specific task**/
    
    //SpecificTaskWindowController
    @FXML
    void Specific(ActionEvent event) 
    {
    	 try {
			   FXMLLoader loader = new FXMLLoader(getClass().getResource("/student/SpecificTaskWindow.fxml"));
			   SpecificTaskWindowController controller =  new SpecificTaskWindowController("SpecificTaskWindowController");
			   controller.setTask(task);
			   loader.setController(controller);
			   Pane login_screen_parent = loader.load();
			        Scene login_screen_scene=new Scene(login_screen_parent);
					Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();//the scene that the event came from.
					app_stage.hide();
					app_stage.setScene(login_screen_scene);
					app_stage.show(); 
		        } catch (IOException e) {
					System.err.println("Missing WatchTask.fxml file");
					e.printStackTrace();
				}
    }
	
    @FXML
	 void TurningBack(ActionEvent event)
	    {
	    	this.nextController = new MainWindowStudentController("StudentMainController");
	    	this.Back("/student/MainWindowStudent.fxml",nextController, event);
	    } 
	  
		@Override
		public void initialize(URL arg0, ResourceBundle arg1) 
		{//this method perform when this controller scene is showing up.
			User user = User.getCurrentLoggedIn();
			userID.setText(user.GetUserName());
			String userID1=user.GetID();
			
			// resultArray ->The query return mat of the specific student //
			
			
			ArrayList<ArrayList<String>> resultArray= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM student WHERE StudentID=" + userID1 );
		    System.out.println(resultArray);	
			
				
			
			// save the student id //
			String studentID= resultArray.get(0).get(0);
			
	    	// res ->The query return mat of the id courses that the student learn//
			ArrayList<ArrayList<String>> StudentInCourseList = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM studentincourse WHERE identityStudent="+userID1);	
			if(StudentInCourseList==null)
			{
				//ErrorMSG.setText("You are not in this course");//show error message.
				//ErrorMSG.setText("");//delete error message.
			}
			
			else
			{
	
			// save list of the name of the courses of the student//
			ArrayList<String> courseNameList = new ArrayList<String>();
	    	ArrayList<ArrayList<String>> CoursesNameList;	    	
	    	// res2 -> The query return mat of the course name and id course//
	    	for(ArrayList<String> row:StudentInCourseList){
	    		CoursesNameList = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT courseName,idcourses FROM courses WHERE idcourses="+row.get(1));
	    
	        // put the course list at the comboBoxChooseCourse//
	        courseNameList.add(CoursesNameList.get(0).get(0)+"("+CoursesNameList.get(0).get(1)+")");
	        ObservableList obList= FXCollections.observableList(courseNameList);;
	        comboBoxChooseCourse.setItems(obList);
	    	
	    	
	    	
	    	}
	    	
			}
		}

	/** This function is enabled when the user selects a specific course in the list 
	 * And handles the choice of the specific task for the course
	 * **/		
			
	@FXML
	void AfterChooseCourse(ActionEvent event)
	{
	// save the student's choise//
		String chooseCourse = comboBoxChooseCourse.getValue();
		String idcourses = chooseCourse.substring(chooseCourse.indexOf("(") + 1, chooseCourse.indexOf(")"));//get the idcourses that is inside a ( ).
		ArrayList<ArrayList<String>> IdTaskInCourseList = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT TaskName FROM task WHERE idcorse="+idcourses);
		if(IdTaskInCourseList==null)
			ErrorMSGG.setText("There is NO Tasks in this course.");//show error message.
		else
		{
			for(ArrayList<String> row : IdTaskInCourseList){
					TaskNameList.add(row.get(0));
			}
			 ObservableList obList= FXCollections.observableList(TaskNameList);
			 comboBoxChooseTask.setItems(obList);
		}
	 }
	
	
	@FXML
	void AfterChooseTask(ActionEvent event) {
		String choosedCourse = comboBoxChooseCourse.getValue();
		choosedCourse = choosedCourse.substring(choosedCourse.indexOf("(") + 1, choosedCourse.indexOf(")"));//get the idcourses that is inside a ( ).
		String choosedTask = comboBoxChooseTask.getValue();
		System.out.println("choosedCourse: "+choosedCourse+" choosedTask: "+choosedTask);
		ArrayList<ArrayList<String>> taskRes = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM task WHERE idcorse="+choosedCourse +" AND TaskName='"+choosedTask+"'");
		System.out.println("taskRes: "+taskRes);
		String idTask = null;
		if(taskRes != null && taskRes.get(0) != null) 
		{
		     ArrayList<String> row = taskRes.get(0);
		     DateFormat df = new SimpleDateFormat("mm-dd-yyyy");
		     try {
				task = new Task(row.get(0),row.get(1),row.get(2),(Date) df.parse(row.get(3)));
			} catch (ParseException e) {
				System.out.println("Error in format from string to date.");
			}
		}
	}
}








