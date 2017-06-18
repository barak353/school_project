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
import javafx.scene.control.DatePicker;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.chrono.ChronoLocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import javax.swing.JFileChooser;

import Secretary.AskRequestFormController;
import Secretary.SecretaryMainController;
import Entity.Course;
import Entity.Task;
import Entity.User;


/**
 * 
 * this controller handles the action: submission task by student
 * 
 */
public class SubmitTaskController extends QueryController implements Initializable {

	private static final String DB = null;

	//-----------------------------------------------------------//
	public SubmitTaskController(String controllerID) 
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

    private	ArrayList<String> TaskNameList = new ArrayList<String>();

    @FXML
    private Text ErrorMSG;
    
    @FXML
    private DatePicker setDate;

    private Task task;

    private File file;
    
    private int mark;
    
    private boolean isTaskChoosed = false;
    private boolean isFileUploaded = false;
    private String chooseCourse;
    private  String choosedTask;
    
    
/**
 * After pressing the appropriate button, this function enters the details of the submitted  task to the DB
 * 
 */
    @FXML
    void submitTask(ActionEvent event) {
    	
    	ErrorMSG.setText(" ");
    	String IDcourse = task.getIdcourse();
    	String idTASK = task.getIdTASK(); 
    	String IDstudent = User.getCurrentLoggedIn().GetID();
    	if(isTaskChoosed == false)
    		ErrorMSG.setText("Please choose a task.");
    		
    	else{
    		if(isFileUploaded == true){
	        	//save file to server.
	    		String v = comboBoxChooseCourse.getValue();
	    		String courseID = v.substring(v.indexOf("(") + 1, v.indexOf(")"));
	    		String studentID = User.getCurrentLoggedIn().GetID();
	    		System.out.println("file path: " + courseID + "//" + studentID);
	    		deleteFolderFromServer(courseID,studentID);
	    		Object ans = uploadFileToServer(file,courseID + "//" + studentID);
	    
				//add the sub task details to the db
				
 				
                 //--------------------------------------------------------------------------------------------------------
				
                Object obj =transfferQueryToServer("INSERT INTO subtask (idTASK,IDcourse,IDstudent,Mark) VALUES (" + idTASK + "," + courseID + "," + studentID + "," + mark + ")");
                if( obj==null )//if INSERT operation had failed
                	 System.out.println("You have failed inserted the data into DB:\ntask "  );
                   if((int)obj==-1 )//if INSERT operation had failed
                System.out.println("You have already this  inserted the data into DB:\ntask "  );
                else
                	System.out.println("You have successfully inserted the data into DB:\ntask "  );
                //--------------------------------------------------------------------------------------------------------
		
	    		ErrorMSG.setText("The file was submitted succesfuly to the server.");
    		}else ErrorMSG.setText("Please upload a file for submition.");
    	}
    }
/**
 * After pressing the appropriate button, this function loads the task file    
 * @param event
 */
    @FXML
    void uploadTask(ActionEvent event) {
    	isFileUploaded = false;
		JFileChooser chooser= new JFileChooser();
		int choice = chooser.showOpenDialog(chooser);
		if (choice != JFileChooser.APPROVE_OPTION) return;
		file = chooser.getSelectedFile();
		isFileUploaded = true;
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
		    	
		   		
		    		System.out.println("(CoursesNameList.get(0)).get(1): "+(CoursesNameList.get(0)).get(1));
			        courseNameList.add((CoursesNameList.get(0)).get(0)+"("+(CoursesNameList.get(0)).get(1)+")");
			        ObservableList obList= FXCollections.observableList(courseNameList);
			    	if(CoursesNameList ==null)
			    		obList.clear();
			    	else
			        comboBoxChooseCourse.setItems(obList);
		    	}
			}
		}

	/** 
	 * This function handle with choosing the specific course and presenting its assignments
	 * **/		
			
	@FXML
	void AfterChooseCourse(ActionEvent event)
	{
		isFileUploaded = false; 
		isTaskChoosed = false;
    	ErrorMSG.setText(" ");
    	// save the student's choise
		 chooseCourse = comboBoxChooseCourse.getValue();
		String idcourses = chooseCourse.substring(chooseCourse.indexOf("(") + 1, chooseCourse.indexOf(")"));//get the idcourses that is inside a ( ).
		ArrayList<ArrayList<String>> IdTaskInCourseList = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT TaskName FROM task WHERE idcorse="+idcourses);
		
		if(IdTaskInCourseList==null)
		{
			comboBoxChooseTask.getItems().clear();
			ErrorMSG.setText("There is NO Tasks in this course.");//show error message.
		}
		
		else
		{
			comboBoxChooseTask.getItems().clear();
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
	void AfterChooseTask(ActionEvent event) {
		User user = User.getCurrentLoggedIn();
    	ErrorMSG.setText(" ");
    	isTaskChoosed = false;
    	isFileUploaded = false;
		String choosedCourse = comboBoxChooseCourse.getValue();
		choosedCourse = choosedCourse.substring(choosedCourse.indexOf("(") + 1, choosedCourse.indexOf(")"));//get the idcourses that is inside a ( ).
		 choosedTask = comboBoxChooseTask.getValue();
		System.out.println("choosedCourse: "+choosedCourse+" choosedTask: "+choosedTask);
		ArrayList<ArrayList<String>> taskRes = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM task WHERE idcorse="+choosedCourse +" AND TaskName='"+choosedTask+"'");
		System.out.println("taskRes: "+taskRes);
		String idTask = null;
		if(taskRes != null && taskRes.get(0) != null) 
		{
		     ArrayList<String> row = taskRes.get(0);
		     ErrorMSG.setText("");
		     //-----------------------------------------------------------
		    // check if the sub date already pass
		     LocalDate now = LocalDate.now();
		     String DateDB=row.get(3).substring(0, 12);
			 task = new Task(row.get(0),row.get(1),row.get(2), DateDB);
			 isTaskChoosed = true;
       
			if ((now.toString().compareTo(DateDB) > 0))
			{
				System.out.println("now gratter then DateDB");
				ErrorMSG.setText("The submmision date is pass!!!");
				mark = 1;
			}
			else
			{
				mark = 0;
				System.out.println("db is gratter then now");
				
			}
		
		}
	}
}








