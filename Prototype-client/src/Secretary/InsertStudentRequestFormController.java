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
 * This controller handles the action of sending a message to school director about student registration to course.
 */
public class InsertStudentRequestFormController extends QueryController implements Initializable{
	
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
		    private Button Send;

		    @FXML
		    private ComboBox<?> CourseCombo;

		    @FXML
		    private Text LabText;

		    @FXML
		    private Text CoursesErr;

		    @FXML
		    private Text SuccessMessage;

		    @FXML
		    private ComboBox<?> ClassCombo;
		    private ArrayList<String> StudentsList;
		    private ArrayList<String> CoursesList;
		    private ArrayList<String> ClassList;
		    private Semester sem;
		    private String StudentID;
		    private String Course;
		    private String CourseID;
		    private String Stud;
		//------------------------------------------------//
		    public InsertStudentRequestFormController(String controllerID)
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
				 Timer time = new Timer(5000, new java.awt.event.ActionListener() {
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
		    		StudentsList.add("("+Students.get(i).get(0)+") - "+StudentsName.get(0).get(1));
		    	}
		    	ObservableList L= FXCollections.observableList(StudentsList);
		    	StudentCombo.setItems(L);
		    	//-----------------------------------------------------------------------------//
		    	ArrayList<ArrayList<String>> Courses= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM coursesinsemester WHERE Sem='"+sem.getYear()+':'+sem.getType()+"'");
		    	if(Courses==null)
		    	{
		    		 CoursesErr.setVisible(true);
					 CoursesErr.setText("There is no courses in this semester:  "+sem.getYear()+':'+sem.getType());
					 Timer time = new Timer(5000, new java.awt.event.ActionListener() {
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
		    			CoursesList.add("("+Courses.get(i).get(0)+") - "+ CourseName.get(0).get(1));
		    			
		    		}
		    		ObservableList CourseL= FXCollections.observableList(CoursesList);
		    		CourseCombo.setItems(CourseL);
		    	}
	    	}
		}
		//------------------------------------------------// 
		  /**
		 * 
		 * The function TurningBack return's to the form choose window.
		 * conditions first.
		 * @param event
		 */
		 @FXML
		 void TurningBack(ActionEvent event)
		 {
		     this.nextController = new AskRequestFormController("AskRequestFormController");
		     this.Back("/Secretary/WhatFormChoose.fxml",nextController, event);
		 } 
		  /**
			 * 
			 * The function SaveHandler checks if the message is already exist's in DB,
			 * and if not- insert's the message to the DB.
			 * send's the message to the school director.
			 * @param event
			 */

