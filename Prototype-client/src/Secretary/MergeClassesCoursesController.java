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
import javafx.scene.text.Text;

public class MergeClassesCoursesController extends QueryController implements Initializable {
	
	  private Object nextController=null;
	  @FXML
      private Text chooseteachertext;
	  @FXML
      private ComboBox<?> teacherList;
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
      private Button Assign;
      @FXML
      private Text t;
      @FXML
      private Button Next;
      @FXML
      private DialogPane diaID;
      private String courseID;
      private Semester sem;
      @FXML
      private Text err;
      @FXML
      private DialogPane dialog2;
      @FXML
      private Text finishtxt;
      @FXML
      private TextArea DataField;
      private String CourseChoise;
      private String RequiredStringCourse;
      private String  ClassChoise;
      private ArrayList<Teacher> Teacher=new ArrayList<Teacher>();
      private Course Course;
      private boolean TeachChoiseFlag=false;
      private boolean flag=true;
      private int counterPrint=0;
      private int OK=0;
      private ArrayList<String> studentsAssigned;
      @FXML
      private TextArea errorlog;
	  //-----------------------------------------------------------//
	  public MergeClassesCoursesController (String controllerID)
	  {
			super(controllerID);
			studentsAssigned=new ArrayList<String>();
	  }
	  //-----------------------------------------------------------// 
	  @FXML
	  void AssignHandler(ActionEvent event) 
	  {
		    this.OK=0;
		    boolean PreFlag=true;
		    int counterStudents=0;
		    flag=true;
			CourseChoise =  (String) CourseL.getValue();//get the item that was pressed in the combo box.
		    ClassChoise = (String) ClassL.getValue();
		    //--------------------------------------------------//
		   
		    if (CourseChoise==null || ClassChoise==null || TeachChoiseFlag==true )
		    {
		    	if (TeachChoiseFlag==true)
		    	{
		    		err.setText("Please fill all the details");	
		    		flag=true;
		    	}
		    	else
		    	{
		    		t.setText("Please fill all the details");	
		    		flag=false;
		    	}
		    	
		    }
		    //------------------------------------------------------------------//
		    else //If all the fields was filled:
		    {
		    	
		    	RequiredStringCourse = CourseChoise.substring(CourseChoise.indexOf("(") + 1, CourseChoise.indexOf(")"));
		    	ArrayList<ArrayList<String>> result= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM teacherinclassincourse WHERE clasID='" + ClassChoise + "' AND coID='" +RequiredStringCourse+ "'");
			    if (result!=null) //The class already assigned to the course
			    {
					t.setText("The class: "+ClassChoise+" is already assgined to the course: "+CourseChoise.substring(CourseChoise.indexOf(")") + 1,CourseChoise.length()));
					flag=false;
			    }
			    else 
			    {
			    	
			    	//-----------------------------------------------------------------------------------------------------------------------------------//

			    	//-------------Checking the students:-----------//
			    	//Taking the students from the class:
			        ArrayList<ArrayList<String>> StudentsInClass= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM studentinclass WHERE identityclass='" + ClassChoise +"'");
			        //Taking the pre courses of the course:
			        ArrayList<ArrayList<String>> PreCoursesOfCourse= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM precourse WHERE currCourse='" + RequiredStringCourse +"'");
			        if(PreCoursesOfCourse==null) //There is no pre courses for the current course
			        {
			        	sem.setMyString(sem.GetMyString()+"------------------------------------------\n"+"Class: "+ClassChoise+" -->  Course: "+CourseChoise);
				    	DataField.setText(sem.GetMyString());
			        	OK=1;
			        	for(int j=0;j<StudentsInClass.size();j++)
			        	{
			        		studentsAssigned.add(StudentsInClass.get(j).get(0));
			        	}
			        }
			        else
			        {
			        	//For each student in the class:
				        for(int j=0;j<StudentsInClass.size();j++)
				        {
				    	    ArrayList<ArrayList<String>> CheckStudentAlreadyAssigned= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM studentincourse WHERE identityStudent='" + StudentsInClass.get(j).get(0) + "' AND identityCourse='" +RequiredStringCourse+"'");
				    	    if(CheckStudentAlreadyAssigned!=null) //If the student already assigned alone to the course
				    	    {
				    	    	err.setText("The student already assigned to the course: "+CourseChoise+" by exceptional registration.");
				    	    }
				    	    else //Check pre courses of student:
				    	    {
				    	    	//Taking the pre courses of the student:
				        	    ArrayList<ArrayList<String>> PreCourseOfStudent= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM studentinprecourse WHERE childID='" +  StudentsInClass.get(j).get(0) +"'");
				        	    if(PreCourseOfStudent==null) //If the student has no pre courses at all
				        	    {
				        	    	errorlog.setVisible(true);
				        	    	PreFlag=false;
				        	    	ArrayList<ArrayList<String>> SName= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM user WHERE userID='" + StudentsInClass.get(j).get(0) +"'");
		        				    sem.setMyString2(sem.GetMyString2()+"\nThe student: ("+StudentsInClass.get(j).get(0)+") - "+SName.get(0).get(1)+"\n didn't fill the pre courses:");
		        	    			for(int k=0;k<PreCoursesOfCourse.size();k++)
		        	    			{
			        				    ArrayList<ArrayList<String>> CName= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM courses WHERE idcourses='" + PreCoursesOfCourse.get(k).get(0) +"'");
		        				        sem.setMyString2(sem.GetMyString2()+"\n"+"("+PreCoursesOfCourse.get(k).get(0)+") - "+CName.get(0).get(1)+"\n--------------------------------------------");
		        				        errorlog.setText(sem.GetMyString2());
		        	    			}
				        	    }
				        	    else
				        	    {
				        	    	 for(int k=0;k<PreCoursesOfCourse.size();k++)
						        	 {
						        	    	for(int r=0;r<PreCourseOfStudent.size();r++)
						        	    	{
						        	    		if(PreCoursesOfCourse.get(k).get(0).equals(PreCourseOfStudent.get(r).get(1))==true && PreCourseOfStudent.get(r).get(2).compareTo("55")>=0)//If found the pre course
						        	    		{
						        	    			break;
						        	    		}
						        	    		else if(r==PreCourseOfStudent.size()-1 && PreCoursesOfCourse.get(k).get(0).equals(PreCourseOfStudent.get(r).get(1))==false)
						        	    		{
						        	    			errorlog.setVisible(true);
						        	    			PreFlag=false; //The student dont match 1 pre course
						        	    			if(counterPrint==0) 
						        	    			{
						        				        ArrayList<ArrayList<String>> SName= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM user WHERE userID='" + StudentsInClass.get(j).get(0) +"'");
						        				        sem.setMyString2(sem.GetMyString2()+"The student: ("+StudentsInClass.get(j).get(0)+") - "+SName.get(0).get(1)+"\n didn't fill the pre courses:");
						        	    				errorlog.setText(sem.GetMyString2());
						        	    			}
						        	    			counterPrint++;
					        				        ArrayList<ArrayList<String>> CName= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM courses WHERE idcourses='" + PreCoursesOfCourse.get(k).get(0) +"'");
					        				        sem.setMyString2(sem.GetMyString2()+"\n"+"("+PreCoursesOfCourse.get(k).get(0)+") - "+CName.get(0).get(1)+"\n--------------------------------------------");
					        				       
					        				        errorlog.setText(sem.GetMyString2());
						        	    		}
						        	    	} 
						        	    }//Finish 1 student
				        	    	
				        	    	 errorlog.setText(sem.GetMyString2());
				        	    }
				    	    }
				    	    
				    	    if(PreFlag==false)
				    	    {
				    	    	err.setVisible(true);
				    	    	err.setText("The student: "+StudentsInClass.get(j).get(0)+"  can't be assigned to the course: "+CourseChoise);
				    	    	counterStudents++;
				    	    }
				    	    else
				    	    {
				    	    	studentsAssigned.add(StudentsInClass.get(j).get(0));
				    	    }
				    	    counterPrint=0;
				    	    PreFlag=true;
				        }//Finish all students in class
				        
				        if(counterStudents==StudentsInClass.size())
				        {
				        	err.setVisible(true);
				        	err.setText("All the students in the class:  "+ ClassChoise +" doesn't fill the pre condotions.\nCan't assign the class to the course.");
				        }
				        else //Can assign the class without the exceptional students:
				        {
				        	sem.setMyString(sem.GetMyString()+"------------------------------------------\n"+"Class: "+ClassChoise+" -->  Course: "+CourseChoise);
					    	DataField.setText(sem.GetMyString());
				        	OK=1;
				        }
			        }
			    	//-----------------------------------------------------------------------------------------------------------------------------------//
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
			    	 //---------------------------------------------------//
			    	   
			    	   //err.setText("");
			    	   t.setVisible(false);
			    	   err.setVisible(true);
			    	   diaID.setVisible(true);
			    	   chooseteachertext.setVisible(true);
			    	   teacherList.setValue(null);
			    	   teacherList.setVisible(true);
			    	   TeachChoiseFlag=true;
			    	   flag=true;
			    	 //---------------------------------------------------//
			    } //Else- the class not assigned to the course yet
		    }// Else - all the fields were filled
	  }
    //-----------------------------------------------------------------------------------------//
	@FXML
	void TurningBack(ActionEvent event)
	{
		this.nextController = new OpenNewSemesterController("OpenNewID");
		this.Back("/Secretary/OpenNewSemesterWindow.fxml",nextController,event);
	}
	//-----------------------------------------------------------------------------------------//
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
		    ArrayList<ArrayList<String>> res= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM class");
			if(res==null)
			{
				t.setText("There is no classes in the DB.");
			}
			else
			{
				  ArrayList<String> list2 = new ArrayList<String>();
				    for (int i=0;i<res.size();i++)
				    {
				    	list2.add(res.get(i).get(0));
				    }
				    //------------------------------------------------------------------//
				    ObservableList comlist= FXCollections.observableList(list2);
				    ClassL.setItems(comlist);
				    ClassL.setVisible(true);
			}
		}
	}
	//-----------------------------------------------------------------------------------------//
	 @FXML
	 void AssignTeacher(ActionEvent event)
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
			       
			          //err.setText("");
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
			 	    	   err.setText("The Teacher exceed her teaching hours, please choose different teacher");
			 	    	   flag=true;
			 	      }
			 	      else if(flag==true)
			 	      {
			 	    		res2=hoursteacher-hoursCourse;
			 	    		res=""+res2;
			 	    		Teacher.get(i).SetHours(res);
			 	    		
			 	    		finishtxt.setVisible(true);	    	 
			 	    		sem.setMyString(sem.GetMyString()+"\nTeacher:  "+MyTeacher+"\n------------------------------------------\n");
			 	    		DataField.setText(sem.GetMyString());
			 	    		if(OK==1)
			 	    		{
			 	    			transfferQueryToServer("INSERT INTO teacherinclassincourse (clasID,coID,AVG,Tidentity,SemesId) VALUES ('" + ClassChoise + "','" + RequiredStringCourse + "','" +ClassAvg+"','"+ Teacher.get(i).GetID()+"','"+ sem.getYear() + ":" + sem.getType() + "')");
				 	    		transfferQueryToServer("UPDATE teacher SET MaxHour="+res+" WHERE teacherid="+ Teacher.get(i).GetID()); //Update the status of previous semester
			 	    			for(int w=0;w<studentsAssigned.size();w++)
			 	    			{
					 	    		transfferQueryToServer("INSERT INTO studentincourse (identityStudent,identityCourse,Grade) VALUES ('" + studentsAssigned.get(w) + "','" + RequiredStringCourse + "','" +"0"+"')");
			 	    			}
				 	    		finishtxt.setText("The class: "+ClassChoise+" assgined successfully to the course: "+CourseChoise.substring(CourseChoise.indexOf(")") + 1,CourseChoise.length()));
				 	    		err.setText("");
			 	    		}
			 	    		
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
			 	    			    	// err.setVisible(false);
			 	    			    	 diaID.setVisible(false);
			 	    			    	 chooseteachertext.setVisible(false);
			 	    			    	 teacherList.setVisible(false);
			 	    			    	 TeachChoiseFlag=false;
			 	    			    	
			 	    			    	 if (counter>0)
			 	    			    	 {
					 	    			    	Platform.runLater(() -> teacherList.getSelectionModel().clearSelection());
			 	    			    	 }
			 	    			  } 
			 }

	 }  //AssignTeacher
}//Class


