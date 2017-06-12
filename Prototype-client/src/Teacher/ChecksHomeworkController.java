package Teacher;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

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
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.ComboBox;



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
    private ComboBox<String> TasktL;

    @FXML
    private Button back;

    @FXML
    private ComboBox<String> CourseList;

    @FXML
    private Text userID;

    public void initialize(URL arg0, ResourceBundle arg1) {//this method perform when this controller scene is showing up.
    	User user = User.getCurrentLoggedIn();
    	userID.setText(user.GetUserName());
        String teacherID = user.GetID();
    	
    	ArrayList<ArrayList<String>> res = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT coID FROM teacherinclassincourse WHERE Tidentity="+teacherID);
    	ArrayList<String> courseNameList = new ArrayList<String>();
    	ArrayList<ArrayList<String>> res2;
    	
    	for(ArrayList<String> row:res){
        	res2 = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT courseName,idcourses FROM courses WHERE idcourses="+row.get(0));
    		courseNameList.add(res2.get(0).get(0)+"("+res2.get(0).get(1)+")");
    	}
    	System.out.println("courseNameList: "+courseNameList);
    	
	    ObservableList obList= FXCollections.observableList(courseNameList);
	    CourseList.setItems(obList);
    }
    
    @FXML
    void chooseCourse(ActionEvent event) {
    	String chooseCourse = CourseList.getValue();
    	String idcourses = chooseCourse.substring(chooseCourse.indexOf("(") + 1, chooseCourse.indexOf(")"));//get the idcourses that is inside a ( ).
    	ArrayList<ArrayList<String>> res = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT idTASK FROM task WHERE idcorse="+idcourses);     
    	System.out.println("choosecours,idcourse: "+ chooseCourse +idcourses);

    	ArrayList<ArrayList<String>> res2;
    	ArrayList<String> TaskNameList = new ArrayList<String>();
       	for(ArrayList<String> row:res){
        	res2 = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT TaskName,idTASK FROM task WHERE idTASK="+row.get(0));
        	TaskNameList.add(res2.get(0).get(0)+"("+res2.get(0).get(1)+")");
    	}
       	System.out.println("task: "+ TaskNameList);
       	ObservableList obList= FXCollections.observableList(TaskNameList);
       	TasktL.setItems(obList);
   
   }
    
    @FXML
    void Continue(ActionEvent event) {
    	try {
    	  		    		
    			String chooseTask = TasktL.getValue();
        		String idtask = chooseTask.substring(chooseTask.indexOf("(") + 1, chooseTask.indexOf(")"));//get the idtask that is inside a ( ).
        		FXMLLoader loader = new FXMLLoader(getClass().getResource("/Teacher/UploadTask.fxml"));
        		TaskOfStudentController controller = new TaskOfStudentController("TaskOfStudentController");
        		String chooseCourse = CourseList.getValue();
        		String idcourses = chooseCourse.substring(chooseCourse.indexOf("(") + 1, chooseCourse.indexOf(")"));//get the idcourses that is inside a ( ).
        		controller.setCourseN(chooseCourse);
        		controller.setCourseID(idcourses);
        		controller.setTaskID(idtask);
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
    
    @FXML
    void TurningBack(ActionEvent event) {
    	this.nextController = new TeacherMainController("TeacherMainController");
    	this.Back("/Teacher/TeacherMain.fxml",nextController, event);
    }
    

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