package Secretary;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.Timer;

import Login.LoginController;
import Entity.Class;
import Entity.Semester;
import Entity.User;
import application.QueryController;
import javafx.application.Platform;
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
import javafx.scene.control.DialogPane;
/**
 * 
 * This controller handles the action of exceptional registration of a student to course. 
 */

public class InsertStudentToCourseController extends QueryController implements Initializable{
	
			private Object nextController=null;
			@FXML
		    private Button logout;

		    @FXML
		    private Text userID;

		    @FXML
		    private Button back;

		    @FXML
		    private Text StudentErr;

		    @FXML
		    private ComboBox<?> StudentCombo;

		    @FXML
		    private DialogPane Dialog;

		    @FXML
		    private Button Save;

		    @FXML
		    private ComboBox<?> CourseCombo;

		    @FXML
		    private Text LabText;

		    @FXML
		    private Text CoursesErr;

		    @FXML
		    private Text SuccessMessage;

		    private ArrayList<String> StudentsList;
		    private ArrayList<String> CoursesList;
		    private Semester sem;
		    private String StudentID;
		    private String Course;
		    private String CourseID;
		    private String Stud;
		    private ArrayList<ArrayList<String>> CheckMessage;
		//------------------------------------------------//
		    public InsertStudentToCourseController(String controllerID)
			{
					super(controllerID);
			} 
		//------------------------------------------------// 
			 /**
		  	 * 
		  	 * Initialize function, shows the logged in user, and initialize the courses combobox that open in this current semester.
		  	 * In addition, initialize the students combobox.
		  	 * @param arg0
		  	 * @param arg1
		  	 */
		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {//this method perform when this controller scene is showing up.
			User user = User.getCurrentLoggedIn();
			userID.setText(user.GetUserName());
			sem=Semester.getCurrentSemester();
			//----------------------------------//
	    	ArrayList<ArrayList<String>> Students= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM student");
	    	if(Students==null)
	    	{
	    		 CoursesErr.setVisible(true);
				 CoursesErr.setText("There is no students in the DB.");
				 Timer time = new Timer(1500, new java.awt.event.ActionListener() {
		                @Override
		                public void actionPerformed(java.awt.event.ActionEvent e) {
		                	try{
		                		CoursesErr.setText("");
		                	}catch(java.lang.NullPointerException e1){
		                		
		                	}
		                }
		            });
		            time.setRepeats(false);
		            time.start();
	    	}
	    	else
	    	{	
	    		StudentsList=new ArrayList<String>();
	    		for(int i=0;i<Students.size();i++)
		    	{
			    	ArrayList<ArrayList<String>> StudentsName= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM user WHERE userID='" +Students.get(i).get(0)+"'");
		    		StudentsList.add("( "+Students.get(i).get(0)+" ) - "+StudentsName.get(0).get(1));
		    	}
		    	ObservableList L= FXCollections.observableList(StudentsList);
		    	StudentCombo.setItems(L);
		    	//-----------------------------------------------------------------------------//
		    	ArrayList<ArrayList<String>> Courses= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM coursesinsemester WHERE Sem='"+sem.getYear()+':'+sem.getType()+"'");
		    	if(Courses==null)
		    	{
		    		 CoursesErr.setVisible(true);
					 CoursesErr.setText("There is no courses in this semester: "+sem.getYear()+':'+sem.getType());
					 Timer time = new Timer(1500, new java.awt.event.ActionListener() {
			                @Override
			                public void actionPerformed(java.awt.event.ActionEvent e) {
			                	try{
			                		CoursesErr.setText("");
			                	}catch(java.lang.NullPointerException e1){
			                		
			                	}
			                }
			            });
			            time.setRepeats(false);
			            time.start();
		    	}
		    	else
		    	{
		    		CoursesList=new ArrayList<String>();
		    		for(int i=0;i<Courses.size();i++)
		    		{
				    	ArrayList<ArrayList<String>> CourseName= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM courses WHERE idcourses='" +Courses.get(i).get(0)+"'");
		    			CoursesList.add("( "+Courses.get(i).get(0)+" ) - "+ CourseName.get(0).get(1));
		    			
		    		}
		    		ObservableList CourseL= FXCollections.observableList(CoursesList);
		    		CourseCombo.setItems(CourseL);
		    	}
	    	}
		}
		//------------------------------------------------// 
		  /**
			 * 
			 * The function TurningBack return's to the student change window.
			 * conditions first.
			 * @param event
			 */
		 @FXML
		 void TurningBack(ActionEvent event)
		 {
		     this.nextController = new StudentChangeController("StudentChangeController");
		     this.Back("/Secretary/StudentChange.fxml",nextController, event);
		 } 
		  /**
				 * 
				 * The function SaveHandler register the student to the chosen course.
				 * @param event
				 */
		 @FXML
		 void SaveHandler(ActionEvent event) 
		 {
			 
			 transfferQueryToServer("INSERT INTO studentincourse (identityStudent,identityCourse,Grade,IdenClas) VALUES ('" + StudentID + "','" + CourseID + "','" +"0"+"','"+CheckMessage.get(0).get(7) +"')");
		   	 transfferQueryToServer("DELETE FROM messagestudent WHERE num='" + CheckMessage.get(0).get(0)+"'");
		     SuccessMessage.setVisible(true);
		     SuccessMessage.setText("The student:  "+Stud+"  inserted successfully to the course:  "+CourseID);
		     Timer time = new Timer(1500, new java.awt.event.ActionListener() {
	                @Override
	                public void actionPerformed(java.awt.event.ActionEvent e) {
	                	try{
	                		SuccessMessage.setText("");
	                	}catch(java.lang.NullPointerException e1){
	                		
	                	}
	                }
	            });
	            time.setRepeats(false);
	            time.start();
	            StudentCombo.getSelectionModel().clearSelection();
	            CourseCombo.getSelectionModel().clearSelection();
	            Dialog.setVisible(false);
	            Save.setVisible(false);
	            CourseCombo.setVisible(false);
	            LabText.setVisible(false);
	            
		 }
		 //------------------------------------------------// 
		  /**
			 * 
			 * The function open to the user the course combobox and the next choosing steps, 
			 * after choosing the student.
			 * conditions first.
			 * @param event
			 */
		 @FXML
		 void StudentChosen(ActionEvent event)
		 {
			 Stud=(String) StudentCombo.getValue();

			 
			 if(Stud!=null)
			 {
				 	StudentID = Stud .substring(Stud .indexOf("(") + 1, Stud .indexOf(")"));
				 	Dialog.setVisible(true);
				 	CourseCombo.setVisible(true);
				 	LabText.setVisible(true);
			 }
			 
		 }
		 //------------------------------------------------// 
		  /**
				 * 
				 * The function ChooseCourseHandler:
				 * checks if the school director approved the registration by checking the message's, and insert's the student to the course.
				 * conditions first. 
				 * @param event
				 */
		 @FXML
		 void ChooseCourseHandler(ActionEvent event)
		 {
			 Course=(String) CourseCombo.getValue();
			 
			 
			 if(Course!=null)
			 {
				 	CourseID=Course.substring(Course .indexOf("(") + 1, Course .indexOf(")"));
				 	ArrayList<ArrayList<String>> CheckA= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM studentincourse WHERE identityStudent='"+StudentID+"' AND identityCourse='"+CourseID+"'");
				 	if(CheckA!=null)
				 	{
				 		 CoursesErr.setVisible(true);
				 		 CoursesErr.setText("The student is already assigned to this course.");
						 Timer time = new Timer(3000, new java.awt.event.ActionListener() {
				                @Override
				                public void actionPerformed(java.awt.event.ActionEvent e) {
				                	try{
				                		CoursesErr.setText("");
				                	}catch(java.lang.NullPointerException e1){
				                		
				                	}
				                }
				            });
				            time.setRepeats(false);
				            time.start();
				            Dialog.setVisible(false);
							CourseCombo.setVisible(false);
							LabText.setVisible(false);
							Platform.runLater(() -> StudentCombo.getSelectionModel().clearSelection());
							Platform.runLater(() -> CourseCombo.getSelectionModel().clearSelection());
				 	}
				 	else
				 	{
				 		CheckMessage= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM messagestudent WHERE StuIdentity='" + StudentID + "' AND CouID='" +CourseID+"' AND type='"+"Student Insert"+"'");
				    	if(CheckMessage==null)
				    	{
				    		 CoursesErr.setVisible(true);
							 CoursesErr.setText("There is no such request.");
							 Timer time = new Timer(1500, new java.awt.event.ActionListener() {
					                @Override
					                public void actionPerformed(java.awt.event.ActionEvent e) {
					                	try{
					                		CoursesErr.setText("");
					                	}catch(java.lang.NullPointerException e1){
					                		
					                	}
					                }
					            });
					            time.setRepeats(false);
					            time.start();
					            Platform.runLater(() -> StudentCombo.getSelectionModel().clearSelection());
					            Platform.runLater(() -> CourseCombo.getSelectionModel().clearSelection());
					            Dialog.setVisible(false);
					            Save.setVisible(false);
					            CourseCombo.setVisible(false);
					            LabText.setVisible(false);
				    	}
				    	else
				    	{
				    		if(CheckMessage.get(0).get(5).equals("YES"))
				    		{
				    			Save.setVisible(true);
				    		}
				    		else
				    		{
				    			 CoursesErr.setVisible(true);
								 CoursesErr.setText("The school director didn't approved the request.");
								 Timer time = new Timer(3000, new java.awt.event.ActionListener() {
						                @Override
						                public void actionPerformed(java.awt.event.ActionEvent e) {
						                	try{
						                		CoursesErr.setText("");
						                	}catch(java.lang.NullPointerException e1){
						                		
						                	}
						                }
						            });
						            time.setRepeats(false);
						            time.start();
						            Platform.runLater(() ->StudentCombo.getSelectionModel().clearSelection());
						            Platform.runLater(() ->CourseCombo.getSelectionModel().clearSelection());
						            Dialog.setVisible(false);
						            Save.setVisible(false);
						            CourseCombo.setVisible(false);
						            LabText.setVisible(false);
				    		}
				    	}	
				 		
				 		
				 	}

				 	
				 	
				 	
			    
			 } 
		 }
		//------------------------------------------------//  
}




