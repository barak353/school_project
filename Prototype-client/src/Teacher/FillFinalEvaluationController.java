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
import javafx.scene.control.TextArea;


public class FillFinalEvaluationController extends QueryController implements Initializable {

	Object nextController=null;	
	//-----------------------------------------------------------//
	
	public FillFinalEvaluationController(String controllerID)
	{
			super(controllerID);
	}
	
    @FXML
    private Button logout;

    @FXML
    private ComboBox StudentList;

    @FXML
    private TextArea comments;

    @FXML
    private ComboBox TaskList;

    @FXML
    private TextField grade;

    @FXML
    private Button save;

    @FXML
    private Button back;

    @FXML
    private ComboBox<String> CourseList;

    @FXML
    private Text userID;
    

    @FXML
    private Text textMSG;
	
    @FXML
    void TurningBack(ActionEvent event) {
    	this.nextController = new TeacherMainController("TeacherMainController");
    	this.Back("/Teacher/TeacherMain.fxml",nextController, event);
    }
    
    @FXML
    void saveB(ActionEvent event) {
    	textMSG.setVisible(true);
    	String finalGrade = grade.getText();
    	int fnlGrade;
    	try{
    		fnlGrade = Integer.parseInt(finalGrade);
    	}catch(NumberFormatException e){
    		textMSG.setText("Final grade most contain only numbers.");
    		return;
    	}
    	if(fnlGrade < 100 && fnlGrade < 0){
    		textMSG.setText("Final grade most be between 100 to 0."); return;
    	}
    	
    	
    }
    public void initialize(URL arg0, ResourceBundle arg1) {//this method perform when this controller scene is showing up.
    	User user = User.getCurrentLoggedIn();
    	userID.setText(user.GetUserName());
    	String teacherID = user.GetID();
    	
    	ArrayList<ArrayList<String>> res = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT cI FROM teacherincourse WHERE TeacherID="+teacherID);
    	ArrayList<String> courseNameList = new ArrayList<String>();
    	ArrayList<ArrayList<String>> res2;
    	
    	for(ArrayList<String> row:res){
        	res2 = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT courseName,idcourses FROM courses WHERE idcourses="+row.get(0));
    		courseNameList.add(res2.get(0).get(0)+"("+res2.get(0).get(1)+")");
    	}
    	System.out.println("courseNameList: "+courseNameList);
    	
	    ObservableList obList= FXCollections.observableList(courseNameList);;
	    CourseList.setItems(obList);
    }
    
    @FXML
    void chooseCourse(ActionEvent event) {
    	String chooseCourse = CourseList.getValue();
    	String idcourses = chooseCourse.substring(chooseCourse.indexOf("(") + 1, chooseCourse.indexOf(")"));//get the idcourses that is inside a ( ).
    	ArrayList<ArrayList<String>> res = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT studentID FROM studentincourse WHERE idcourses="+idcourses);
    	ObservableList obList= FXCollections.observableList(res);;
    	StudentList.setItems(obList);
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