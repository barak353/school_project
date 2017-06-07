package Secretary;

import java.net.URL;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;

import Login.LoginController;
import Entity.Course;
import Entity.Semester;
import SystemManager.SystemManagerAddCourseController;
import Entity.User;
import application.QueryController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class OpenNewSemesterController extends QueryController implements Initializable{
	
	//-----------------------------------------------------------//
	
	public OpenNewSemesterController (String controllerID)
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
    private TextField type;

    @FXML
    private Text currentDate;
    
    @FXML
    private Button doneButton;

    @FXML
    private Button addButton;

    @FXML
    private DialogPane dialogID;
    
    @FXML
    private Text dialogText;

    @FXML
    private Text errorText;
    
    @FXML
    void openSemester(ActionEvent event) {
    	errorText.setText("");
    	boolean isValidInput = true;
    	String type = this.type.getText();
    	if(!type.equals("A") && !type.equals("B") && !type.equals("C") && !type.equals("a") && !type.equals("b") && !type.equals("c")){errorText.setText("Type most be just 'A' or 'B' or 'C'"); isValidInput = false;}
    	Object obj = null;
    	int r = 0;
        ArrayList<ArrayList<String>> resultArray= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM courses");
        System.out.println("resultArray: "+resultArray);
    	if(isValidInput == true){
    		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy");
    		LocalDateTime now = LocalDateTime.now();
            System.out.println(dtf.format(now));
    		Semester sem = new Semester(dtf.format(now),this.type.getText(),true);
    		dialogText.setText("Choose from the comboBox which course you want to add to semester: Year: "+sem.getYear()+" Type: " + sem.getType());
    		Semester.setCurrentSemester(sem);
    		dialogText.setVisible(true);
    		dialogID.setVisible(true);
    	    doneButton.setVisible(true);
    	    addButton.setVisible(true);
    	}
    	
	 	//if(isValidInput == true){obj =  transfferQueryToServer("INSERT INTO semester VALUES ("+currentdate+","+",1)");}
    	//if(isValidInput && obj != null) r = (int) obj;//we want to check if the query was successful.
   	 	//if(isValidInput && r != -1){//show add pre-course screen if the course was added successful.
    	
   	 	}
    /*	boolean isValidInput = true;
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
	    		if(res != null)errorID.setText("ERROR: this course ID is already in DB.");
	    		else errorID.setText("ERROR: Teaching unit is not exist in the DB.");
    		}// check if the course ID is already exist in the DB or the course ID is not exist.
    	 }*/
    
    

	    @FXML
	    void addCourse(ActionEvent event) {

	    }
	    @FXML
	    void done(ActionEvent event) {
    		dialogText.setVisible(false);
    		dialogID.setVisible(false);
    	    doneButton.setVisible(false);
    	    doneButton.setVisible(false);
    	    addButton.setVisible(false);
	    }//hide the add courses window when done as pressed.

		@FXML
		void TurningBack(ActionEvent event)
		{
			this.nextController = new SecretaryMainController("SecretaryMainControllerID");
			this.Back("/Secretary/SecretaryMainWindow.fxml",nextController, event);
		}

		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {//this method perform when this controller scene is showing up.
			User user = User.getCurrentLoggedIn();
			userID.setText(user.GetUserName());
	        LocalDate localDate = LocalDate.now();
	        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
	        LocalDateTime now = LocalDateTime.now();
	    	currentDate.setText("Open new semester for the current date: " + dtf.format(now));
		}
}