package Teacher;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Login.LoginController;
import Entity.Semester;
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
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.ComboBox;

/**
 * 
 * this controller handles the action: checks home work of student by teacher
 * first, the teacher choose course ,then task and Continue to another screen - task of student
 * 
 */

public class ChecksHomeworkController extends QueryController implements Initializable {

	Object nextController=null;	
	
	//-----------------------------------------------------------//
	public ChecksHomeworkController(String controllerID)
	{
			super(controllerID);
	}
	
    @FXML
    private Button logout;

    @FXML
    private Button Continue;

    @FXML
    private ComboBox<String> TaskL;

    @FXML
    private Button back;

    @FXML
    private ComboBox<String> CourseList;

    @FXML
    private Text userID;
    private ObservableList<String> obList;
    private String teacherID;
    
    private boolean isCourseChoosed = false;
    private boolean isTaskChoosed = false;

    @FXML
    private Text ErrorMSG;
    
    /**
     * this function initialize the screen white the name of the user, and the combobox  of course
     */
    
    public void initialize(URL arg0, ResourceBundle arg1) {//this method perform when this controller scene is showing up.
    	User user = User.getCurrentLoggedIn();
    	userID.setText(user.GetUserName());
         teacherID = user.GetID();
	    ObservableList obList= FXCollections.observableList(new ArrayList());;
	    CourseList.setItems(obList);
    	ArrayList<ArrayList<String>> res = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT DISTINCT coID FROM teacherinclassincourse WHERE Tidentity="+teacherID);
    	System.out.println("res: "+res);
    	ArrayList<String> courseNameList = new ArrayList<String>();
    	ArrayList<ArrayList<String>> res2;
    	if(res == null){
    		obList= FXCollections.observableList(new ArrayList());
    		CourseList.setItems(obList);
         }else{
	    	for(ArrayList<String> row:res){
	        	res2 = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT courseName,idcourses FROM courses WHERE idcourses="+row.get(0));
	    		courseNameList.add(res2.get(0).get(0)+"("+res2.get(0).get(1)+")");
	    	}
	    	System.out.println("courseNameList: "+courseNameList);
	    	
		    obList= FXCollections.observableList(courseNameList);
		    CourseList.setItems(obList);
    	}
    }
    
    /**
     * this function check if the user choose task from the combobox
     * @param event
     */

    @FXML
    void ChooseTask(ActionEvent event) {
    	ErrorMSG.setText("");
       	isTaskChoosed = true;
    }
    /**
     *  After choose course in the combobox, this function initialize the combobox of task in this course
     * @param event
     */
    
    @FXML
    void chooseCourse(ActionEvent event) {
    	ErrorMSG.setText("");
    	String chooseCourse = CourseList.getValue();
    	String idcourses = chooseCourse.substring(chooseCourse.indexOf("(") + 1, chooseCourse.indexOf(")"));//get the idcourses that is inside a ( ).
    	Semester semester = Semester.getCurrentSemester();
    	String IDsem = Semester.getCurrentSemester().getYear()+":"+Semester.getCurrentSemester().getType();
    	ArrayList<ArrayList<String>> res = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT TaskName FROM task WHERE IDsem='"+IDsem+"' AND idcorse="+idcourses+
    			" AND Teach="+teacherID);     
    	if(res != null){
	    	//ArrayList<ArrayList<String>> res2;
	    	ArrayList<String> TaskNameList = new ArrayList<String>();
	       	for(ArrayList<String> row:res){
	        	//res2 = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT TaskName,idTASK FROM task WHERE idTASK="+row.get(0));
	        	TaskNameList.add(row.get(0));
	    	}
	       	System.out.println("task: "+ TaskNameList);
	       	obList= FXCollections.observableList(TaskNameList);
	       	System.out.println("obList: "+obList);
	       	TaskL.setItems(obList);
	       	isCourseChoosed = true;
	       	isTaskChoosed = false;
    	}else{
    		isCourseChoosed = false;
	       	isTaskChoosed = false;
	       	obList= FXCollections.observableList(new ArrayList());
	       	System.out.println("obList: "+obList);
	       	TaskL.setItems(obList);
	    	ErrorMSG.setText("This course dosen't have any tasks to show");

    		}
   }
    
    /**
     *  After pressing the Continue button, this function check if the use choose from the combobox course and task,
     *  if yes, the system Continue to the next screen - task of student
     * @param event
     */
    
    @FXML
    void Continue(ActionEvent event) {
    	try {
		    	ErrorMSG.setText("");
    	  		if(isCourseChoosed && isTaskChoosed)	{	
	    			String choosedTaskName = TaskL.getValue();//get TaskName.
	        		FXMLLoader loader = new FXMLLoader(getClass().getResource("/Teacher/TaskOfStudent.fxml"));
	        		TaskOfStudentController controller = new TaskOfStudentController("TaskOfStudentController");
	        		String chooseCourse = CourseList.getValue();
	        		String idcourses = chooseCourse.substring(chooseCourse.indexOf("(") + 1, chooseCourse.indexOf(")"));//get the idcourses that is inside a ( ).
	        		controller.setCourseN(chooseCourse);
	        		controller.setCourseID(idcourses);
	        		controller.setTaskName(choosedTaskName);
			        loader.setController(controller);
			        Pane login_screen_parent = loader.load();
					Scene login_screen_scene=new Scene(login_screen_parent);
					Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();//the scene that the event came from.
					app_stage.hide();
					app_stage.setScene(login_screen_scene);
					app_stage.show(); 
    	  			}else{
    	  		    	ErrorMSG.setText("Please choose course and task.");
    	  			}
		} catch (IOException e) {
			System.err.println("Missing TaskOfStudent.fxml file");
			e.printStackTrace();
		}
    }
    
    /**
     * function that return to the last screen
     * @param event
     */
    
   
    @FXML
    void TurningBack(ActionEvent event) {
    	this.nextController = new TeacherMainController("TeacherMainController");
    	this.Back("/Teacher/TeacherMain.fxml",nextController, event);
    }
    
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