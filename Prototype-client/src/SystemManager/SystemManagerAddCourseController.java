package SystemManager;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;
import Entity.Course;
import Entity.Semester;
import Login.LoginController;
import Parent.ChildDetailsController;
import Parent.ChoiceChildController;
import Entity.User;
import application.QueryController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SystemManagerAddCourseController extends QueryController implements Initializable{
	
	//-----------------------------------------------------------//
	
	public SystemManagerAddCourseController (String controllerID)
	{
			super(controllerID);
	}
	
	//-----------------------------------------------------------// 
	Object nextController=null;	

    @FXML
    private TextField hours;

    @FXML
    private Text currentSemester;

    @FXML
    private Button addCourseButton;

    @FXML
    private Button back;

    @FXML
    private Text userID;

    @FXML
    private TextField TeachingUnit;

    @FXML
    private Button logout;

    @FXML
    private TextField courseName;

    @FXML
    private Button addPreCourseButton;

    @FXML
    private TextField course;

    @FXML
    private Text errorID;
   
    @FXML
    private Text add;
    
    private String lastCourse = "";
    private String nameCourse = "";
    private String teachingUnit = "";
    private String Hours = "";
    
	//-----------------------------------------------------------// 
    
    @FXML
    void addCourse(ActionEvent event) {
    	boolean isValidInput = true;
    	
    	lastCourse = course.getText();
    	nameCourse = courseName.getText();
    	teachingUnit = TeachingUnit.getText();
    	Hours = hours.getText();

    	errorID.setText("");
    	//empty field ID course
    	if(lastCourse.equals("")==true){
    		errorID.setText("ERROR: Please insert valid course ID.");
    		isValidInput = false;
    	}

    	else if (lastCourse.length() > 5){
    		errorID.setText("Course ID can be longer then 5 digits.");
    		isValidInput = false;
    	}
    	
    	
    	try{//check if the course ID is only numbers.
    		Integer.parseInt(lastCourse);
    	}
    	catch(NumberFormatException e){
    		errorID.setText("ERROR: Please insert valid course ID, Course ID most contain only numbers."); 
    		isValidInput = false;
    	}
    	
    	
    	//Checks if there is an empty field
    	if(nameCourse.equals("")==true || teachingUnit.equals("")==true || Hours.equals("")==true){
    		errorID.setText("ERROR: Please fill out all fields.");
    		isValidInput = false;
    	}
    


    	int r = 0;
    	Object obj = null;
	 	if(isValidInput == true){
	 		obj =  transfferQueryToServer("INSERT INTO courses VALUES ("+course.getText()+",'"+courseName.getText()+"',"+TeachingUnit.getText()+","+hours.getText()+")");
	 	}
    	if(isValidInput && obj != null){ 
    		r = (int) obj;//we want to check if the query was successful.
    	}
    	if(isValidInput && obj == null){ 
			add.setText("The course was successfully added in DB."); 
    	}

   	 	if(isValidInput && r != -1){//show add pre-course screen if the course was added successful.
    	}
   	 	else{//if the course encounter some error so let's check which error is it.
    		if(isValidInput == true){//check if the course ID is less then 5 digits.
	        	ArrayList<ArrayList<String>> res = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT idcourses FROM courses WHERE idcourses = "+course.getText());
	    		if(res != null){
	    			errorID.setText("ERROR: this course ID is already in DB."); 
	    			isValidInput = false;
	    		}
	    		else {
	    			errorID.setText("ERROR: Teaching unit is not exist in the DB."); 
	    			isValidInput = false;
	    		}
    		}// check if the course ID is already exist in the DB or the course ID is not exist.
    	 }
   	 	addPreCourseButton.setVisible(true);

    	}
    
    
		//------------------------------------------// 
		
	    @FXML
	    void addPreCourse(ActionEvent event) {
	    	try {
	    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/SystemManager/SystemManagerAddPreCourseWindow.fxml"));
	    		SystemManagerAddPreCourseController controller = new SystemManagerAddPreCourseController("SystemManagerAddPreCourseController");
	    		controller.setCourseID(lastCourse);
		        loader.setController(controller);
				   Pane login_screen_parent = loader.load();
			        Scene login_screen_scene=new Scene(login_screen_parent);
					Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();//the scene that the event came from.
					app_stage.hide();
					app_stage.setScene(login_screen_scene);
					app_stage.show(); 
		        } catch (IOException e) {//problem with the teacherWindow.xml file.
					System.err.println("Missing SystemManagerAddPreCourseWindow.fxml file");
					e.printStackTrace();
				}
	 	
	    }
	 
    
	//------------------------------------------// 


		@FXML
		void TurningBack(ActionEvent event)
		{
			this.nextController = new SystemManagerMainController("SystemManagerMainControllerID");
			this.Back("/SystemManager/SystemManagerMainWindow.fxml",nextController, event);
		}

		

		//------------------------------------------// 
	    
	    
		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {//this method perform when this controller scene is showing up.
			User user = User.getCurrentLoggedIn();
			userID.setText(user.GetUserName());
			
		}
	    
 }