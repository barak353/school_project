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
 * This controller handles the System Manager actions and is responsible for presenting the System Add Pre-Course screen
 *
 */

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
    private ComboBox<String> preCourses;
    @FXML
    private Button back;
    @FXML
    private Button addButton;
    @FXML
    private Text ID;
    @FXML
    private Text errorID;
    @FXML
    private Text userID;
    @FXML
    private Button finishButton;
    @FXML
    private Text addpreCourses;
    private String lastCourse;
    private String PreCourses;
    private int flag=0;
	
	//-----------------------------------------------------------//
    
    /**
     * function that return to the last screen
     * @param event
     */
	
	@FXML
	void TurningBack(ActionEvent event)
	{
		this.nextController = new SystemManagerAddCourseController("SystemManagerAddCourseController");
		this.Back("/SystemManager/SystemManagerAddCourseWindow.fxml",nextController, event);
	}

    
	//------------------------------------------// 

    /**
     *  After pressing the addPrecourse button, this function check if the user choose from the ComboBox pre-Courses,
     *  if yes, the system Inserts data into the database and sends the lastCourse to the next Controller
     *  the system Continue to the next screen -  add Pre course
     * @param event
     */   
    @FXML
    void addPrecourse(ActionEvent event) 
    {    
    	errorID.setText("");
    	addpreCourses.setText("");
    	
    	
    	if(flag==0){ //checks if not choose Pre-course.
    		errorID.setText("ERROR:\nPlease choose Pre-course.");
    		addpreCourses.setText("");
    	}
    	
    	Object obj = null;
    	String idPreCourses = PreCourses.substring(PreCourses.indexOf("(") + 1, PreCourses.indexOf(")"));//get the idteachingUnit that is inside a ( ).
    	insertPreCourse(idPreCourses);
	    //PreCourses.clear();
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

	public boolean insertPreCourse(String idPreCourses) {
		Object obj;
		boolean isInserted = true ;
		if(flag==1){ 
	 		obj =  transfferQueryToServer("INSERT INTO precourse(precourse, currCourse) VALUES ('"+idPreCourses+"',"+lastCourse+")");
	    	if(obj == null){ //check if pre-course was successfully added in DB
				if(isNotTest){
		    		addpreCourses.setText("The pre-course was successfully added in DB.");
					errorID.setText("");
				}
	    	}
	    	int r=0;
	    	if(obj != null){  //check if Data exists in DB
	    		r = (int)obj;
	    		if(r == -1){//if we encounter a error let's check which error it was.
					if(isNotTest){
			        	errorID.setText("ERROR: this pre-course ID is already in DB."); 
					}
						isInserted = false;
	    		}
	    	}
    	}
		return 	isInserted;
	} 
    
	//------------------------------------------// 
    /**
     *  After pressing the finish button,
     *  the system Continue to the next screen -  add course
     * @param event
     */   
    @FXML
    void finish(ActionEvent event) {
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
    
  //------------------------------------------// 
    
    /**
     * this function initialize the screen whit the name of the user, id last Course and the ComboBox pre-courses list.
     */
   
  	@Override
  	public void initialize(URL arg0, ResourceBundle arg1) {//this method perform when this controller scene is showing up.
  		User user = User.getCurrentLoggedIn();
  		userID.setText(user.GetUserName());
  		ID.setText(lastCourse);
	    ArrayList<ArrayList<String>> res = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM courses WHERE idcourses!='" + lastCourse +"'");
        if(res==null){
        	errorID.setText("NO found courses");//show error message.
        }
        else{
		    ArrayList<String> precourses = new ArrayList<String>();
	    	for(ArrayList<String> row:res){
	    		precourses.add(row.get(1)+" ("+row.get(0)+")");
	    	}
		    ObservableList obList= FXCollections.observableList(precourses);
		    preCourses.setItems(obList);
        }
  	}
  
  	
//------------------------------------------//  	
  
    /**
     * this function check if the user choose pre-Courses from the ComboBox
     * @param event
     */

    @FXML
    public void preCourses(ActionEvent event) {
		flag = 1;
		if(isNotTest){
			PreCourses = preCourses.getValue();
		}
    }
	
	//------------------------------------------// 
	public void setCourseID(String lastCourse) {
		// TODO Auto-generated method stub
		this.lastCourse = lastCourse;
	}


}
