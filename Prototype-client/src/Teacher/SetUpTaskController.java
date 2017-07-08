package Teacher;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.scene.control.ComboBox;
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

/**
 * 
 * this controller handles the action: set up task for course by teacher
 * first, the teacher choose course ,then Continue to another screen - upload task
 * 
 */


public class SetUpTaskController extends QueryController implements Initializable {

	Object nextController=null;	
	//-----------------------------------------------------------//
	
	public SetUpTaskController(String controllerID)
	{
			super(controllerID);
	}

	//-----------------------------------------------------------//
	
    @FXML
    private Button logout;

    @FXML
    private Button back;

    @FXML
    private Button Continue;

    @FXML
    private Text userID;
    
    @FXML
	 private Text textMSG;
    
    @FXML
    private ComboBox<String> CourseList;
    private int iscourseChoosed = 0;  
   
  //-----------------------------------------------------------//

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
     *  After pressing the Continue button, this function check if the user choose from the combobox course,
     *  if yes, the system Continue to the next screen -  upload task
     * @param event
     */
    
    @FXML
    void Continue(ActionEvent event) {
    	try {
    		if(iscourseChoosed==0){
    			textMSG.setText("you didn't choose course");
    			textMSG.setVisible(true);
    			return;
    			
    		}    		
    			String chooseCourse = CourseList.getValue();
        		String idcourses = chooseCourse.substring(chooseCourse.indexOf("(") + 1, chooseCourse.indexOf(")"));//get the idcourses that is inside a ( ).
        		FXMLLoader loader = new FXMLLoader(getClass().getResource("/Teacher/UploadTask.fxml"));
        		UploadTaskController controller = new UploadTaskController("UploadTaskController");
        		controller.setCourseN(chooseCourse);
        		controller.setCourseID(idcourses);
		        loader.setController(controller);
		        Pane login_screen_parent = loader.load();
				Scene login_screen_scene=new Scene(login_screen_parent);
				Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();//the scene that the event came from.
				app_stage.hide();
				app_stage.setScene(login_screen_scene);
				app_stage.show(); 
				
				} catch (IOException e) {
					System.err.println("Missing UploadTask.fxml file");
					e.printStackTrace();
				}
    }
    
    
	public boolean isCourseExist(String teacherID, String semeID) {
    	ArrayList<ArrayList<String>> res = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT DISTINCT coID FROM teacherinclassincourse WHERE Tidentity="+teacherID + " AND SemesId='" + semeID + "'");
    	if( res == null )return false;
    	else return true;
	}
    
    /**
     * this function initialize the screen white the name of the user, and the combobox  of course
     */
    
    public void initialize(URL arg0, ResourceBundle arg1) {//this method perform when this controller scene is showing up.
    	User user = User.getCurrentLoggedIn();
    	userID.setText(user.GetUserName());
        
    	String teacherID = user.GetID();
    	String semeID = Semester.getCurrentSemester().getYear()+":"+Semester.getCurrentSemester().getType();
    	ArrayList<ArrayList<String>> res = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT DISTINCT coID FROM teacherinclassincourse WHERE Tidentity="+teacherID + " AND SemesId='" + semeID + "'");
    	ArrayList<String> courseNameList = new ArrayList<String>();
    	ArrayList<ArrayList<String>> res2;
    	if(isCourseExist(teacherID,semeID) == false) return;
    	for(ArrayList<String> row:res){
        	res2 = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT courseName,idcourses FROM courses WHERE idcourses="+row.get(0));
    		courseNameList.add(res2.get(0).get(0)+"("+res2.get(0).get(1)+")");
    	}
    	System.out.println("courseNameList: "+courseNameList);
    	
	    ObservableList<String> obList= FXCollections.observableList(courseNameList);;
	    CourseList.setItems(obList);
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
    
    /**
     * this function check if the user choose course from the combobox
     * @param event
     */

    @FXML
    void chooseCourse(ActionEvent event) {
    	iscourseChoosed = 1;
    }
    

}



