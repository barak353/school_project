package Secretary;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.Timer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import Entity.User;
import application.QueryController;
import javafx.fxml.Initializable;

public class DeleteStudentRequestFormController extends QueryController implements Initializable {

	    Object nextController=null;
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
	    private Text SuccessMessage;

	    @FXML
	    private Button Finish;

	    @FXML
	    private DialogPane dialogPane;

	    @FXML
	    private Label StudentsLable;

	    @FXML
	    private ComboBox<?> StudentCombo;

	    @FXML
	    private Button Save;

	    @FXML
	    private Text StudentErr;
		private ArrayList<ArrayList<String>> StudentsInCourse;
		private String RequiredStringCourse;
		private ArrayList<String> listCourses;
		private ObservableList L;
		private String ChosenCourse;
		private ArrayList<ArrayList<String>> CurrentSemester;
		private ArrayList<ArrayList<String>> CoursesInSemester;

	//------------------------------------------------//
		public DeleteStudentRequestFormController(String controllerID)
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
	   	  	CurrentSemester= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM semester WHERE status='true'");
	   	  	if(CurrentSemester!=null)
	   	  	{
	   	  		listCourses = new ArrayList<String>();
	   	  		//Get Current Semester:
	   	  		CoursesInSemester= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM coursesinsemester WHERE Sem='" +CurrentSemester.get(0).get(0) +"'");
	   	  		if(CoursesInSemester!=null)
	   	  		{
	   	  			for(int i=0;i<CoursesInSemester.size();i++)
	   	  			{
	   	  				//All the students that learn the course:
	   	  				ArrayList<ArrayList<String>> CourseWithStudents= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM studentincourse WHERE identityCourse='" + CoursesInSemester.get(i).get(0)+"'");
	   	  				//Insert to the combobox only courses with students:
	   	  				if(CourseWithStudents!=null)
	   	  				{
	   	  					ArrayList<ArrayList<String>> CourseName= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT courseName FROM courses WHERE idcourses='" + CoursesInSemester.get(i).get(0)+"'");
	   	  					 listCourses.add("("+CoursesInSemester.get(i).get(0)+")"+CourseName.get(0).get(0));	
	   	  				}
	   	  			}
	     	  		L= FXCollections.observableList(listCourses);
	     	  		ComboCourse.setItems(L);	
	     	  		if(listCourses.isEmpty()==true)
	     	  		{
	     	  				ErrCourseMessage.setVisible(true);
	     	  				ErrCourseMessage.setText("There is no courses that open this semester and that students assigned to them.");
	     	  				Finish.setVisible(true);
	     	  		}
	   	  		}
	    	  	//----------------------------------------------------------------------------------//  
	   	  		else
	   	  		{
	   	   	    	ErrCourseMessage.setVisible(true);
	   	   	  		ErrCourseMessage.setText("There is no courses in this semester.");
	   	   	  		Finish.setVisible(true);
	   	  		}
	   	  	}
	   	  	else
	   	  	{
	   	    	ErrCourseMessage.setVisible(true);
	   	  		ErrCourseMessage.setText("There is no semester's in the DB.");
	   	  		Finish.setVisible(true);
	   	  	}
		}
		//--------------------------------------------------------//
	    @FXML
	    void ChooseCourseHandler(ActionEvent event)
	    {
	    		       ChosenCourse =  (String) ComboCourse.getValue();
	    		       if(ChosenCourse!=null)
	    		       {
	    		    	   RequiredStringCourse = ChosenCourse.substring(ChosenCourse.indexOf("(") + 1, ChosenCourse.indexOf(")"));
	               		   StudentsInCourse= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM studentincourse WHERE identityCourse='" +RequiredStringCourse+"'");
	               		   if (StudentsInCourse!=null) //If there is students in the course:
	               		   {
	               			   ArrayList<String> listStudents = new ArrayList<String>();
	               			   for(int i=0;i<StudentsInCourse.size();i++)
	               			   {
	          	  		    	  ArrayList<ArrayList<String>> StudentsName= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT userName FROM user WHERE userID='" +StudentsInCourse.get(i).get(0)+"'");
	          	  		    	  listStudents.add("("+StudentsInCourse.get(i).get(0)+")- "+StudentsName.get(0).get(0));
	               			   }
	          	  		      ObservableList StudentL= FXCollections.observableList( listStudents);
	          	  		      StudentCombo.setItems(StudentL);
	          	  		      //---------------------------------//
	          	  		      //Set visible the next combobox:
	          	  		      StudentErr.setVisible(true);
	          	  		      dialogPane.setVisible(true);
	          	  		      StudentsLable.setVisible(true);
	          	  		      StudentCombo.setVisible(true);
	          	  		      Save.setVisible(true);
	          	  		      SuccessMessage.setVisible(true);
	          	  		      ErrCourseMessage.setText("");
	          	  		      //---------------------------------//   
	               		   }
	    		       }	  
	     }
	    //--------------------------------------------------------//
	    @FXML
	    void FinishHandler(ActionEvent event)
	    {
	    	try {
				   FXMLLoader loader = new FXMLLoader(getClass().getResource("/Secretary/SecretaryMainWindow.fxml"));
				   loader.setController(new SecretaryMainController ("SecretaryMainController "));
				   Pane login_screen_parent = loader.load();
				        Scene login_screen_scene=new Scene(login_screen_parent);
						Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();//the scene that the event came from.
						app_stage.hide();
						app_stage.setScene(login_screen_scene);
						app_stage.show(); 
			        } catch (IOException e) {
						System.err.println("Missing SecretaryMainController .fxml file");
						e.printStackTrace();
					}	  
	    }
	    //--------------------------------------------------------//
	    @FXML
	    void  SaveHandler(ActionEvent event)
	    {
	    	 String ChosenStudent =  (String) StudentCombo.getValue();
	    	 if (ChosenStudent==null)
	    	 {
	    		 StudentErr.setText("Choose a student please!!");
	    		 Timer time = new Timer(1500, new java.awt.event.ActionListener() {
			                @Override
			                public void actionPerformed(java.awt.event.ActionEvent e) {
			                try{
			                		 StudentErr.setText("");
			                }catch(java.lang.NullPointerException e1){
			                		
			                }
			                }
			            });
			      time.setRepeats(false);
			      time.start();
	    	 }
	    	 else
	    	 {
	     		String RequiredStringStudentID = ChosenStudent.substring(ChosenStudent.indexOf("(") + 1, ChosenStudent.indexOf(")")); 
		    	ArrayList<ArrayList<String>> result= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM messagestudent WHERE type='" + "Student Delete" + "' AND CouID='"+ RequiredStringCourse+"' AND StuIdentity='"+RequiredStringStudentID+"'");
		    	if(result!=null)
		    	{
		    		 ErrCourseMessage.setText("The message already exists.");
		    		 SuccessMessage.setText("");
		    		 Finish.setVisible(true);
		    	}
		    	else
		    	{   
		    		  DateTimeFormatter dtf = DateTimeFormatter.ofPattern("DD/MM/yyyy");
		    		  LocalDateTime now = LocalDateTime.now();
		    		  String Date=""+now.getDayOfMonth()+"/"+now.getMonthValue()+"/"+now.getYear();
			    	  transfferQueryToServer("INSERT INTO messagestudent (StuIdentity,CouID,type,Mdate,Answer) VALUES ('"+RequiredStringStudentID+"','"+RequiredStringCourse+"','"+"Student Delete" + "','"+Date+"','"+"NULL"+"')");
			    	  SuccessMessage.setVisible(true);
			    	  SuccessMessage.setText("The message was sended successfully.");
			    	  Timer time = new Timer(2500, new java.awt.event.ActionListener() {
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
			    		    		   		//CourseCombo.setValue(null);
			    		    		   		//StudentCombo.setValue(null);	
			    			    
			    				 
			    				 StudentErr.setText("");
			    		 //------------------------------------------------------------------------------------------------//
			    		 listCourses = new ArrayList<String>();
			    		 for(int i=0;i<CoursesInSemester.size();i++)
				  		 {
			    				ArrayList<ArrayList<String>> CourseWithStudents= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM studentincourse WHERE identityCourse='" + CoursesInSemester.get(i).get(0)+"'");	  				//Insert to the combobox only courses with students:
				  				if(CourseWithStudents!=null)
				  				{
				  					 ArrayList<ArrayList<String>> CourseName= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT courseName FROM courses WHERE idcourses='" + CoursesInSemester.get(i).get(0)+"'");
				  					 listCourses.add("("+CoursesInSemester.get(i).get(0)+")"+CourseName.get(0).get(0));	
				  				}
				  		 }
			  	  		L= FXCollections.observableList(listCourses);
			  	  		ComboCourse.setItems(L);
			    		 	//-------------------------------------------------------------------------------------------//
			   			  if(listCourses.isEmpty()==true)
			   			  {
			   				  ErrCourseMessage.setVisible(true);
			   	       		  ErrCourseMessage.setText("There is no more courses that open this semester and that students assigned to them.");
			   	       		  Finish.setVisible(true);
			   			  }
			       		   
			    	      StudentErr.setVisible(false);
			  		      dialogPane.setVisible(false);
			  		      StudentsLable.setVisible(false);
			  		      StudentCombo.setVisible(false);
			  		      Save.setVisible(false);
			  		      ComboCourse.setValue(null);
			  		      ComboCourse.getSelectionModel().clearSelection();
			  		      StudentCombo.setValue(null);
		    	}	
	    	 }
	    		 
	    }//SaveHandler
	    //--------------------------------------------------------//
	    @FXML
	    void TurningBack(ActionEvent event)
	    {
	    	this.nextController = new  AskRequestFormController("AskRequestFormController");
			this.Back("/Secretary/WhatFormChoose.fxml",nextController, event);
	    }
}


