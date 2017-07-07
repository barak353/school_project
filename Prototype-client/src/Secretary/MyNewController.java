package Secretary;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.Timer;

import Entity.Semester;
import Entity.Course;
import Entity.Teacher;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MyNewController extends QueryController implements Initializable {

	private Object nextController=null;
	@FXML
	private Button logout;
    @FXML
    private Text userID;
    @FXML
    private Button back; 
    @FXML
    private ComboBox<?> CourseL;
    @FXML
    private ComboBox<?> ClassL;
    @FXML
    private TextArea DataField;
    @FXML
    private TextArea errorlog;
	@FXML
    private Text chooseteachertext;
	@FXML
    private Button Assign;
	@FXML
    private Button Next;
	@FXML
    private Button Finish;
	@FXML
    private DialogPane dialog2;
    @FXML
    private Text finishtxt;
    @FXML
    private Text err;
	@FXML
    private ComboBox<?> teacherList;
    private Semester sem;
    @FXML
    private Text t;
    @FXML
    private DialogPane diaID;
    private String RequiredStringCourse;
    private Course Course;
    private String  ClassChoise;
    private String CourseChoise;
    private int TeacherFlag=0;
    private ArrayList<String> studentsAssigned;
    private ArrayList<String> studentsNotAssigned;
    private ArrayList<ArrayList<String>> SName;
    private ArrayList<String> PreCoursesOfCourse;
    private ArrayList<String> AllStudentsInClass;
    private int counterPrint=0;
    private ArrayList<Teacher> Teacher=new ArrayList<Teacher>();
    private String NotAssigned="";
    public int StatusJunit=0;
    //--------------------------------------------------------------------------//
	 /**
  	 * 
  	 * Initialize function, shows the logged in user, and initialize the courses combobox that open in this current semester.
  	 * In addition, initialize the classes combobox.
  	 * @param arg0
  	 * @param arg1
  	 */
	  public void initialize(URL arg0, ResourceBundle arg1) {//this method perform when this controller scene is showing up.
		User user = User.getCurrentLoggedIn();
		userID.setText(user.GetUserName());
		//------------------------------------------------------------------//
		sem=Semester.getCurrentSemester();
		DataField.setText(sem.GetMyString());
		DataField.setVisible(true);
		//Checking if there are courses in this semester in DB:
		if (sem.getCourseList().isEmpty()==true)
		{
			t.setText("There is no courses in this semester");
		}
		//-----------------------------------------//
		else
		{
			ArrayList<String> list = new ArrayList<String>();
		    for (int i=0;i<sem.getCourseList().size();i++)
		    {
		    	list.add(sem.getCourseList().get(i));
		    }
		    //------------------------------------------------------------------//
		    ObservableList obList= FXCollections.observableList(list);
		    CourseL.setItems(obList);
		    CourseL.setVisible(true);
		    //------------------------------------------------------------------//
		    ArrayList<ArrayList<String>> res= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM studentinclass");
			if(res==null)
			{
				t.setText("There is no classes with students in the DB.");
				 Timer time = new Timer(1500, new java.awt.event.ActionListener() {
		                @Override
		                public void actionPerformed(java.awt.event.ActionEvent e) {
		                	try{
		                		t.setText("");
		                	}catch(java.lang.NullPointerException e1){
		                	}
		                }
		            });
		            time.setRepeats(false);
		            time.start();
			}
			else
			{
				ArrayList<String> list2 = new ArrayList<String>();
				list2.add(res.get(0).get(1));
				for(int i=0;i<res.size();i++) //Run on the table
				{
					for(int j=0;j<list2.size();j++) //Run to the list
					{
						if(res.get(i).get(1).equals(list2.get(j))==true)
						{
							break;
						}
						else if (j==list2.size()-1 && res.get(i).get(1).equals(list2.get(j))==false)
						{
							list2.add(res.get(i).get(1));
						}
					}

				}
				    //------------------------------------------------------------------//
				    ObservableList comlist= FXCollections.observableList(list2);
				    ClassL.setItems(comlist);
				    ClassL.setVisible(true);
			}
		}
	}
	//--------------------------------------------------------------------------//
	public MyNewController (String controllerID)
	{
			super(controllerID);
	}
	//--------------------------------------------------------------------------//
	/**
	* 
	* The function TurningBack return's to the open new semester window.
	* conditions first.
	* @param event
	*/
	@FXML
	void TurningBack(ActionEvent event)
	{
		this.nextController = new OpenNewSemesterController("OpenNewID");
		this.Back("/Secretary/OpenNewSemesterWindow.fxml",nextController,event);
	}	
	//--------------------------------------------------------------------------//
	  /**
	  * 
	  * The function FinishHandler return's to the main screen of the secretary.
	  * @param event
	  */
	  @FXML
	  void FinishHandler(ActionEvent event)
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
					System.err.println("Missing SecretaryMainController.fxml file");
					e.printStackTrace();
				}
	  }
	//--------------------------------------------------------------------------//
	/**
	* 
	* The function AssignHandler assignes the chosen class to the chosen course after cheking 
	* conditions first.
	* Also fill's the appropriate teachers from the same teaching unit of the chosen course.
	* @param event
	*/
	@FXML
	public void AssignHandler(ActionEvent event) 
	{  
		String AssigningResult;
		//-------------------------------------------//
		CourseChoise =  (String) CourseL.getValue();
	    ClassChoise = (String) ClassL.getValue();
	    AssigningResult=AssignClassToCourse(ClassChoise,CourseChoise);
	    switch(AssigningResult)
	    {
	    	   case "Missing Class":
	    	   {
	    		   PrintErr("Fill Class Please.");
	    		   break;
	    	   }
	    	   case "Missing Course":
	    	   {
	    		   PrintErr("Fill Course Please.");
	    		   break;
	    	   }
	    	   case "Missing Class and Course":
	    	   {
	    		   PrintErr("Fill Class And Course Please.");
	    		   break;
	    	   }
	    	   case "Missing Teacher":
	    	   {
	    		   PrintErr("Fill Teacher Please.");
	    		   break;
	    	   }
	    	   case "Class Already Assigned":
	    	   {
	    		   PrintErr("The class: "+ClassChoise+" is already assigned to the course: "+CourseChoise);
	    		   CourseL.getSelectionModel().clearSelection();
	    		   ClassL.getSelectionModel().clearSelection();
		           break;
	    	   }
	    	   case "ALL":
	    	   {
	    		   PrintErr("All the student's in the class didnt fill the pre courses.");
	    		   CourseL.getSelectionModel().clearSelection();
	    		   ClassL.getSelectionModel().clearSelection();
	    		   break;
	    	   }
	    	   case "Success":
	    	   {
	    		   break;
	    	   }
	    }//switch
	}//AssignHandler
	//--------------------------------------------------------------------------//
	public String AssignClassToCourse(String Class, String Course)
	{
		
		sem.setMyString2("");
		if (StatusJunit==0)errorlog.setText(sem.GetMyString2());
		if (StatusJunit==0)errorlog.setVisible(false);
		ArrayList<String> PreDidntFill;
		studentsNotAssigned=new ArrayList<String>();
		studentsAssigned=new ArrayList<String>();
		NotAssigned="";
		if(TeacherFlag==1)
		{		
			String Check=Course.substring(Course.indexOf("(") + 1, Course.indexOf(")"));
			if(Check.equals(RequiredStringCourse)==false)
			{
				if (StatusJunit==0)diaID.setVisible(false);
				if (StatusJunit==0)chooseteachertext.setVisible(false);
				if (StatusJunit==0)teacherList.setVisible(false);
				TeacherFlag=0;
			}
		}
		//----------------------------------------------------------------------//
		//Checking the details:
		if(TeacherFlag==1)
		{		
			String Check=Course.substring(Course.indexOf("(") + 1, Course.indexOf(")"));
			if(Check.equals(RequiredStringCourse)==true)
			{
				return "Missing Teacher";
			}
		}
		
		 if(Class==null && Course!=null)return "Missing Class";
		 if(Class!=null && Course==null)return "Missing Course";
		 if (Class==null && Course==null) return "Missing Class and Course";
		//---------------------------------------------------------------------//
		//Checking if the class already assigned to the course:
		RequiredStringCourse = Course.substring(Course.indexOf("(") + 1, Course.indexOf(")"));
    	ArrayList<ArrayList<String>> result= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM teacherinclassincourse WHERE clasID='" + Class + "' AND coID='" +RequiredStringCourse+"'");
    	//If already assigned:
    	if(result!=null) return "Class Already Assigned";
    	//---------------------------------------------------------------------//
    	AllStudentsInClass=TakeStudentsFromClass();
    	PreCoursesOfCourse=TakePreCoursesOfCourse();
    	//There is no pre courses 
    	if (PreCoursesOfCourse==null)
    	{
    		if (StatusJunit==0)sem.setMyString(sem.GetMyString()+"\n---------------------------------------------\n"+"Class: "+ClassChoise+" -->  Course: "+CourseChoise);
    		if (StatusJunit==0)DataField.setText(sem.GetMyString());
	     	for(int j=0;j<AllStudentsInClass.size();j++)
        	{
        		if(CheckIfStudentAlradyAssigned(AllStudentsInClass.get(j),RequiredStringCourse)==false) //If the student not assigned
        		{
        			studentsAssigned.add(AllStudentsInClass.get(j));
        		}
        		else
        		{
        			if (StatusJunit==0)errorlog.setVisible(true);
        			SName= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM user WHERE userID='" + AllStudentsInClass.get(j) +"'");
        			if (StatusJunit==0)sem.setMyString2(sem.GetMyString2()+"\n--------------------------------------\n"+"The student ("+AllStudentsInClass.get(j)+") - "+SName.get(0).get(1)+" already assigned to the course: "+CourseChoise+" by exceptional registration.");
        	    	if (StatusJunit==0)errorlog.setText(sem.GetMyString2());
        	    	studentsNotAssigned.add(SName.get(0).get(1));
        		}
        	}
	     	//-----------------------------------------------------------//
	 		
	 		//All the student's assigned
	 		if(studentsNotAssigned.size()==0)
	 		{
	 			if (StatusJunit==0)diaID.setVisible(true);
	 			if (StatusJunit==0)chooseteachertext.setVisible(true);
	 			if (StatusJunit==0)teacherList.setVisible(true);
		 		TeacherFlag=1;
		 		if (StatusJunit==0)TakeTeacher();
	 			return "Success";
	 		}
	 		else if (studentsNotAssigned.size()==AllStudentsInClass.size())
	 		{
	 			return "ALL";
	 		}
	 		else
	 		{
	 			if (StatusJunit==0)diaID.setVisible(true);
	 			if (StatusJunit==0)chooseteachertext.setVisible(true);
	 			if (StatusJunit==0)teacherList.setVisible(true);
		 		TeacherFlag=1;
		 		if (StatusJunit==0)TakeTeacher();
	 			for(int i=0;i<studentsNotAssigned.size();i++)
	 			{
	 				NotAssigned=NotAssigned+"\n"+studentsNotAssigned.get(i);
	 			}
	 			if (StatusJunit==0)TakeTeacher();
	 			return NotAssigned;
	 		}
	 		//-----------------------------------------------------------//
    	}//PreCoursesOfCourse==null
        //---------------------------------------------------------------//
    	//If there are pre courses to the course:
    	//For each student in the class:
    	for(int j=0;j<AllStudentsInClass.size();j++)
    	{
    		 if(CheckIfStudentAlradyAssigned(AllStudentsInClass.get(j),RequiredStringCourse)==true) //If the student already assigned alone to the course
	    	 {
      	    	SName= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM user WHERE userID='" + AllStudentsInClass.get(j) +"'");
      	    	if (StatusJunit==0)PrintErr("The student ("+AllStudentsInClass.get(j)+") - "+SName.get(0).get(1)+" already assigned to the course: "+CourseChoise+" by exceptional registration.");
    			if (StatusJunit==0)errorlog.setVisible(true);
    			if (StatusJunit==0)sem.setMyString2(sem.GetMyString2()+"\n--------------------------------------\n"+"The student ("+AllStudentsInClass.get(j)+") - "+SName.get(0).get(1)+" already assigned to the course: "+CourseChoise+" by exceptional registration.");
     	    	if (StatusJunit==0)errorlog.setText(sem.GetMyString2());
     	    	NotAssigned=NotAssigned+"\n"+SName.get(0).get(1);
     	    	studentsNotAssigned.add(SName.get(0).get(1));
	    	 }
    		 else
    		 {
    			 PreDidntFill=CheckIfStudentHasPreCourse(AllStudentsInClass.get(j));
    			 if(PreDidntFill.size()!=0)
    			 {
    				 if (StatusJunit==0)errorlog.setVisible(true);
    	     	    SName= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM user WHERE userID='" + AllStudentsInClass.get(j) +"'");
    	     	    NotAssigned=NotAssigned+"\n"+SName.get(0).get(1)+"\n Pre Courses:"; 
    	     	   if (StatusJunit==0)sem.setMyString2(sem.GetMyString2()+"\n---------------------------------------------\n"+"\nThe student: ("+AllStudentsInClass.get(j)+") - "+SName.get(0).get(1)+"\n didn't fill the pre courses:");
    	     	    for(int t=0;t<PreDidntFill.size();t++)
    				{
    	     	    	NotAssigned=NotAssigned+"\n"+PreDidntFill.get(t);
    	     	    	if (StatusJunit==0)sem.setMyString2(sem.GetMyString2()+"\n"+PreDidntFill.get(t));
    	     	    	if (StatusJunit==0)errorlog.setText(sem.GetMyString2());
    				}
    	     	    	if (StatusJunit==0)sem.setMyString2(sem.GetMyString2()+"\n--------------------------------------\n"+"The student ("+AllStudentsInClass.get(j)+") - "+SName.get(0).get(1)+" can't be assigned to the course: "+CourseChoise);
	    	    		if (StatusJunit==0)errorlog.setText(sem.GetMyString2());
	    	    		studentsNotAssigned.add(SName.get(0).get(1));
    			 }
    			 else
    			 {
    				 studentsAssigned.add(AllStudentsInClass.get(j));
    			 }
    			 
    		 }//Else the student not assigned to the course
    	}//Finish All the students in class.
 		//All the student's assigned
 		if(studentsNotAssigned.size()==0)
 		{
 			if (StatusJunit==0)diaID.setVisible(true);
 			if (StatusJunit==0)chooseteachertext.setVisible(true);
 			if (StatusJunit==0)teacherList.setVisible(true);
 	 		TeacherFlag=1;
 	 		if (StatusJunit==0)TakeTeacher();
 			return "Success";
 		}
 		else if(studentsNotAssigned.size()==AllStudentsInClass.size())
 		{
 			return "ALL";
 		}
 		else
 		{
 			if (StatusJunit==0)diaID.setVisible(true);
 			if (StatusJunit==0)chooseteachertext.setVisible(true);
 			if (StatusJunit==0)teacherList.setVisible(true);
 	 		TeacherFlag=1;
 	 		if (StatusJunit==0)TakeTeacher();
 			return NotAssigned;
 		}
	}//Func
	public void TakeTeacher()
	{
		//Suitable Course that was chosen- 1 course:
    	ArrayList<ArrayList<String>> teaching= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM courses WHERE idcourses='" + RequiredStringCourse +"'");
    	Course=new Course(teaching.get(0).get(0),teaching.get(0).get(1),teaching.get(0).get(2),teaching.get(0).get(3));
    	//Get teaching unit number:
    	String TeachingUnit=Course.getTeachingUnit();
    	//Getting all the teachers that in this teaching unit:
    	ArrayList<ArrayList<String>> TeachersID= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM teacherinteachunit WHERE teachUnitNum='" + TeachingUnit +"'");
    	//Defining the list of combobox:
    	ArrayList<String> listtteachers = new ArrayList<String>();
    	for (int i=0;i<TeachersID.size();i++)
	    {
    		//Get teacher in teacher table:
	    	ArrayList<ArrayList<String>> usersID= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM teacher WHERE teacherid='" + TeachersID.get(i).get(0) +"'");
	    	ArrayList<ArrayList<String>> TeachersName= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM user WHERE userID='" + usersID.get(0).get(0) +"'");
	    	Teacher.add(new Teacher(usersID.get(0).get(0),TeachersName.get(0).get(1),TeachersName.get(0).get(2),TeachersName.get(0).get(3),TeachersName.get(0).get(4),usersID.get(0).get(1),TeachersName.get(0).get(5)));
	    	listtteachers.add("("+usersID.get(0).get(0)+")"+" - "+TeachersName.get(0).get(1));
	    }
	    //------------------------------------------------------------------//
 	   ObservableList L= FXCollections.observableList(listtteachers);
 	   teacherList.setItems(L);
 	   //------------------------------------------------------------------//
	}
	//--------------------------------------------------------------------------//
	 public void PrintErr (String Message)
	{
		t.setText(Message);
		 Timer time = new Timer(8000, new java.awt.event.ActionListener() {
               @Override
               public void actionPerformed(java.awt.event.ActionEvent e) {
               	try{
               		t.setText("");
               	}catch(java.lang.NullPointerException e1){
               		
               	}
               }
           });
           time.setRepeats(false);
           time.start();
	}
	//--------------------------------------------------------------------------//
	 public ArrayList<String> TakeStudentsFromClass ()
	 {
		 int i;
		 ArrayList<String> StudentsArray=new ArrayList<String>();
		//Taking the students from the class:
	    ArrayList<ArrayList<String>> StudentsInClass= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM studentinclass WHERE identityclass='" + ClassChoise +"'"); 
	    for(i=0;i<StudentsInClass.size();i++)
		{
	    	StudentsArray.add(StudentsInClass.get(i).get(0));
		}
	    return StudentsArray;
	 }
	//--------------------------------------------------------------------------//
	 public ArrayList<String> TakePreCoursesOfCourse()
	 {
		 int i;
		 ArrayList<String> PreCoursesOfCourseArray;
	     ArrayList<ArrayList<String>> PreCoursesOfCourse= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM precourse WHERE currCourse='" + RequiredStringCourse +"'");
	     if(PreCoursesOfCourse==null)
	     {
	    	return null; 
	     }
	     else
	     {
	    	 PreCoursesOfCourseArray=new ArrayList<String>();
	    	 for(i=0;i<PreCoursesOfCourse.size();i++)
	    	 {
	    		 PreCoursesOfCourseArray.add(PreCoursesOfCourse.get(i).get(0));
	    	 }
	    	 return PreCoursesOfCourseArray;
	     }
	 }
	 //--------------------------------------------------------------------------//
	 public boolean CheckIfStudentAlradyAssigned (String StudentID, String Course)
	 {
 	    ArrayList<ArrayList<String>> CheckStudentAlreadyAssigned= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM studentincourse WHERE identityStudent='" + StudentID + "' AND identityCourse='" +Course+"'");
 	    if (CheckStudentAlreadyAssigned==null) return false;
 	    else return true;
	 }
	 //--------------------------------------------------------------------------//
	 public ArrayList<String> CheckIfStudentHasPreCourse (String Student)
	 {
		ArrayList<String> DidntFill=new  ArrayList<String>();
 	    ArrayList<ArrayList<String>> PreCourseOfStudent= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM studentinprecourse WHERE childID='" +  Student +"'");
 	    if(PreCourseOfStudent==null)
 	    {
 	    	for(int i=0;i<PreCoursesOfCourse.size();i++)
 	    	{
		        ArrayList<ArrayList<String>> CName= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM courses WHERE idcourses='" + PreCoursesOfCourse.get(i) +"'");
 	    		DidntFill.add(CName.get(0).get(1));
 	    	}
 	    }
 	    else 
 	    {
 	    	 for(int k=0;k<PreCoursesOfCourse.size();k++)
        	 {
        	    	for(int r=0;r<PreCourseOfStudent.size();r++)
        	    	{
        	    		if(PreCoursesOfCourse.get(k).equals(PreCourseOfStudent.get(r).get(1))==true && Integer.parseInt(PreCourseOfStudent.get(r).get(2))>55)//If found the pre course
        	    		{
        	    			break;
        	    		}
        	    		else if((r==PreCourseOfStudent.size()-1 && PreCoursesOfCourse.get(k).equals(PreCourseOfStudent.get(r).get(1))==false) || (r==PreCourseOfStudent.size()-1 && PreCoursesOfCourse.get(k).equals(PreCourseOfStudent.get(r).get(1))==true && Integer.parseInt(PreCourseOfStudent.get(r).get(2))<55))
        	    		{
    				        ArrayList<ArrayList<String>> CName= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM courses WHERE idcourses='" + PreCoursesOfCourse.get(k) +"'");
    				        DidntFill.add(CName.get(0).get(1));
        	    		}
        	    	} 
        	    }//Finish 1 student
 	    }//Else
 	   return DidntFill;
	 }
	 //--------------------------------------------------------------------------//
	  /**
	   * 
	   * The function AssignTeacher checks if the chosen teacher didn't exceed her teaching hours,
	   * and if not- assign's the chosen class to the chosen course with the chosen teacher.
	   * conditions first.
	   * @param event
	   */
		 @FXML
		 public void AssignTeacher(ActionEvent event)
		 {
			 String ClassAvg="0";
			 int hoursteacher;
			 int hoursCourse;
			 String res;
			 int res2;
			 int i=0;
			 int index=0;
			 int counter=0;
			 //------------------------------------------------------------------------// 
				 counter++;
			
				 String MyTeacher =  (String) teacherList.getValue();//Get The chosen teacher
				 if (MyTeacher!=null)
				 {
					      String TeacherID = MyTeacher .substring(MyTeacher .indexOf("(") + 1, MyTeacher .indexOf(")"));
				       
				    	  for(i=0;i<Teacher.size();i++)
				 	      {
				    		  if (Teacher.get(i).GetID().equals(TeacherID)==true) 
					 	      {
				    			  index=i;
				    			  break;
					 	      }
				 	      }
				    	//----------------------------------------------------------------------------//
				 	      hoursteacher=Integer.parseInt(Teacher.get(index).Gethours());
				 	      hoursCourse=Integer.parseInt(Course.getHours());
				 	      if (hoursteacher<hoursCourse)
				 	      {
				 	    	   t.setVisible(true);
				 	    	   t.setText("The Teacher exceed her teaching hours, please choose different teacher");
				 	    	  Timer time = new Timer(1500, new java.awt.event.ActionListener() {
					                @Override
					                public void actionPerformed(java.awt.event.ActionEvent e) {
					                	try{
					                		t.setText("");
					                	}catch(java.lang.NullPointerException e1){
					                		
					                	}
					                }
					            });
					            time.setRepeats(false);
					            time.start();
					            Platform.runLater(() -> teacherList.getSelectionModel().clearSelection());
				 	      }
				 	      else 
				 	      {
				 	    		res2=hoursteacher-hoursCourse;
				 	    		res=""+res2;
				 	    		Teacher.get(i).SetHours(res);
				 	    		
				 	    		finishtxt.setVisible(true);	    	 
				 	    		sem.setMyString(sem.GetMyString()+"\nTeacher:  "+MyTeacher+"\n------------------------------------------\n");
				 	    		DataField.setText(sem.GetMyString());
				 	    		transfferQueryToServer("INSERT INTO teacherinclassincourse (clasID,coID,AVG,Tidentity,SemesId) VALUES ('" + ClassChoise + "','" + RequiredStringCourse + "','" +ClassAvg+"','"+ Teacher.get(i).GetID()+"','"+ sem.getYear() + ":" + sem.getType() + "')");
					 	    	transfferQueryToServer("UPDATE teacher SET MaxHour="+res+" WHERE teacherid="+ Teacher.get(i).GetID()); //Update the status of previous semester
				 	    		for(int w=0;w<studentsAssigned.size();w++)
				 	    		{
						 	    		transfferQueryToServer("INSERT INTO studentincourse (identityStudent,identityCourse,Grade,IdenClas) VALUES ('" + studentsAssigned.get(w) + "','" + RequiredStringCourse + "','" +"0"+"','"+ClassChoise+"')");
								    	ArrayList<ArrayList<String>> c= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM studentinprecourse WHERE childID='" + studentsAssigned.get(w) + "' AND pCourseID='" +RequiredStringCourse+"'");
						 	    		if(c!=null)//If the student doing aggain the course
						 	    		{
								    		transfferQueryToServer("DELETE FROM studentinprecourse WHERE childID="+studentsAssigned.get(w)+" and pCourseID="+RequiredStringCourse+"");
						 	    		}
				 	    		}
					 	    	finishtxt.setText("The class: "+ClassChoise+" assgined successfully to the course: "+CourseChoise.substring(CourseChoise.indexOf(")") + 1,CourseChoise.length()));
					 	    	studentsAssigned=null;
					 	    	sem.setMyString2("");
					 	    	errorlog.setText(sem.GetMyString2());
					 	    	CourseL.getSelectionModel().clearSelection();
					 	    	ClassL.getSelectionModel().clearSelection();
					 	    	diaID.setVisible(false);
					 	    	chooseteachertext.setVisible(false);
					 	    	teacherList.setVisible(false);
					 	    	Finish.setVisible(true);
					 	    	TeacherFlag=0;
				 	    		
				 	    		Timer time = new Timer(2500, new java.awt.event.ActionListener() {
				 	   		    @Override
				 	   		    public void actionPerformed(java.awt.event.ActionEvent e) {
				 	   		    try{
				 	   		           	finishtxt.setText("");
				 	   		            }catch(java.lang.NullPointerException e1){
				 	   		                		
				 	   		            }
				 	   		            }
				 	   		     });
				 	   		            time.setRepeats(false);
				 	   		            time.start();
				 	   		    
				 	    			    	 t.setText("");
				 	    			    	 t.setVisible(true);
				 	    			    	
				 	    			    	 diaID.setVisible(false);
				 	    			    	 chooseteachertext.setVisible(false);
				 	    			    	 teacherList.setVisible(false);
				 	    			    	
				 	    			    	 if (counter>0)
				 	    			    	 {
						 	    			    	Platform.runLater(() -> teacherList.getSelectionModel().clearSelection());
				 	    			    	 }
				 	    			  } 
				 }

		 }  //AssignTeacher
}