		 @FXML
		 void SaveHandler(ActionEvent event) 
		 {
			 
			 String ChosenClass=(String) ClassCombo.getValue();
			 if(ChosenClass!=null)
			 {
				ArrayList<ArrayList<String>> CheckMessage= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM messagestudent WHERE StuIdentity='"+StudentID+"'AND CouID='"+CourseID+"'AND type='"+"Student Insert"+"'");
				if(CheckMessage!=null)
				{
					 CoursesErr.setVisible(true);
					 CoursesErr.setText("You already requested to insert the student:  "+Stud+"  to the course:  "+Course);
					 Timer time = new Timer(5000, new java.awt.event.ActionListener() {
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
	 	    			Send.setVisible(false);
	 	    			CourseCombo.setVisible(false);
	 	    			LabText.setVisible(false);
	 	    			ClassCombo.setVisible(false);
	 	    			ClassCombo.getSelectionModel().clearSelection();
	 	    			CourseCombo.getSelectionModel().clearSelection();
	 	    			StudentCombo.getSelectionModel().clearSelection();
				}
				else
				{
					DateTimeFormatter dtf = DateTimeFormatter.ofPattern("DD/MM/yyyy");
	    		    LocalDateTime now = LocalDateTime.now();
	    		    String Date=""+now.getDayOfMonth()+"/"+now.getMonthValue()+"/"+now.getYear();
 	    			transfferQueryToServer("INSERT INTO messagestudent (StuIdentity,CouID,type,Mdate,Answer,Mess,Cla) VALUES ('" + StudentID + "','" + CourseID + "','" +"Student Insert"+"','"+ Date+"','"+"NULL"+"','"+"Hello,\nStudent Insert Please:\nStudent: "+Stud+"\nClass:"+ChosenClass+"\nCourse:"+Course+"','"+ChosenClass+"')");
 	    			SuccessMessage.setVisible(true);
 	    			SuccessMessage.setText("The message was sended successfully.");
 	    			Timer time = new Timer(5000, new java.awt.event.ActionListener() {
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
 	    			Dialog.setVisible(false);
 	    			Send.setVisible(false);
 	    			CourseCombo.setVisible(false);
 	    			LabText.setVisible(false);
 	    			ClassCombo.setVisible(false);
 	    			ClassCombo.getSelectionModel().clearSelection();
 	    			CourseCombo.getSelectionModel().clearSelection();
 	    			StudentCombo.getSelectionModel().clearSelection();
				} 
			 }
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
			 * Checks if there is some class that in the same age of the student, that assigned to the chosen course
			 * for assigning the student to that class in this course by exceptional registration.
			 * @param event
			 */
		 @FXML
		 void ChooseCourseHandler(ActionEvent event)
		 {
			 Course=(String) CourseCombo.getValue();
			 
			 
			if(Course!=null)
			{
				 	CourseID=Course.substring(Course .indexOf("(") + 1, Course .indexOf(")"));
				 	//Check if the student is already assigned to the course:
				 	ArrayList<ArrayList<String>> CheckA= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM studentincourse WHERE identityStudent='"+StudentID+"' AND identityCourse='"+CourseID+"'");
				 	if(CheckA!=null)
				 	{
				 		 CoursesErr.setVisible(true);
				 		 CoursesErr.setText("The student is already assigned to this course.");
						 Timer time = new Timer(5000, new java.awt.event.ActionListener() {
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
				 		//Take the age of the student:
					 	ArrayList<ArrayList<String>> StudentAge= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM student WHERE StudentID='"+StudentID+"'");
					 	//Take classes that assgined to this course right now:
					 	ArrayList<ArrayList<String>> ClassesAssigned= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM teacherinclassincourse WHERE coID='"+CourseID+"' AND SemesId='"+sem.getYear()+':'+sem.getType()+"'");
					 	if(ClassesAssigned==null)
					 	{
					 		 CoursesErr.setVisible(true);
							 CoursesErr.setText("There is no classes that assgined to the course:  " +Course+"  in this semester:  "+sem.getYear()+':'+sem.getType());
							 Timer time = new Timer(5000, new java.awt.event.ActionListener() {
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
			 	    			Send.setVisible(false);
			 	    			CourseCombo.setVisible(false);
			 	    			LabText.setVisible(false);
			 	    			ClassCombo.setVisible(false);
			 	    			Platform.runLater(() -> ClassCombo.getSelectionModel().clearSelection());
			 	    			Platform.runLater(() -> CourseCombo.getSelectionModel().clearSelection());
			 	    			Platform.runLater(() -> StudentCombo.getSelectionModel().clearSelection());
					 	}
					 	else
					 	{
					 		ClassList=new ArrayList<String>();
					 		for(int i=0;i<ClassesAssigned.size();i++)
					 		{
					 			String Cla=ClassesAssigned.get(i).get(0).substring(0,1);
					 			if(Cla.equals(StudentAge.get(0).get(2)))
					 			{
					 				ClassList.add(ClassesAssigned.get(i).get(0));
					 			}
					 		}
					 		if(ClassList.size()==0)
					 		{
					 			 CoursesErr.setVisible(true);
								 CoursesErr.setText("There is no classes that assgined to the course:  " +Course+"  in the same age of the student.");
								 Timer time = new Timer(5000, new java.awt.event.ActionListener() {
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
				 	    			Send.setVisible(false);
				 	    			CourseCombo.setVisible(false);
				 	    			LabText.setVisible(false);
				 	    			ClassCombo.setVisible(false);
				 	    			Platform.runLater(() -> ClassCombo.getSelectionModel().clearSelection());
				 	    			Platform.runLater(() -> CourseCombo.getSelectionModel().clearSelection());
				 	    			Platform.runLater(() -> StudentCombo.getSelectionModel().clearSelection());     
					 		}
					 		else
					 		{
					 			ObservableList ClaL= FXCollections.observableList(ClassList);
					 			ClassCombo.setItems(ClaL);
					 			ClassCombo.setVisible(true);
					 			Send.setVisible(true);
					 		}
					 	}
				 	}
			}
		 }
		//------------------------------------------------//  
}




