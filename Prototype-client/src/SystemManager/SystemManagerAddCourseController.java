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
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * 
 * This controller handles the System Manager actions and is responsible for presenting the Add Course screen
 *
 */

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
    private Button logout;
    @FXML
    private TextField courseName;
    @FXML
    private Button addPreCourseButton;
    @FXML
    private Text course;
    @FXML
    private Text errorID;
    @FXML
    private Text add;
    @FXML
    private ComboBox<String> TeachingUnit;
    @FXML
    private Text Coursetext;
    @FXML
    private Button addMoreCourseButton;
    
    private String lastCourse = "";
    private String nameCourse = "";
    private String Hours = "";
    private String teachingUnit;
    private int flag=0;

	//-----------------------------------------------------------// 
   
    /**
     *  After pressing the addCourse button, this function check if the user fill out all fields and choose from the ComboBox teaching Unit,
     *  if yes, the system Inserts data into the database 
     *  and the system show the ID course,the add Pre-Course Button and the add More Course Button
     * @param event
     */   
    
    @FXML
    void addCourse(ActionEvent event){
    	boolean isValidInput = true;
    	nameCourse = courseName.getText();
    	Hours = hours.getText();
    	errorID.setText("");
		add.setText("");
		course.setText("");
		
    	try{	//check if the filed hours is only numbers.
    		Integer.parseInt(Hours);
    	}
    	catch(NumberFormatException e){
    		errorID.setText("ERROR:\nHours most contain only numbers."); 
    		add.setText("");
    		course.setText("");
    		isValidInput = false;
    	}
   	
    	if(nameCourse.equals("")==true || Hours.equals("")==true){	//checks if there is an empty field
    		errorID.setText("ERROR:\nPlease fill out all fields.");
    		add.setText("");
    		course.setText("");
    		isValidInput = false;
    	}
    	
    	else if(flag == 0){	//not choose Teaching Unit.
    		errorID.setText("ERROR:\nPlease choose Teaching Unit.");
    		add.setText("");
    		course.setText("");
    		isValidInput = false;
    	}
		
	    ArrayList<ArrayList<String>> res = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT courseName FROM courses WHERE courseName='" + nameCourse +"'");
	    if(res != null){ // check if the name course is already exist in the DB or not exist.
			errorID.setText("ERROR:\nthis Name Course is exist in DB, Choose another name."); 
    		add.setText("");
    		course.setText("");
    		isValidInput = false;
		}
    	
    	Object obj = null;
    	String idteachingUnit = teachingUnit.substring(teachingUnit.indexOf("(") + 1, teachingUnit.indexOf(")"));//get the idteachingUnit that is inside a ( ).
    	System.out.println("idteachingUnit: "+ idteachingUnit);
    	
    	if(isValidInput == true){  //if the data were inserted properly, added in DB.
	 		obj =  transfferQueryToServer("INSERT INTO courses(courseName, teachingUnit, hours) VALUES ('"+courseName.getText()+"','"+idteachingUnit+"',"+hours.getText()+")");
	    	if(obj == null){ //check if data insert to DB
				add.setText("The course was successfully added in DB.");
				addPreCourseButton.setVisible(true);
				addMoreCourseButton.setVisible(true);
		    	errorID.setText("");
		    	
				//The number the DB gave to the ID course
				ArrayList<ArrayList<String>> res1= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT idcourses FROM courses WHERE courseName='" + nameCourse +"'");
		        if(res1==null){
		        	errorID.setText("NO found name courses");//show error message.
		    		add.setText("");
		    		course.setText("");
		    		isValidInput = false;
		        }
		        else{
		    		ArrayList<String> chooseIDcourse = new ArrayList<String>();
		    		chooseIDcourse.add(res1.get(0).get(0));
		    		String[] id = new String[chooseIDcourse.size()];
		    		id = chooseIDcourse.toArray(id);
		    		Coursetext.setVisible(true);
		    		course.setText(id[0]);
		    		lastCourse=id[0];
		        }
	    	}
    	}
    }
    
		//------------------------------------------// 
		
    /**
     *  After pressing the addPreCourse button,
     *  the system Continue to the next screen -  Add Pre-Course
     *  and sends the lastCourse to the next Controller
     * @param event
     */   

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
    		} 
	    	catch (IOException e) {//problem with the teacherWindow.xml file.
				System.err.println("Missing SystemManagerAddPreCourseWindow.fxml file");
				e.printStackTrace();
			}
    	}
	 
    
	//------------------------------------------//
    
    /**
     *  After pressing the addmoreCourse button,
     *  the system Continue to the next screen -  Add Course
     * @param event
     */ 
    
    	@FXML
    	void addmoreCourse(ActionEvent event) {
			 try {
				   FXMLLoader loader = new FXMLLoader(getClass().getResource("/SystemManager/SystemManagerAddCourseWindow.fxml"));
				   loader.setController(new SystemManagerAddCourseController("SystemManagerAddCourseControllerID"));
				   Pane login_screen_parent = loader.load();
				        Scene login_screen_scene=new Scene(login_screen_parent);
						Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();//the scene that the event came from.
						app_stage.hide();
						app_stage.setScene(login_screen_scene);
						app_stage.show(); 
			        } catch (IOException e) {//problem with the teacherWindow.xml file.
						System.err.println("Missing SystemManagerAddCourseWindow.fxml file");
						e.printStackTrace();
					}
	    }

    	//-----------------------------------------------------------//
    	   
        /**
         * function that return to the last screen
         * @param event
         */
    	
		@FXML
		void TurningBack(ActionEvent event){
			this.nextController = new SystemManagerMainController("SystemManagerMainControllerID");
			this.Back("/SystemManager/SystemManagerMainWindow.fxml",nextController, event);
		}
		

		//------------------------------------------// 
	    
	    /**
	     * this function initialize the screen whit the name of the user and the ComboBox teaching unit list.
	     */

		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {//this method perform when this controller scene is showing up.
			User user = User.getCurrentLoggedIn();
			userID.setText(user.GetUserName());
			
		    ArrayList<ArrayList<String>> res = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM teachingunit");
	        if(res==null){
	        	errorID.setText("NO found teaching unit");//show error message.
	        }
	        else{
			    ArrayList<String> teachingunitList = new ArrayList<String>();
		    	for(ArrayList<String> row:res){
		    		teachingunitList.add(row.get(1)+" ("+row.get(0)+")");
		    	}
			    ObservableList obList= FXCollections.observableList(teachingunitList);
			    TeachingUnit.setItems(obList);
	        }
		}
		
		
		//------------------------------------------// 

	    /**
	     * this function check if the user choose teaching Unit from the ComboBox
	     * @param event
	     */

	    @FXML
	    void teachingUnit(ActionEvent event) {
			flag = 1;
			teachingUnit = TeachingUnit.getValue();
	    }

	   	    
 }