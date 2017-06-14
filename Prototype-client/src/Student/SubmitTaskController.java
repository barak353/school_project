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

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import javax.swing.JFileChooser;

import Secretary.AskRequestFormController;
import Secretary.SecretaryMainController;
import Entity.Course;
import Entity.Task;
import Entity.User;



public class SubmitTaskController extends QueryController implements Initializable {

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

    private Task task;

    private File file;
    
    private boolean isTaskChoosed = false;

/**This function is enabled after the user has chosen a course and a specific task**/
    @FXML
    void submitTask(ActionEvent event) {
    	ErrorMSG.setText(" ");
    	if(isTaskChoosed == true){
        	//save file to server.
    		String v = comboBoxChooseCourse.getValue();
    		String courseID = v.substring(v.indexOf("(") + 1, v.indexOf(")"));
    		String studentID = User.getCurrentLoggedIn().GetID();
    		System.out.println("file path: " + courseID + "//" + studentID);
    		deleteFolderFromServer(courseID,studentID);
    		Object ans = uploadFileToServer(file,courseID + "//" + studentID);
    		ErrorMSG.setText("The file was submitted succesfuly to the server.");
    	}else{
    		ErrorMSG.setText("Please choose task.");
    	}
    }
    
    @FXML
    void uploadTask(ActionEvent event) {
		JFileChooser chooser= new JFileChooser();
		int choice = chooser.showOpenDialog(chooser);
		if (choice != JFileChooser.APPROVE_OPTION) return;
		file = chooser.getSelectedFile();
		if (file.exists())
			System.out.println("file or directory denoted by this abstract pathname exists.");
		else
			System.out.println("file or directory denoted by this abstract pathname is not exists.");
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
		isTaskChoosed = false;
    	ErrorMSG.setText(" ");
    	// save the student's choise
		String chooseCourse = comboBoxChooseCourse.getValue();
		String idcourses = chooseCourse.substring(chooseCourse.indexOf("(") + 1, chooseCourse.indexOf(")"));//get the idcourses that is inside a ( ).
		ArrayList<ArrayList<String>> IdTaskInCourseList = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT TaskName FROM task WHERE idcorse="+idcourses);
		if(IdTaskInCourseList==null)
			ErrorMSG.setText("There is NO Tasks in this course.");//show error message.
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
    	ErrorMSG.setText(" ");
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
		     DateFormat df = new SimpleDateFormat("mm-dd-yyyy");
		     try {
				task = new Task(row.get(0),row.get(1),row.get(2),(Date) df.parse(row.get(3)));
				isTaskChoosed = true;
			} catch (ParseException e) {
				System.out.println("Error in format from string to date.");
			}
		}
	}
}








