package Secretary;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.Timer;

import Entity.User;
import application.QueryController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.text.Text;

public class InsertStudentToCourseController extends QueryController implements Initializable{
		
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
    private String ChosenStud;
    private String ChosenStudent;
	Object nextController=null;
	//----------------------------------------------------------------------//
	public InsertStudentToCourseController(String controllerID)
	{
			super(controllerID);
	} 
    //----------------------------------------------------------------------//
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		User user = User.getCurrentLoggedIn();
		userID.setText(user.GetUserName());
		//--------------------------------------//
		//Students:
    	ArrayList<ArrayList<String>> AllTheStudents= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM student");
    	if(AllTheStudents==null)
    	{
    		StudentErr.setText("There is no students in DB.");	
    	}
    	else
    	{
    		ArrayList<String> StudentL = new ArrayList<String>();
        	//Taking the names of the students:
        	for(int i=0;i<AllTheStudents.size();i++)
        	{
            	ArrayList<ArrayList<String>> Name= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM user WHERE userID='" + AllTheStudents.get(i).get(0)+"'");
            	StudentL.add("("+AllTheStudents.get(i).get(0)+") - "+Name.get(0).get(1));
        	}
        	ObservableList obList= FXCollections.observableList(StudentL);
        	StudentCombo.setItems(obList);
        	StudentCombo.setVisible(true);
    	}
    	
	}
	//----------------------------------------------------------------------//
	@FXML
	void TurningBack(ActionEvent event)
	{
		this.nextController = new SecretaryMainController("SecretaryMainController");
		this.Back("/Secretary/SecretaryMainWindow.fxml",nextController, event);
	} 
	//----------------------------------------------------------------------//
    @FXML
    void SaveHandler(ActionEvent event) 
    {
    	String Course=(String) CourseCombo.getValue();
    	String ChosenCourse;
    	if(Course==null)
    	{
    		CoursesErr.setText("Please choose a course.");
    		 Timer time = new Timer(2500, new java.awt.event.ActionListener() {
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
    		
			ChosenCourse = Course.substring(Course.indexOf("(") + 1, Course.indexOf(")"));
	    	ArrayList<ArrayList<String>> IfAlreadyAssigned= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM studentincourse WHERE identityCourse='"+ChosenCourse+"' AND identityStudent='"+ChosenStudent+"'");
	    	if(IfAlreadyAssigned!=null)
	    	{
	    		CoursesErr.setText("The student is already assigned to the course: "+Course );
	    		 Timer time = new Timer(2500, new java.awt.event.ActionListener() {
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
		    	ArrayList<ArrayList<String>> MessageCheck= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM messagestudent WHERE type='"+"Student Insert"+"' AND CouID='"+ChosenCourse+"' AND StuIdentity='"+ChosenStudent+"'");
		    	if(MessageCheck==null)
		    	{
		    		CoursesErr.setText("There is no such message.\nCan't make the change.");
		    		 Timer time = new Timer(2500, new java.awt.event.ActionListener() {
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
		    		if(MessageCheck.get(0).get(4).equals("YES")==true)
		    		{
			    		transfferQueryToServer("INSERT INTO studentincourse (identityStudent,identityCourse,Grade) VALUES ('" + ChosenStudent + "','" + ChosenCourse + "','" + "" + "')");
	    		   		transfferQueryToServer("DELETE FROM messagestudent WHERE type='"+"Student Insert"+"' AND CouID='"+ChosenCourse+"' AND StuIdentity='"+ChosenStudent+"'");
	    		   		ArrayList<ArrayList<String>> c= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM studentinprecourse WHERE childID='" + ChosenStudent + "' AND pCourseID='" +ChosenCourse+"'");
		 	    		if(c!=null)//If the student doing aggain the course
		 	    		{
				    		transfferQueryToServer("DELETE FROM studentinprecourse WHERE childID="+ChosenStudent+" and pCourseID="+ChosenCourse+"");
		 	    		}
	    		   		SuccessMessage.setVisible(true);
	    		   		SuccessMessage.setText("The student: "+ChosenStud+"inserted successfully to the course: "+Course);
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
		    		}
		    		else
		    		{
			    		CoursesErr.setText("The school director didn't approved this change.\nCan't make the change.");
			    		 Timer time = new Timer(2500, new java.awt.event.ActionListener() {
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
		    	}
	    	}
    		
    		
    		
    	}

    }//Save Handler
    //----------------------------------------------------------------------//
    @FXML
    void StudentChosen(ActionEvent event) 
    {
    	
    	//----------------------------------//
    	ChosenStud=(String) StudentCombo.getValue();
		if(ChosenStud==null)
		{
    		StudentErr.setText("Please choose a student.");	
    		 Timer time = new Timer(2500, new java.awt.event.ActionListener() {
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
			ChosenStudent = ChosenStud.substring(ChosenStud.indexOf("(") + 1, ChosenStud.indexOf(")"));
			//----------------------------------//
	    	//Get courses that open this semester:
			
	    	ArrayList<ArrayList<String>> Current= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM semester WHERE status='"+"true"+"'");
	    	if(Current==null)
	    	{
	    		StudentErr.setText("There is no current semester's in DB.");
	    		 Timer time = new Timer(2500, new java.awt.event.ActionListener() {
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
	    		ArrayList<ArrayList<String>> CoursesInSemester= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM coursesinsemester WHERE Sem='"+Current.get(0).get(0)+"'");
		    	if(CoursesInSemester==null)
		    	{
		    		StudentErr.setText("There is no courses that open in this semester.");
		    		 Timer time = new Timer(2500, new java.awt.event.ActionListener() {
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
		    		ArrayList<String> CourseL = new ArrayList<String>();

		    		for(int i=0;i<CoursesInSemester.size();i++)
		        	{
		            	ArrayList<ArrayList<String>> NameCourse= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM courses WHERE idcourses='" + CoursesInSemester.get(i).get(0)+"'");
		            	CourseL.add("("+CoursesInSemester.get(i).get(0)+") - "+NameCourse.get(0).get(1));
		        	}
		        	ObservableList L= FXCollections.observableList(CourseL);
		        	CourseCombo.setItems(L);
		        	CourseCombo.setVisible(true);
		        	Dialog.setVisible(true);
		        	LabText.setVisible(true);
		        	Save.setVisible(true);
		        	CoursesErr.setVisible(true);
		    	}//Else courses in semester!=null
	    	}//Else current semester!=null
		}//Else chosen stud!=null
    }////Handler
    //----------------------------------------------------------------------//
}//Class Controller