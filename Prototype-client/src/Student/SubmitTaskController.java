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
import Entity.Semester;
import Entity.Task;
import Entity.User;
import Login.LoginController;


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
    
    private boolean isCourseChoosed = false;
    private boolean isTaskChoosed = false;
    private boolean isFileUploaded = false;
    
    private String chooseCourse;
    private  String choosedTask;
    private String idTeacher;
    private String idTClass;
    private String userID1;
    
    
/**
 * After pressing the appropriate button, this function enters the details of the submitted  task to the DB
 * @param event
 */
    @FXML
    void submitTask(ActionEvent event){
    	ErrorMSG.setText(" ");
    	if (isCourseChoosed == true)
    	{
    	if (isTaskChoosed == true)
    	{ 
    	String IDNcourse = task.getIdcourse();
    	String TaskName = task.getTaskName(); 
    	String IDstudent = User.getCurrentLoggedIn().GetID();
    	String IDsem = task.getIDsem();
    	String IDsemQ = Semester.getCurrentSemester().getYear() + Semester.getCurrentSemester().getType();
        if(isFileUploaded == true)
        {
	        	//save file to server.
	    		String v = comboBoxChooseCourse.getValue();
	    		String courseID = v.substring(v.indexOf("(") + 1, v.indexOf(")"));
	    		String studentID = User.getCurrentLoggedIn().GetID();
	    		String semQ = Semester.getCurrentSemester().getYear()+":"+Semester.getCurrentSemester().getType();
	            ArrayList<ArrayList<String>> res =(ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT fileExtN FROM subtask WHERE semesterName='"+ semQ +"' AND mytaskname='" 
						+ TaskName + "' AND IDNcourse=" + courseID 
							+ " AND stIDENT=" + studentID);
	            if(res != null && (res.get(0) != null || res.get(0).get(0) != null)){
	            	ErrorMSG.setText("Override submmited file.");
	            	deleteFileFromServer(IDsemQ+"//"+courseID+"//"+studentID, res.get(0).get(0));
        		}
	    		String semYT = Semester.getCurrentSemester().getYear()+Semester.getCurrentSemester().getType();
	    		Object ans = uploadFileToServer(file,semYT + "//" +courseID + "//" + studentID);
				//add the sub task details to the db
                 //--------------------------------------------------------------------------------------------------------
	    		Object obj1 =transfferQueryToServer("DELETE FROM subtask WHERE stIDENT=" + IDstudent + " AND mytaskname='" + TaskName + "' AND semesterName='" 
	    										+ IDsem + "' AND IDNcourse=" + IDNcourse);
                Object obj =transfferQueryToServer("INSERT INTO subtask (stIDENT,mytaskname,semesterName,IDNcourse,Mark,fileExtN) VALUES (" + studentID + ",'" + TaskName + "','" + IDsem + "'," + IDNcourse + "," 
                									+ mark + ",'"+ file.getName() +"')");
                if( obj==null )//if INSERT operation had failed
     	    		ErrorMSG.setText("You succeed, task was upload.");
                else if((int)obj==-1 )//if INSERT operation had failed
                		ErrorMSG.setText("You have already inserted this data into DB.");
                	 else{
         	    		ErrorMSG.setText("You have successfully inserted the data into DB.");
         	    		isFileUploaded = false;
         	    		isTaskChoosed = false;
                	 }
    	}//isFileUploaded
        else ErrorMSG.setText("Please upload a file for submition.");
    }//isTaskChoosed
    else
		ErrorMSG.setText("Please choose a task.");
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
	  	this.Back("/Student/MainWindowStudent.fxml",nextController, event);
	 } 
	  
		@Override
		public void initialize(URL arg0, ResourceBundle arg1) 
		{//this method perform when this controller scene is showing up.
			User user = User.getCurrentLoggedIn();
			userID.setText(user.GetUserName());
			 userID1=user.GetID();
			ArrayList<ArrayList<String>> StudentInCourseList = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT identityCourse FROM studentincourse WHERE identityStudent="+userID1);	
			if(StudentInCourseList == null)
			{
				ErrorMSG.setText("You are not enrolled in courses");//show error message.
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
	 * @param event
	 * **/		
			
	@FXML
	void AfterChooseCourse(ActionEvent event)
	{
		isFileUploaded = false; 
		isTaskChoosed = false;
		isCourseChoosed=false;
    	ErrorMSG.setText(" ");
    	// save the student's choise
		 chooseCourse = comboBoxChooseCourse.getValue();
		String idcourses = chooseCourse.substring(chooseCourse.indexOf("(") + 1, chooseCourse.indexOf(")"));//get the idcourses that is inside a ( ).
		ArrayList<ArrayList<String>> resClass = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT IdenClas FROM studentincourse WHERE identityCourse="+idcourses
				+" AND identityStudent="+userID1);
		if(resClass==null)
		{
			 ErrorMSG.setText("There is NO class in this course.");//show error message.
			 return;
		}
		idTClass = 	resClass.get(0).get(0);
		System.out.println("idTClass=" +idTClass);
		ArrayList<ArrayList<String>> resTeacher = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT Tidentity FROM teacherinclassincourse WHERE coID="+idcourses
				+" AND clasID='"+idTClass+"'");
		if(resTeacher==null)
		{
			 ErrorMSG.setText("There is NO teacher in this course.");//show error message.
			 return;
		}
		idTeacher = resTeacher.get(0).get(0);
		System.out.println("idTeacher=" +idTeacher);
		ArrayList<ArrayList<String>> IdTaskInCourseList = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT TaskName FROM task WHERE idcorse="+idcourses
				+" AND Teach="+idTeacher);
		
		if(IdTaskInCourseList==null)
		{
			comboBoxChooseTask.getItems().clear();
			ErrorMSG.setText("There is NO Tasks in this course");//show error message.
			isCourseChoosed=false;
		}
		
		else
		{
			isCourseChoosed=true;
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
 * @param event
 * **/	
	@FXML
	void AfterChooseTask(ActionEvent event)
	{
		if (isCourseChoosed==false)
		{
			isTaskChoosed = false;
	    	isFileUploaded = false;
		}
		else {
		User user = User.getCurrentLoggedIn();
    	ErrorMSG.setText(" ");
    	isTaskChoosed = false;
    	isFileUploaded = false;
		String choosedCourse = comboBoxChooseCourse.getValue();
		choosedCourse = choosedCourse.substring(choosedCourse.indexOf("(") + 1, choosedCourse.indexOf(")"));//get the idcourses that is inside a ( ).
		 choosedTask = comboBoxChooseTask.getValue();
		System.out.println("choosedCourse: "+choosedCourse+" choosedTask: "+choosedTask);
		String curSem = Semester.getCurrentSemester().getYear()+":"+Semester.getCurrentSemester().getType();
		ArrayList<ArrayList<String>> taskRes = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM task WHERE idcorse="+choosedCourse +" AND TaskName='"+choosedTask+"'"+" AND IDsem='"+ curSem +"'");
		String idTask = null;
		if(taskRes != null && taskRes.get(0) != null) 
		{
		     ArrayList<String> row = taskRes.get(0);
		     ErrorMSG.setText("");
		     //-----------------------------------------------------------
		    // check if the sub date already pass
		     LocalDate now = LocalDate.now();
		     String DateDB=row.get(3).substring(0, 12);
			 task = new Task(row.get(0),row.get(1),row.get(2), DateDB, row.get(4));
			 isTaskChoosed = true;
       
			if ((now.toString().compareTo(DateDB) > 0))
			{
				
				ErrorMSG.setText("The submmision date is pass!");
				mark = 1;
			}
			else
			{
				mark = 0;
			
			}
		
		}
	
		}}
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
}








