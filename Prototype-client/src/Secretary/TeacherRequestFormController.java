package Secretary;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
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

public class TeacherRequestFormController extends QueryController implements Initializable{
	
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
	public TeacherRequestFormController (String controllerID)
	{
	       super(controllerID);
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
		SaveID.setVisible(true);
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
 	  				SaveID.setVisible(false);
   	  			}
   	  		}
   	  		else
   	  		{
   	  			ErrText.setVisible(true);
   	  			ErrText.setText("There is no courses in this semester that students learn them.");
   	  		    FinishButton.setVisible(true);
				SaveID.setVisible(false);
   	  		}
	  	}
	  	else
	  	{
	  		ErrText.setVisible(true);
	  		ErrText.setText("There is no semester's in the DB.");
	  		FinishButton.setVisible(true);
			SaveID.setVisible(false);
	  	}
	}
	//--------------------------------------------------------------------------------------------//
	@FXML
	public void CourseHandler (ActionEvent event)
	{
		int counter=0;
		int MyFlag=1;
		String ChosenCourse=(String) CourseCombo.getValue();
		if(ChosenCourse==null)
		{
			ErrText.setText("Please choose a course.");
			 finishmessage.setText("");
		}
		//-----------------------------------------------//
		else
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
					System.err.println("Missing SecretaryMainWindow.fxml file");
					e.printStackTrace();
				}
	}
	//--------------------------------------------------------------------------------------------//
	@FXML
	public void SaveButtonHandler(ActionEvent event)
	{
		SaveID.setVisible(true);
		String Class= (String) ClassCombo.getValue();
		String Teach=(String) TeacherCombo.getValue();
		//----------------------------------------//
		 if (Class==null || Teach==null)
		 {
			 ErrText.setText("Please fill all the fields");
			 finishmessage.setText("");
			 FinishButton.setVisible(false);
	  		 SaveID.setVisible(true);
		 }
		 else
		 {
			 String RequiredStringTeacher =  Teach.substring( Teach.indexOf("(") + 1,  Teach.indexOf(")"));
			 //Get the chosen teacher:
			 ArrayList<ArrayList<String>> TeacherCheck=	(ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM teacherinclassincourse WHERE clasID='"+ Class + "' AND coID=" +RequiredStringCourse+" AND SemesId='"+CurrentSemester.get(0).get(0)+"'"); 
			 if(TeacherCheck.get(0).get(3).equals(RequiredStringTeacher)==true)
			 {
				 ErrText.setText("The Teacher already teach this class");	
				 finishmessage.setText("");
				 FinishButton.setVisible(true);
			 }
			 else
			 {
				 ArrayList<ArrayList<String>> Teacher= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM teacher WHERE teacherid='" +RequiredStringTeacher+ "'");
				 hoursteacher=Integer.parseInt(Teacher.get(0).get(1));
		    	 if (hoursteacher<hoursCourse)
		    	 {
		    	     ErrText.setText("The Teacher exceed her teaching hours.\nPlease choose another teacher");
		    	     finishmessage.setText("");
		    	     FinishButton.setVisible(true);
		    	 }
		    	 else
		    	 {
		    		    String message="Teacher Change";
		    		    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("DD/MM/yyyy");
		    		    LocalDateTime now = LocalDateTime.now();
		    		    String Date=""+now.getDayOfMonth()+"/"+now.getMonthValue()+"/"+now.getYear();

				    	ArrayList<ArrayList<String>> result= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM message WHERE type='" + message +"' AND TEACHid='"+RequiredStringTeacher +"' AND CLASidentity='"+Class+"' AND CouID='"+ RequiredStringCourse+"'");
				    	if(result!=null)
						{
				    		 ErrText.setText("The message already exists.");
				    		 FinishButton.setVisible(true);
						}
				    	else
				    	{
				    		transfferQueryToServer("INSERT INTO message (type,Mdate,TEACHid,CLASidentity,CouID) VALUES ('" + message + "','"+Date+"','"+ RequiredStringTeacher + "','" + Class +"','"+ RequiredStringCourse+"')");
				    		finishmessage.setText("The message was sended successfully.");
				    		ErrText.setText("");
				    		FinishButton.setVisible(true);
					    	SaveID.setVisible(false);
				    	}
				 }	  
			 }
		 }//else*/	 
	 }//Save	
}//Class