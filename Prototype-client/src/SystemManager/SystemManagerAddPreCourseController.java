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

public class SystemManagerAddPreCourseController extends QueryController implements Initializable{
	//-----------------------------------------------------------//
	
	public SystemManagerAddPreCourseController (String controllerID)
	{
			super(controllerID);
	}
	
	//-----------------------------------------------------------// 
	Object nextController=null;	
    
    @FXML
    private Button logout;

    @FXML
    private Text currentSemester;

    @FXML
    private TextField preCourses;

    @FXML
    private Text errorIDdialog;

    @FXML
    private Button back;

    @FXML
    private Button addButton;

    @FXML
    private Text errorID;

    @FXML
    private Text userID;

    @FXML
    private Button doneButton;


    private String lastCourse;
    
    private String precourses ="";

	
	//------------------------------------------// 
	
	@FXML
	void TurningBack(ActionEvent event)
	{
		this.nextController = new SystemManagerAddCourseController("SystemManagerAddCourseController");
		this.Back("/SystemManager/SystemManagerAddCourse.fxml",nextController, event);
	}

	
	//------------------------------------------// 

    
	public void setCourseID(String lastCourse) {
		// TODO Auto-generated method stub
		this.lastCourse = lastCourse;
	}

    
	//------------------------------------------// 

		
    @FXML
    void addPrecourse(ActionEvent event) 
    {    
    	preCourses.clear();
    	errorID.setVisible(true);
    	boolean isValidInput = true;
    	precourses=preCourses.getText();

    	errorID.setText("");
    	//empty field ID course
    	if(precourses.equals("")==true){
    		errorID.setText("ERROR: Please insert valid course ID."); 
    		isValidInput = false;
    	}
    	
    	else if (precourses.length() > 5){
    		errorID.setText("Course ID can be longer then 5 digits.");
    		isValidInput = false;
    	}

    	else {
    		try{//check if the course ID is only numbers.
    			Integer.parseInt(precourses);
    		}
    		catch(NumberFormatException e){
    			errorID.setText("ERROR: Please insert pre-course, pre-course ID most contain only numbers."); 
    			isValidInput = false;
    		}
    	}
    	
    	Object obj = null;
	 	if(isValidInput){
	 		obj = transfferQueryToServer("INSERT INTO precourse  VALUES ("+precourses+",'"+lastCourse+"')");
	 	}
	 	
	 	int r = 0;
	 	if(isValidInput && obj != null) r = (int)obj;
	    if(isValidInput && r == -1){//if we encounter a error let's check which error it was.
	        ArrayList<ArrayList<String>> res = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT idcourses FROM courses WHERE idcourses = "+lastCourse);
	        if(res == null)
	        	errorID.setText("ERROR: pre-course ID is not exist in the DB.");
	        else 
	        	errorID.setText("ERROR: this pre-course ID is already in DB."); 
	    }
    }
    
    
	//------------------------------------------// 

	
    @FXML
    void done(ActionEvent event) {
   
    	errorIDdialog.setText("");
    	//pleaseAdd.setText("Please add pre-course for course number: " + lastCourse);
    	errorIDdialog.setVisible(false);
    	
    }//hide the add pre-course window when done was pressed.

    
  //------------------------------------------// 
    
    
  	@Override
  	public void initialize(URL arg0, ResourceBundle arg1) {//this method perform when this controller scene is showing up.
  		User user = User.getCurrentLoggedIn();
  		userID.setText(user.GetUserName());
  		
  	}
  	 

}
