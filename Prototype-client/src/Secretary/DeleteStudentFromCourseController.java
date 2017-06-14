package Secretary;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Entity.Class;
import Entity.Course;
import Entity.Semester;
import Entity.User;
import application.QueryController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
	
public class DeleteStudentFromCourseController extends QueryController implements Initializable {
	
	@FXML
	private Button logout;

	@FXML
	private Text userID;

	@FXML
	private Button back;

	@FXML
	private ComboBox<?> ComboCourse;
	
	@FXML
	private Text ErrCourseMessage;

	@FXML
	private ComboBox<?> StudentCombo;

	@FXML
	private Text StudentErr;

	@FXML
	private Text SuccessMessage;
	
	@FXML
	private DialogPane dialogPane;
	
	@FXML
	private Label StudentsLable;

	@FXML
	private Button Save;
	
	Object nextController=null;
	//--------------------------------------------------------//
	public DeleteStudentFromCourseController(String controllerID)
	{
			super(controllerID);
	} 
	//--------------------------------------------------------//
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		User user = User.getCurrentLoggedIn();
		userID.setText(user.GetUserName());
		//--------------------------------------------------//
   	  	ArrayList<ArrayList<String>> CurrentSemester= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM semester WHERE status='true'");
   	  	if(CurrentSemester!=null)
   	  	{
   	  		ArrayList<String> listCourses = new ArrayList<String>();
   	  		//Get Current Semester:
   	  		ArrayList<ArrayList<String>> CoursesInSemester= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM coursesinsemester WHERE Sem='" +CurrentSemester.get(0).get(0) +"'");
   	  		if(CoursesInSemester!=null)
   	  		{
   	  			for(int i=0;i<CoursesInSemester.size();i++)
   	  			{
   	  				ArrayList<ArrayList<String>> CourseWithStudents= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM studentincourse WHERE identityCourse='" + CoursesInSemester.get(i).get(0)+"'");
   	  				//Insert to the combobox only courses with students:
   	  				if(CourseWithStudents!=null)
   	  				{
   	  					ArrayList<ArrayList<String>> CourseName= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT courseName FROM courses WHERE idcourses='" + CoursesInSemester.get(i).get(0)+"'");
   	  					listCourses.add("("+CoursesInSemester.get(i).get(0)+")"+CourseName.get(0).get(0));
   	  				}
   	  			}
     	  		ObservableList L= FXCollections.observableList(listCourses);
     	  		ComboCourse.setItems(L);	 	  			
   	  		}
    	  	//----------------------------------------------------------------------------------//  
   	  		else
   	  		{
   	   	    	ErrCourseMessage.setVisible(true);
   	   	  		ErrCourseMessage.setText("There is no courses in this semester.");
   	  		}

   	  	}
   	  	else
   	  	{
   	    	ErrCourseMessage.setVisible(true);
   	  		ErrCourseMessage.setText("There is no semester's in the DB.");
   	  	}
	}
	//--------------------------------------------------------//
    @FXML
	void TurningBack(ActionEvent event)
	{
		this.nextController = new OpenNewSemesterController("OpenNewID");
		this.Back("/Secretary/OpenNewSemesterWindow.fxml",nextController,event);
	}
    //--------------------------------------------------------//
    @FXML
    void ChooseCourseHandler(ActionEvent event)
    {
    		   String ChosenCourse =  (String) ComboCourse.getValue();
    		   String RequiredStringCourse = ChosenCourse.substring(ChosenCourse.indexOf("(") + 1, ChosenCourse.indexOf(")"));
	   	  	  
    		   ArrayList<ArrayList<String>> StudentsInCourse= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT identityStudent FROM studentincourse WHERE identityCourse='" +RequiredStringCourse+"'");
	   	  	   ArrayList<String> listStudents = new ArrayList<String>();
	   	  	   System.out.println(listStudents.size());
	   	  		      for(int i=0;i<listStudents.size();i++)
	   	  		      {
	   	  		    	  ArrayList<ArrayList<String>> StudentsName= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT userName FROM user WHERE userID='" +StudentsInCourse.get(i).get(0)+"'");
	   	  		    	  System.out.println(StudentsName.get(0).get(0));
	   	  		    	  listStudents.add("("+StudentsInCourse.get(i).get(0)+")- "+StudentsName.get(0).get(0));
	   	  		      }
	   	  		      ObservableList StudentL= FXCollections.observableList( listStudents);
	   	  		      StudentCombo.setItems(StudentL);
	   	  		      //---------------------------------//
	   	  		      //Set visible the next combobox:
    		   
	   	  		      dialogPane.setVisible(true);
	   	  		      StudentsLable.setVisible(true);
	   	  		      StudentCombo.setVisible(true);
	   	  		      Save.setVisible(true);
    		   
	   	  		      SuccessMessage.setVisible(true);
	   	  		      //---------------------------------//
	   	  	   
    }
    //--------------------------------------------------------//
}


/*
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
    	}//Else
    }Save*/
