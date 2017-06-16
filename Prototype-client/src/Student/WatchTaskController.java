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

import java.awt.Label;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import Secretary.AskRequestFormController;
import Secretary.SecretaryMainController;
import Entity.Course;
import Entity.SubTask;
import Entity.Task;
import Entity.User;


/**
 * this controller handles the action: Viewing task by student
 * @author Admin
 *
 */
public class WatchTaskController extends QueryController implements Initializable {

	//-----------------------------------------------------------//
	public WatchTaskController(String controllerID) 
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
    private Button  WatchSubmittedTask;

  //  @FXML
   // private Label studentUpload;
    
   // @FXML
   // private Label teacherUpload;
    
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
    private Text ErrorMSG;
    
    private SubTask subtask;


    @FXML
   private Text SubtaskGrade1;
    @FXML
    private Text SubtaskComments1;
    
    @FXML
    private Text ErrorMsg;
    
    private Task task;
    
    private boolean isTaskChoosed = false;
    
    private	ArrayList<String> TaskList = new ArrayList<String>();

/**This function is enabled after the user has chosen a course and a specific task 
 * handle the watching task that the teacher upload**/
    @FXML
    void watchTask(ActionEvent event) {
    	if(isTaskChoosed == true){
        	FXMLLoader loader = null;
        	ErrorMSG.setText(" ");
	    	String userHomeFolder = System.getProperty("user.home");
	    	File textFile = new File(userHomeFolder, "mytext.txt");
	    	try {
				BufferedWriter out = new BufferedWriter(new FileWriter(textFile));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    	WatchTaskController controller;
    	}else{
    		ErrorMSG.setText("Please choose course and task");
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
			ArrayList<ArrayList<String>> StudentInCourseList = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT identityCourse FROM studentincourse WHERE identityStudent="+userID1);	
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
		    		System.out.println("(CoursesNameList.get(0)).get(1): "+(CoursesNameList.get(0)).get(1));
			        courseNameList.add((CoursesNameList.get(0)).get(0)+"("+(CoursesNameList.get(0)).get(1)+")");
			        ObservableList obList= FXCollections.observableList(courseNameList);
			        comboBoxChooseCourse.setItems(obList);
		    		}
		    	}
			}
		}

		/** 
		 * This function handle with choosing the specific course and presenting its assignments
		 * 
		 */	
			
	@FXML
	void AfterChooseCourse(ActionEvent event)
	{
	// save the student's choise//
		isTaskChoosed = false;
		String chooseCourse = comboBoxChooseCourse.getValue();
		String idcourses = chooseCourse.substring(chooseCourse.indexOf("(") + 1, chooseCourse.indexOf(")"));//get the idcourses that is inside a ( ).
		ArrayList<ArrayList<String>> IdTaskInCourseList = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT TaskName FROM task WHERE idcorse="+idcourses);
		if(IdTaskInCourseList==null){
			ErrorMSG.setText("There is NO Tasks in this course.");//show error message.
			//This lines cleans the Task combobox.
			 ObservableList obList= FXCollections.observableList(new ArrayList());
			 comboBoxChooseTask.setItems(obList);
		}
		else
		{
			ErrorMSG.setText("");//show error message.
			for(ArrayList<String> row : IdTaskInCourseList){
					TaskNameList.add(row.get(0));
			}
			 ObservableList obList= FXCollections.observableList(TaskNameList);
			 comboBoxChooseTask.setItems(obList);
		}
	 }
	
	/**
	 * After selecting the specific course, this function handle with task choice
	 * **/
	@FXML
	void AfterChooseTask(ActionEvent event) 
	{
		isTaskChoosed = false;
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
		     task = new Task(row.get(0),row.get(1),row.get(2), row.get(3));
			isTaskChoosed = true;
		}
	}
	
	/*ברק זה ההנדלר לצפייה בקובץ מטלה שהוגשה!!(פתור) 
	*
	*
	*/
	
	/** This function is enabled after the user has chosen a course and a specific task
	 * handle the watching sub task that the student submit**/    
    @FXML
    void watchSubTask(ActionEvent event) 
    {
		isTaskChoosed = false;
		String choosedCourse = comboBoxChooseCourse.getValue();
		choosedCourse = choosedCourse.substring(choosedCourse.indexOf("(") + 1, choosedCourse.indexOf(")"));//get the idcourses that is inside a ( ).
		String choosedTask = comboBoxChooseTask.getValue();
		System.out.println("choosedCourse: "+choosedCourse+" choosedTask: "+choosedTask);
		String studentID = User.getCurrentLoggedIn().GetID();
		//the query return the subtasks of the student un the specific cours
		//ArrayList<ArrayList<String>> taskSubRes = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM subtask WHERE IDcourse="+choosedCourse +" AND IDstudent='"+studentID+"'"+" AND idTASK='"++"'");
		
		ArrayList<ArrayList<String>> taskRes = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM task WHERE idcorse="+choosedCourse +" AND TaskName='"+choosedTask+"'");
		System.out.println("taskRes: "+taskRes);
		//if 1
		if(taskRes != null && taskRes.get(0) != null)
		{
			isTaskChoosed = true;
			for(ArrayList<String> row : taskRes)
			{
				SubtaskGrade1.setText("");
		    	SubtaskComments1.setText("");
		    	ErrorMsg.setText("");
		    	
				ArrayList<ArrayList<String>> taskSubRes1 = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM subtask WHERE IDcourse="+choosedCourse +" AND IDstudent='"+studentID+"'"+" AND idTASK='"+ row.get(0) +"'");
				System.out.println( "----taskSubRes:" + taskSubRes1);
				
				//if 2
				if(taskSubRes1 != null )
				{
					ArrayList<String> row1 = taskSubRes1.get(0);
				if(row1.get(4)==null)
					SubtaskGrade1.setText("Task Grade : you didnt get grade yet");
				else
					SubtaskGrade1.setText("Task Grade :" + row1.get(4));
		    	
		    	if(row1.get(5)==null)
		    		SubtaskComments1.setText("Task Comments :");
		    
		    	else
		    		SubtaskComments1.setText("Task Comments :" + row1.get(5));
		    	
		    	
		    	}
				//else 2
				else
				{
					System.out.println( "There is Not submitted Task  ");
					ErrorMsg.setText("There is Not submitted Task  ");
				}
				
			}
			
			
				
		}
		//else 1
		else
		{
			SubtaskGrade1.setText("");
	    	SubtaskComments1.setText("");
			ErrorMsg.setText("There is Not  Task  ");
		}
		
		

		    	 
            
			//	subtask = new SubTask(row.get(0),row.get(1),row.get(2),row.get(3),row.get(4),row.get(5));
				



}
}








