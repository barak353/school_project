package Secretary;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.Timer;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import Login.LoginController;
import Entity.User;
import application.QueryController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * 
 * This controller handles the teacher placement action.
 */
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
    @FXML
    private Label TeacherLable;
    @FXML
    private Label ClassLable;
    @FXML
    private DialogPane Dialog;
    Object nextController=null;
    private ArrayList<ArrayList<String>> CurrentSemester;
    private ArrayList<String> listCourses;
    private ArrayList<ArrayList<String>> CoursesInSemester;
    private ObservableList L;
    private int hoursteacher;
    private int hoursCourse;
    @FXML
    private Button FinishButton;
    private String RequiredStringCourse;
    //------------------------------------------------//
	public TeacherPlacementController (String controllerID)
	{
	       super(controllerID);
	} 
	//------------------------------------------------// 
	/**
	 * 
	 * The function Turning back return's to the main screen of the secretary.
	 * @param event
	 */
	@FXML
	void TurningBack(ActionEvent event)
	{
		this.nextController = new SecretaryMainController("SecretaryMainController");
		this.Back("/Secretary/SecretaryMainWindow.fxml",nextController, event);
	}
	/**
	 * 
	 * Initialize function, shows the logged in user, also initialize the combobox of courses that open in the current 
	 * semester and courses that classes already assigned to them for making teacher change only.
	 * @param arg0
	 * @param arg1
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {//this method perform when this controller scene is showing up.
		User user = User.getCurrentLoggedIn();
		userID.setText(user.GetUserName());
		//Defining courses list in the semester:
   	  	CurrentSemester= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM semester WHERE status='true'");
   	  	if(CurrentSemester!=null)
   	  	{
   	  		listCourses = new ArrayList<String>();
   	  		//Get Current Semester:
   	  		CoursesInSemester= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM coursesinsemester WHERE Sem='" +CurrentSemester.get(0).get(0) +"'");
   	  		if(CoursesInSemester!=null) //If there is semester in DB.
   	  		{
   	  			for(int i=0;i<CoursesInSemester.size();i++)
   	  			{
	  				//All the classes that learn the course:
	  				ArrayList<ArrayList<String>> CourseWithClasses= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM teacherinclassincourse WHERE coID='" + CoursesInSemester.get(i).get(0)+"' AND SemesId='"+CurrentSemester.get(0).get(0)+"'");
	  				//Insert to the combobox only courses with students:
	  				if(CourseWithClasses!=null)
	  				{
	  					ArrayList<ArrayList<String>> CourseName= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT courseName FROM courses WHERE idcourses='" + CoursesInSemester.get(i).get(0)+"'");
	  					listCourses.add("("+CoursesInSemester.get(i).get(0)+")"+CourseName.get(0).get(0));	
	  				}
	  			}
   	  			L= FXCollections.observableList(listCourses);
   	  			CourseCombo.setItems(L);	
   	  			if(listCourses.isEmpty()==true)
   	  			{
 	  				ErrText.setVisible(true);
 	  				ErrText.setText("There is no courses that open this semester and that students assigned to them.");
 	  				FinishButton.setVisible(true);
   	  			}
   	  		}
   	  		else
   	  		{
   	  			ErrText.setVisible(true);
   	  			ErrText.setText("There is no courses in this semester that students learn them.");
   	  		    FinishButton.setVisible(true);
   	  		}
	  	}
	  	else
	  	{
	  		ErrText.setVisible(true);
	  		ErrText.setText("There is no semester's in the DB.");
	  		FinishButton.setVisible(true);
	  	}
	}
	//--------------------------------------------------------------------------------------------//
	/**
	 * 
	 * The function CourseHandler will be called when the secretry will choose a course, than the classes that
	 * learn this chosen course will appear in the combobox of the classes.
	 * In addition, all the teacher's that belong's to the teaching unit of the chosen course will appear in
	 * the teacher's combobox.
	 * @param event
	 */
	@FXML
	public void CourseHandler (ActionEvent event)
	{
		int counter=0;
		int MyFlag=1;
		String ChosenCourse=(String) CourseCombo.getValue();
		//-----------------------------------------------//
		if (ChosenCourse!=null)
		{
			 ArrayList<String> listClasses = new ArrayList<String>();
			 RequiredStringCourse = ChosenCourse.substring(ChosenCourse.indexOf("(") + 1, ChosenCourse.indexOf(")"));
			 //Get the classes that learn the chosen course:
			 ArrayList<ArrayList<String>> ClassesInCourse= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM teacherinclassincourse WHERE SemesId='" +CurrentSemester.get(0).get(0) + "' AND coID='" +RequiredStringCourse+ "'");
			 for(int i=0;i<ClassesInCourse.size();i++)
			 {
				 listClasses.add(ClassesInCourse.get(i).get(0));
			 }
			 ObservableList List=FXCollections.observableList(listClasses);
			 ClassCombo.setItems(List);	
			 //-----------------------------------------------//
			 ArrayList<String> listTeachers = new ArrayList<String>();
			 //Get the teaching unit of the course:
			 ArrayList<ArrayList<String>> TeachingUnit= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM courses WHERE idcourses='" +RequiredStringCourse+ "'");
			 String MyTeachingUnit=TeachingUnit.get(0).get(2);
			 //Get all the teachers that theach this unit: 
			 ArrayList<ArrayList<String>> TeachersInUnit= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM teacherinteachunit WHERE teachUnitNum='" +MyTeachingUnit+ "'");

			 for(int i=0;i< TeachersInUnit.size();i++)
			 {	
				 	 ArrayList<ArrayList<String>> Teacher= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM teacher WHERE teacherid='" +TeachersInUnit.get(i).get(0)+ "'");
				 
				 	 hoursteacher=Integer.parseInt(Teacher.get(0).get(1));
	    	         hoursCourse=Integer.parseInt(TeachingUnit.get(0).get(3)); //Get Course hours
	    	         if (hoursteacher<hoursCourse)
	    		     {
	    	        	 counter++;
	    		     }
			 }
			 if(counter==TeachersInUnit.size())
			 {
				 ErrText.setText("All the teachers in this unit exceed their teaching hours.\nCan't make the change.");
				 Timer time = new Timer(1500, new java.awt.event.ActionListener() {
		                @Override
		                public void actionPerformed(java.awt.event.ActionEvent e) {
		                	try{
		                		 ErrText.setText("");
		                	}catch(java.lang.NullPointerException e1){
		                		
		                	}
		                }
		            });
		            time.setRepeats(false);
		            time.start();
				 MyFlag=0;
				 FinishButton.setVisible(true);
				 Dialog.setVisible(false);
				 ClassCombo.setVisible(false);
				 TeacherCombo.setVisible(false);
				 SaveID.setVisible(false);
				 TeacherLable.setVisible(false);
				 ClassLable.setVisible(false);
				 counter=0;
			 }
			 //--------------------------------------------------------------------//
			 if(MyFlag==1)
			 {
				 FinishButton.setVisible(false);
				 ErrText.setText("");
				 for(int i=0;i< TeachersInUnit.size();i++)
				 {
					 ArrayList<ArrayList<String>> TeacherName= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT userName FROM user WHERE userID='" +TeachersInUnit.get(i).get(0)+ "'");
				     listTeachers.add("("+TeachersInUnit.get(i).get(0)+") - "+TeacherName.get(0).get(0));
					 ObservableList MyList=FXCollections.observableList(listTeachers);
					 TeacherCombo.setItems(MyList);	
				 } 
				 //-----------------------------------------------//
				 Dialog.setVisible(true);
				 ClassCombo.setVisible(true);
				 TeacherCombo.setVisible(true);
				 SaveID.setVisible(true);
				 TeacherLable.setVisible(true);
				 ClassLable.setVisible(true);
				 //-----------------------------------------------//
			 }
		}
	}
	/**
	 * 
	 * The function Finish will return us to the main window of the secretary.
	 * @param event
	 */
	@FXML
	void FinishButtonHandler(ActionEvent event)
	{
		 try {
			   FXMLLoader loader = new FXMLLoader(getClass().getResource("/Secretary/SecretaryMainWindow.fxml"));
			   loader.setController(new SecretaryMainController("SecretaryMainController"));
			   Pane login_screen_parent = loader.load();
			        Scene login_screen_scene=new Scene(login_screen_parent);
					Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();//the scene that the event came from.
					app_stage.hide();
					app_stage.setScene(login_screen_scene);
					app_stage.show(); 
		        } catch (IOException e) {
					System.err.println("Missing openNewSemesterWindow.fxml file");
					e.printStackTrace();
				}
	}
	//--------------------------------------------------------------------------------------------//
	/**
	 * 
	 * This function will check if the chosen teacher can teach the chosen class in the chosen course, checks
	 * if the teacher didn't exceed her teaching hour's and if all the conditions are fullfiled, this function 
	 * will make the teacher placement change.
	 * @param event
	 */
	@FXML
	public void SaveButtonHandler(ActionEvent event)
	{
		String Class= (String) ClassCombo.getValue();
		String Teach=(String) TeacherCombo.getValue();


		//----------------------------------------//
		 if (Class==null || Teach==null)
		 {
			 ErrText.setText("Please fill all the fields");
			 finishmessage.setText("");
		 }
		 else
		 {
			 String RequiredStringTeacher =  Teach.substring( Teach.indexOf("(") + 1,  Teach.indexOf(")"));
			 //Get the chosen teacher:
			 ArrayList<ArrayList<String>> TeacherCheck=	(ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM teacherinclassincourse WHERE clasID='"+ Class + "' AND coID=" +RequiredStringCourse+" AND SemesId='"+CurrentSemester.get(0).get(0)+"'"); 
			 if(TeacherCheck.get(0).get(3).equals(RequiredStringTeacher)==true)
			 {
				 ErrText.setText("The Teacher already teach this class");
				 Timer time = new Timer(3000, new java.awt.event.ActionListener() {
		                @Override
		                public void actionPerformed(java.awt.event.ActionEvent e) {
		                	try{
		                		 ErrText.setText("");
		                	}catch(java.lang.NullPointerException e1){
		                		
		                	}
		                }
		            });
		            time.setRepeats(false);
		            time.start();
				 finishmessage.setText("");
				 TeacherCombo.getSelectionModel().clearSelection();
				 FinishButton.setVisible(true);
			 }
			 else
			 {
				 ArrayList<ArrayList<String>> Teacher= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM teacher WHERE teacherid='" +RequiredStringTeacher+ "'");
				 hoursteacher=Integer.parseInt(Teacher.get(0).get(1));
		    	 if (hoursteacher<hoursCourse)
		    	 {
		    	     ErrText.setText("The Teacher exceed her teaching hours.\nPlease choose another teacher");
		    	     Timer time = new Timer(3000, new java.awt.event.ActionListener() {
			                @Override
			                public void actionPerformed(java.awt.event.ActionEvent e) {
			                	try{
			                		 ErrText.setText("");
			                	}catch(java.lang.NullPointerException e1){
			                		
			                	}
			                }
			            });
			            time.setRepeats(false);
			            time.start();
		    	     finishmessage.setText("");
		    	     FinishButton.setVisible(true);
		    	     TeacherCombo.getSelectionModel().clearSelection();
		    	 }
		    	 else
		    	 {
		    		    //Checking the answer from the school director:
				    	ArrayList<ArrayList<String>> result= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM messageteacher WHERE type='" + "Teacher Change" + "' AND TEACHid='"+RequiredStringTeacher +"' AND CLASidentity='"+Class+"' AND CourID='"+ RequiredStringCourse+"'");
				    	if(result==null)
				    	{
				    		ErrText.setText("There is no such message, can't make the change.");
				    	    finishmessage.setText("");
				    	    FinishButton.setVisible(true);
				    	    SaveID.setVisible(false);
				    	    Dialog.setVisible(false);
				    	    TeacherLable.setVisible(false);
				    	    ClassLable.setVisible(false);
				    	    TeacherCombo.setVisible(false);
				    	    ClassCombo.setVisible(false);
				    	    TeacherCombo.getSelectionModel().clearSelection();
				    	    ClassCombo.getSelectionModel().clearSelection();
				    	    CourseCombo.getSelectionModel().clearSelection();
				    	}
				    	else
				    	{	
				    		if(result.get(0).get(5).equals("YES")==true)
				    		{
				    			int res2;
				    		    int resAdd;
				    	        res2=hoursteacher-hoursCourse;
				    	        resAdd=hoursteacher+hoursCourse;
						    	String res=""+res2; 
						    	transfferQueryToServer("UPDATE teacher SET MaxHour="+res+" WHERE teacherid="+ Teacher.get(0).get(0)); 
						    	transfferQueryToServer("UPDATE teacherinclassincourse SET Tidentity="+Teacher.get(0).get(0)+" WHERE clasID='"+ Class + "' AND coID=" +RequiredStringCourse+" AND SemesId='"+CurrentSemester.get(0).get(0)+"'"); 
						    	transfferQueryToServer("UPDATE teacher SET MaxHour="+resAdd+" WHERE teacherid="+ TeacherCheck.get(0).get(3)); 
			    		   		transfferQueryToServer("DELETE FROM messageteacher WHERE type='" + "Teacher Change" + "' AND TEACHid='"+RequiredStringTeacher +"' AND CLASidentity='"+Class+"' AND CourID='"+ RequiredStringCourse+ "'");
						    	finishmessage.setText("Teacher placement change succeeded");
						    	ErrText.setText("");
						    	FinishButton.setVisible(true);
						    	TeacherCombo.getSelectionModel().clearSelection();
					    	    ClassCombo.getSelectionModel().clearSelection();
					    	    CourseCombo.getSelectionModel().clearSelection();
					    	    SaveID.setVisible(false);
					    	    Dialog.setVisible(false);
					    	    TeacherLable.setVisible(false);
					    	    ClassLable.setVisible(false);
					    	    TeacherCombo.setVisible(false);
					    	    ClassCombo.setVisible(false);
				    		}
				    		else
				    		{
				    			ErrText.setText("The school director didn't approved the request.");
				    			finishmessage.setText("");
				    			FinishButton.setVisible(true);
					    	    SaveID.setVisible(false);
					    	    Dialog.setVisible(false);
					    	    TeacherLable.setVisible(false);
					    	    ClassLable.setVisible(false);
					    	    TeacherCombo.setVisible(false);
					    	    ClassCombo.setVisible(false);
					    	    TeacherCombo.getSelectionModel().clearSelection();
					    	    ClassCombo.getSelectionModel().clearSelection();
					    	    CourseCombo.getSelectionModel().clearSelection();
				    		}
				    	}
				 }	  
			 }
		 }//else*/	 
	 }//Save	
}//Class