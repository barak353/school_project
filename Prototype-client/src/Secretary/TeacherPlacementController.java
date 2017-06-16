package Secretary;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Text;
import Login.LoginController;
import Entity.User;
import application.QueryController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class TeacherPlacementController extends QueryController  implements Initializable{
	
    @FXML
    private Button logout;

    @FXML
    private Text userID;

    @FXML
    private Button back;

    @FXML
    private Button SaveID;

    @FXML
    private Text ErrText;

    @FXML
    private Text finishmessage;

    @FXML
    private ComboBox<?> CourseCombo;

    @FXML
    private ComboBox<?> ClassCombo;

    @FXML
    private ComboBox<?> TeacherCombo;
    Object nextController=null;
	
    //------------------------------------------------//
	public TeacherPlacementController (String controllerID)
	{
	       super(controllerID);
	} 
	//------------------------------------------------// 
    @FXML
    void CourseHandler(ActionEvent event)
    {

    }
    //------------------------------------------------// 
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
		
	}
	
	@FXML
	public void SaveButtonHandler(ActionEvent event)
	{
	/*	String TeacherID;
		String Class;
		String Course;
		boolean flag=true;
		boolean NextFlag=true;
		int hoursteacher;
		int hoursCourse;
		String res;
		int res2;
		//----------------------------------------//
		 TeacherID=TeacherField.getText();
		 Class=ClassField.getText();
		 Course=CourseField.getText();
		//----------------------------------------//
		 if (TeacherID.equals("")==true || Class.equals("")==true || Course.equals("")==true)
		 {
			 text.setText("Please fill all the fields");
		 }
		 else
		 {
			 ArrayList<ArrayList<String>> Teacher= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM teacher WHERE teacherid='" + TeacherID +"'");
			 if (Teacher==null)
			 {
				 text.setText("The teacher is not exists");
				 flag=false;
			 }
		     ArrayList<ArrayList<String>> ChosenCourse= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM courses WHERE idcourses='" + Course +"'");
		     if (ChosenCourse==null)
		     {
		    	 text.setText("The course is not exists");
				 flag=false;
		     }
			 ArrayList<ArrayList<String>> ChosenClass= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM class WHERE ClassID='" +  Class +"'");
			 if (ChosenClass==null)
			 {
		    	 text.setText("The class is not exists");
				 flag=false;
			 }
			 if (flag==true)
			 {
				 ArrayList<ArrayList<String>> CheckClassInCourse= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM teacherinclassincourse WHERE clasID='" +  Class + "' AND coID='" + Course +"'");
				 if (CheckClassInCourse==null)
				 {
					 text.setText("The class is not learning this course");	
					 NextFlag=false;
				 }
				 else if (CheckClassInCourse.get(0).get(3).equals(TeacherID))
				 {
					 text.setText("The teacher already teching this class");	
					 NextFlag=false;
				 }
				 else
				 {
					 ArrayList<ArrayList<String>> CheckTeachUnit= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM teacherinteachunit WHERE idenTeach='" +  TeacherID + "' AND teachUnitNum='" + ChosenCourse.get(0).get(2) +"'");
					 if (CheckTeachUnit==null)
					 {
						 text.setText("The teacher is not teaching this course.\nPlease choose a teacher from the unit:"+ChosenCourse.get(0).get(2)); 
					 }
					 else
					 {
						 
					     hoursteacher=Integer.parseInt(Teacher.get(0).get(1));
	 	    	         hoursCourse=Integer.parseInt(ChosenCourse.get(0).get(3));
	 	    	         if (hoursteacher<hoursCourse)
	 	    		     {
	 	    	        	 text.setText("The Teacher exceed her teaching hours, can't make the teacher change.");
	 	    		     }
	 	    	         else
	 	    	         {
	 	    	        	res2=hoursteacher-hoursCourse;
	    			    	res=""+res2; 
	    			    	transfferQueryToServer("UPDATE teacher SET MaxHour="+res+" WHERE teacherid="+ Teacher.get(0).get(0)); 
	    			    	transfferQueryToServer("UPDATE teacherinclassincourse SET Tidentity="+Teacher.get(0).get(0)+" WHERE clasID='"+ CheckClassInCourse.get(0).get(0) + "' AND coID=" +ChosenCourse.get(0).get(0)); 
	    			    	finishmessage.setText("Teacher placement change succeeded");
	    			    	text.setText("");
	 	    	         }
					 }
				 }
			 }	 
		 }//else*/	 
	 }//Save	
	}//Class














