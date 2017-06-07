package SystemManager;

import java.net.URL;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;
import Entity.Course;
import Entity.Semester;
import Login.LoginController;
import Entity.User;
import application.QueryController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class SystemManagerAddCourseController extends QueryController implements Initializable{
	
	//-----------------------------------------------------------//
	
	public SystemManagerAddCourseController (String controllerID)
	{
			super(controllerID);
	}
	
	//-----------------------------------------------------------// 

	Object nextController=null;	

    @FXML
    private Button logout;

    @FXML
    private Text userID;

    @FXML
    private Button back;

    @FXML
    private TextField course;

    @FXML
    private TextField courseName;

    @FXML
    private TextField TeachingUnit;

    @FXML
    private TextField hours;

    @FXML
    private DialogPane addPrecourse;


    @FXML
    private Text currentDate;
    
    @FXML
    private Text pleaseAdd;

    @FXML
    private Button doneButton;

    @FXML
    private Button addButton;

    @FXML
    private Text errorIDdialog;
    
    @FXML
    private Text errorID;

    @FXML
    private TextField preCourses;
    
    private String lastCourse = "";
    
    @FXML
    private Text currentSemester;
    
    @FXML
    void addCourse(ActionEvent event) {
    	boolean isValidInput = true;
		errorID.setText("");
    	lastCourse = course.getText();//save the course that was added to the DB.
    	if( lastCourse == "" ){errorID.setText("Please insert valid course ID.");isValidInput = false;}
    	else if (lastCourse.length() > 5){errorID.setText("Course ID can be longer then 5 digits.");isValidInput = false;}
    	try{//check if the course ID is only numbers.
    		Integer.parseInt(lastCourse);
    	}catch(NumberFormatException e){errorID.setText("Course ID most contain only numbers."); isValidInput = false;}
    	int r = 0;
    	Object obj = null;
	 	if(isValidInput == true){obj =  transfferQueryToServer("INSERT INTO courses VALUES ("+course.getText()+",'"+courseName.getText()+"',"+TeachingUnit.getText()+","+hours.getText()+")");}
    	if(isValidInput && obj != null) r = (int) obj;//we want to check if the query was successful.
   	 	if(isValidInput && r != -1){//show add pre-course screen if the course was added successful.
	    	addPrecourse.setVisible(true);
	    	preCourses.setVisible(true);
	    	preCourses.setVisible(true);
	    	doneButton.setVisible(true);
	    	pleaseAdd.setVisible(true);
	    	addButton.setVisible(true);
    	}else{//if the course encounter some error so let's check which error is it.
    		if(isValidInput == true){//check if the course ID is less then 5 digits.
	        	ArrayList<ArrayList<String>> res = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT idcourses FROM courses WHERE idcourses = "+course.getText());
	    		if(res != null){errorID.setText("ERROR: this course ID is already in DB."); isValidInput = false;}
	    		else {errorID.setText("ERROR: Teaching unit is not exist in the DB."); isValidInput = false;}
    		}// check if the course ID is already exist in the DB or the course ID is not exist.
    	 }
    }
    

		
	    @FXML
	    void addPrecourse(ActionEvent event) {
	    	errorIDdialog.setVisible(true);
	    	boolean isValidInput = true;
	    	errorIDdialog.setText("");
	    	if( preCourses.getText() == "" ){errorIDdialog.setText("Please insert valid course ID."); isValidInput = false;}
	    	else if (preCourses.getText().length() > 5)errorIDdialog.setText("Course ID can be longer then 5 digits.");
	    	else {
	    		try{//check if the course ID is only numbers.
	    			Integer.parseInt(preCourses.getText());
	    		}catch(NumberFormatException e){errorIDdialog.setText("pre-course ID most contain only numbers."); isValidInput = false;}
	    	}
	    	Object obj = null;
    	 	if(isValidInput){obj = transfferQueryToServer("INSERT INTO precourse  VALUES ("+lastCourse+",'"+preCourses.getText()+"')");}
    	 	int r = 0;
    	 	if(isValidInput && obj != null) r = (int)obj;
    	    if(isValidInput && r == -1){//if we encounter a error let's check which error it was.
    	        ArrayList<ArrayList<String>> res = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT idcourses FROM courses WHERE idcourses = "+course.getText());
    	    	if(res != null)errorID.setText("ERROR: this re-course ID is already in DB.");
    	    	else errorID.setText("ERROR: pre-course ID is not exist in the DB.");
    	    }
	    }
		
		@FXML
		void TurningBack(ActionEvent event)
		{
			this.nextController = new SystemManagerMainController("SystemManagerMainControllerID");
			this.Back("/SystemManager/SystemManagerMainWindow.fxml",nextController, event);
		}


	    @FXML
	    void done(ActionEvent event) {
	    	errorIDdialog.setText("");
	    	addPrecourse.setVisible(false);
	    	preCourses.setVisible(false);
	    	doneButton.setVisible(false);
	    	pleaseAdd.setVisible(false);
	    	addButton.setVisible(false);
	    	pleaseAdd.setText("Please add pre-course for course number: " + lastCourse);
	    	errorIDdialog.setVisible(false);
	    }//hide the add pre-course window when done was pressed.

		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {//this method perform when this controller scene is showing up.
			User user = User.getCurrentLoggedIn();
			userID.setText(user.GetUserName());
			
		}
	    
 }