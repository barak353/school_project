package Secretary;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.Timer;

import Entity.Semester;
import Entity.Teacher;
import Entity.User;
import Entity.Course;
import application.QueryController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class StudentChangeController extends QueryController implements Initializable{

    @FXML
    private Text userID;
    @FXML
    private Button logout;
    @FXML
    private Button back;

    @FXML
    private Button Save;

    @FXML
    private TextField StudentIDField;

    @FXML
    private TextField CourseField;

    @FXML
    private ComboBox<?> TypeChoose;

    @FXML
    private TextArea PreMissing;
    @FXML
    private Text error;
    @FXML
    private Text finish;
    private String StudentID;
    private String Course;
    private String Type;
    private boolean flag=true;

	Object nextController=null;
	
	public StudentChangeController(String controllerID)
	{
			super(controllerID);
	} 
    //--------------------------------------------------//
    @FXML
    void SaveButtonHandler(ActionEvent event) 
    {
    	StudentID=StudentIDField.getText();
    	Course=CourseField.getText();
    	Type=(String) TypeChoose.getValue();
    	
    	//----------------------------------//
    	if(StudentID.equals("")==true || Course.equals("")==true || Type==null)
    	{
    		error.setText("Please fill all the fields.");
    	}
    	else
    	{
    		 ArrayList<ArrayList<String>> Student= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM student WHERE StudentID='" + StudentID +"'");
			 if (Student==null)
			 {
				 error.setText("The student is not exists");
				 flag=false;
			 }
		     ArrayList<ArrayList<String>> ChosenCourse= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM courses WHERE idcourses='" + Course +"'");
		     if (ChosenCourse==null)
		     {
		    	 error.setText("The course is not exists");
				 flag=false;
		     }
			 if (flag==true)
			 {
				 if (Type.equals("Insert"))
				 {
					 
					 
					 
				 }
				 else if(Type.equals("Delete"))
				 {
		    		 ArrayList<ArrayList<String>> StudentCourseCheck= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM studentincourse WHERE identityStudent='" + StudentID +"' AND identityCourse='" + Course+"'");
		    		 if (StudentCourseCheck==null)
		    		 {
		    			 error.setText("The student not assigned to the course: "+ChosenCourse.get(0).get(1));
		    		 }
		    		 else
		    		 {
	    			    	transfferQueryToServer("INSERT INTO studentinprecourse (childID,pCourseID,FinalGrade) VALUES ('" + StudentID + "','" + Course+ "','" +StudentCourseCheck.get(0).get(2)+"')");
	   		    		    transfferQueryToServer("DELETE FROM studentincourse WHERE identityStudent="+StudentID+" and identityCourse="+Course+"");
	   		    		 

	   		    		    error.setText("");
	    			    	finish.setText("The student was deleted successfully from the course");
		    		 }
					 
				 }
    		
			 }
    	}//Else*/
    }//Save
    
    //-------------------------------------------------------------------------------------------// 
	@FXML
	void TurningBack(ActionEvent event)
	{
		this.nextController = new SecretaryMainController("SecretaryMainController");
		this.Back("/Secretary/SecretaryMainWindow.fxml",nextController, event);
	} 
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {//this method perform when this controller scene is showing up.
		User user = User.getCurrentLoggedIn();
		userID.setText(user.GetUserName());
		ArrayList<String> list = new ArrayList<String>();
		list.add("Insert");
		list.add("Delete");
		ObservableList comlist= FXCollections.observableList(list);
		TypeChoose.setItems(comlist);
		TypeChoose.setVisible(true);
	}
}




