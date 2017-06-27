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
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
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
import Entity.Semester;
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
    
    private Task task;
    
    private boolean isTaskChoosed = false;
    private boolean isCourseChoosed = false;
    
    private	ArrayList<String> TaskList = new ArrayList<String>();
    
    private ObservableList L;

/**This function is enabled after the user has chosen a course and a specific task 
 * handle the watching task that the teacher upload*
 * @param event
 * */
    @FXML
    void watchTeacherTask(ActionEvent event) {
    	if(isTaskChoosed == true){
    		System.out.println("2");
    		isTaskChoosed = false;
        	ErrorMSG.setText(" ");
        	String choosedTask = comboBoxChooseTask.getValue();
        	String chooseCourse = comboBoxChooseCourse.getValue();
    		String idcourses = chooseCourse.substring(chooseCourse.indexOf("(") + 1, chooseCourse.indexOf(")"));//get the idcourses that is inside a ( ).
        	String sem = Semester.getCurrentSemester().getYear()+":"+Semester.getCurrentSemester().getType();
        	String semFile = Semester.getCurrentSemester().getYear()+Semester.getCurrentSemester().getType();
        	String studentID = User.getCurrentLoggedIn().GetID();
        	ArrayList<ArrayList<String>> res = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT fileExtN FROM  task WHERE IDsem='"+sem
    								+"' AND TaskName='"+choosedTask+"' AND idcorse="+idcourses);
        	String fileName;
        	if(res == null)ErrorMSG.setText("Teacher didnt upload task for this course.");
        	else {
        		ArrayList<String> row = res.get(0);
        		if(row != null ){
        			fileName = row.get(0);
        			if(fileName != null && !fileName.equals("")){
        			     downloadFileFromServer(semFile+"//"+idcourses,fileName );
        			     ErrorMSG.setText("Download was successful, please check foler: "+semFile+"//"+idcourses+"//"+fileName);
        				}
        			else ErrorMSG.setText("Teacher didn't upload a task.");
        		}else ErrorMSG.setText("Teacher didn't upload a task.");
        	}
    	}else{
    		ErrorMSG.setText("Please choose course and task.");
    	}
    }
    
    @FXML
    void viewCommentTask(ActionEvent event) {
    	if(isTaskChoosed == true){
    		ErrorMSG.setVisible(true);
        	ErrorMSG.setText(" ");
        	String choosedTask = comboBoxChooseTask.getValue();
        	String chooseCourse = comboBoxChooseCourse.getValue();
    		String idcourses = chooseCourse.substring(chooseCourse.indexOf("(") + 1, chooseCourse.indexOf(")"));//get the idcourses that is inside a ( ).
        	String sem = Semester.getCurrentSemester().getYear()+":"+Semester.getCurrentSemester().getType();
        	String semFile = Semester.getCurrentSemester().getYear()+Semester.getCurrentSemester().getType();
        	String studentID = User.getCurrentLoggedIn().GetID();
        	ArrayList<ArrayList<String>> resChecked = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM  subtask WHERE semesterName='"+sem
					+"' AND mytaskname='"+choosedTask+"' AND IDNcourse="+idcourses+
					" AND stIDENT="+ studentID);
        	if(resChecked == null){ErrorMSG.setText("You did not submit this task, or Teacher did not grade this task.");return;}
        	ArrayList<ArrayList<String>> res = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT grade,Comments FROM  subtask WHERE semesterName='"+sem
					+"' AND mytaskname='"+choosedTask+"' AND IDNcourse="+idcourses+
					" AND stIDENT="+ studentID);
        	if(res == null){ErrorMSG.setText("Teacher did not grade this task.");return;}
        	if(res.get(0) == null){ErrorMSG.setText("Teacher did not grade this task");return;}
        	if(res.get(0).get(0) == null || res.get(0).get(1) == null){ErrorMSG.setText("Teacher did not grade this task");return;}
        	String semfile = Semester.getCurrentSemester().getYear() + Semester.getCurrentSemester().getType();
        	File file = new File("download//"+semfile+"//"+idcourses+"//"+studentID+"//"+choosedTask+"-comments.txt");
        	file.getParentFile().mkdirs();
        	try{
            	PrintWriter printWriter = new PrintWriter(file);
        		    //printWriter.println ("course: "+idcourses+", task: "+choosedTask+", student: "+studentID+"/n"+"grade: "+res.get(1)+"/n"+"comments: "+res.get(0));
            	printWriter.println ("Task name: "+choosedTask+", Semester: " + sem + ", Course ID = " + idcourses + ", Student ID: " + studentID);    
            	printWriter.println ("================");
            	printWriter.println ("Grade: " + res.get(0).get(0));   
            	printWriter.println ("Comments: " + res.get(0).get(1));   
            	printWriter.close (); 
        	} catch (IOException e) {
        	   System.out.println("error");
        	}
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
				ErrorMSG.setText("You are not enrolled in courses ");//show error message.
				isCourseChoosed = false;
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
		    		//This lines cleans the Task combobox
		    		if (CoursesNameList==null)
		    		{
		    			ErrorMSG.setText("You are not enrolled in courses ");//show error message
		    			isCourseChoosed = false;
		    			return;
		    		}
		    		
		    		else
		    		{
		    		isCourseChoosed = true;	
		    		ErrorMSG.setText("");//show error message
		    		System.out.println("(CoursesNameList.get(0)).get(1): "+(CoursesNameList.get(0)).get(1));
			        courseNameList.add((CoursesNameList.get(0)).get(0)+"("+(CoursesNameList.get(0)).get(1)+")");
			        
			        L= FXCollections.observableList(courseNameList);
			        comboBoxChooseCourse.setItems(L);
		    		}
		    	}
			}
		}

		/** 
		 * This function handle with choosing the specific course and presenting its assignments
		 * @param event
		 */	
			
	@FXML
	void AfterChooseCourse(ActionEvent event)
	{
	// save the student's choise//
		if (isCourseChoosed == true)
		{
		isTaskChoosed = false;
		String chooseCourse = comboBoxChooseCourse.getValue();
		String idcourses = chooseCourse.substring(chooseCourse.indexOf("(") + 1, chooseCourse.indexOf(")"));//get the idcourses that is inside a ( ).
		ArrayList<ArrayList<String>> IdTaskInCourseList = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT TaskName FROM task WHERE idcorse="+idcourses);
		//if task list is empty
		if(IdTaskInCourseList==null)
		{
			 comboBoxChooseTask.getItems().clear();
			 ErrorMSG.setText("There is NO Tasks in this course.");//show error message.
			 return;
		}
		
		else
		{
			//This lines cleans the Task combobox
			comboBoxChooseTask.getItems().clear();
			ErrorMSG.setText("");//show error message.
			for(ArrayList<String> row : IdTaskInCourseList)
			{
					TaskNameList.add(row.get(0));
			}

			 L= FXCollections.observableList(TaskNameList);
			 comboBoxChooseTask.setItems(L);
		}
		}
		
	 }
	
	/**
	 * After selecting the specific course, this function handle with task choice
	 * @param event
	 * **/
	@FXML
	void AfterChooseTask(ActionEvent event) 
	{
		if (isCourseChoosed == true)
		{
		isTaskChoosed = false;
		String choosedCourse = comboBoxChooseCourse.getValue();
		choosedCourse = choosedCourse.substring(choosedCourse.indexOf("(") + 1, choosedCourse.indexOf(")"));//get the idcourses that is inside a ( ).
		String choosedTask = comboBoxChooseTask.getValue();
		System.out.println("choosedCourse: "+choosedCourse+" choosedTask: "+choosedTask);
		ArrayList<ArrayList<String>> taskRes = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM task WHERE idcorse="+choosedCourse +" AND TaskName='"+choosedTask+"'");
		System.out.println("taskRes: "+taskRes);
		if(taskRes != null && taskRes.get(0) != null) 
		{
		     ArrayList<String> row = taskRes.get(0);
		     task = new Task(row.get(0),row.get(1),row.get(2), row.get(3),row.get(4));
		     System.out.println("1");
			 isTaskChoosed = true;
		}else
		{
			SubtaskGrade1.setText("");
	    	SubtaskComments1.setText("");
			ErrorMSG.setText("There is NO Tasks in this course.");//show error message.
			return;
		}
		}
	}
	
	
	
	/** This function is enabled after the user has chosen a course and a specific task
	 * handle the watching sub task that the student submit*
	 * @param event
	 * */    
    @FXML
    void watchStudentTask(ActionEvent event) 
    {
		if(isTaskChoosed == true){
			ErrorMSG.setText("");
			String choosedCourse = comboBoxChooseCourse.getValue();
			choosedCourse = choosedCourse.substring(choosedCourse.indexOf("(") + 1, choosedCourse.indexOf(")"));//get the idcourses that is inside a ( ).
			String choosedTask = comboBoxChooseTask.getValue();
			String studentID = User.getCurrentLoggedIn().GetID();
	    	String sem = Semester.getCurrentSemester().getYear()+":"+Semester.getCurrentSemester().getType();
	    	String semFile = Semester.getCurrentSemester().getYear()+Semester.getCurrentSemester().getType();
	    	ArrayList<ArrayList<String>> res = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT fileExtN FROM  subtask WHERE semesterName='"+sem
					+"' AND mytaskname='"+choosedTask+"' AND IDNcourse="+choosedCourse+
					" AND stIDENT="+ studentID);
	    	
	    	if(res == null){ErrorMSG.setText("Student did not submmit this task.");return;}
	    	isTaskChoosed = false;
	    	if(res.get(0) == null){
	    		ErrorMSG.setText("Student did not submmit this task.");return;
	    		}
	    
	    	String fileName= res.get(0).get(0);
	    	downloadFileFromServer(semFile+"//"+choosedCourse+"//"+studentID, fileName);
	    	ErrorMSG.setText("Download was successful, please check the folder: "+semFile+"//"+choosedCourse+"//"+studentID+"//"+ fileName);
		}
		else ErrorMSG.setText("Please choose course and task.");
		
		
		
		
		
		
		
		//the query return the subtasks of the student in the specific cours
		
		/*ArrayList<ArrayList<String>> taskRes = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM task WHERE idcorse="+choosedCourse +" AND TaskName='"+choosedTask+"'");
		if(taskRes != null && taskRes.get(0) != null)
		{
			isTaskChoosed = true;
			for(ArrayList<String> row : taskRes)
			{
				SubtaskGrade1.setText("");
		    	SubtaskComments1.setText("");
		    	ErrorMSG.setText("");
		 //show the detiels of the student's sub task    	
				ArrayList<ArrayList<String>> taskSubRes1 = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM subtask WHERE IDcourse="+choosedCourse +" AND IDstudent='"+studentID+"'"+" AND idTASK='"+ row.get(0) +"'");
				System.out.println( "taskSubRes:" + taskSubRes1);
				
				//if 2
				if(taskSubRes1 != null )
				{
					ArrayList<String> row1 = taskSubRes1.get(0);
				if(row1.get(4)==null)
					SubtaskGrade1.setText("Task Grade : ");
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
					SubtaskGrade1.setText("");
			    	SubtaskComments1.setText("");
					ErrorMSG.setText("There is Not submitted Task  ");
				}
				
			}
				
		}
		//else 1
		else
		{
			SubtaskGrade1.setText("");
	    	SubtaskComments1.setText("");
			ErrorMSG.setText("There is Not  Task in this course ");
		}*/


}
}








