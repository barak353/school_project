package SchoolManager;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.JFileChooser;

import Login.LoginController;
import Entity.User;
import application.QueryController;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import Student.WatchTaskController;

public class viewAllInformationController extends QueryController implements Initializable{

	// -----------------------------------------------------------//

	private static final int ArrayList = 0;

	public viewAllInformationController(String controllerID) {
		super(controllerID);
	}

	// -----------------------------------------------------------//

	Object nextController = null;

	@FXML
	private Button logout;

	@FXML
	private Button back;

    @FXML
    private Text ERRmsg;
	
	@FXML
	private Text userID;
	
    @FXML
    private ComboBox<String> chooseInfo;

    @FXML
    private TextArea infoArea;

    
    /**
     * Prints all information as stored in data base by choosing type of reports.
     * @param event
     */
    @FXML
    void printInfo(ActionEvent event) {
		boolean isNext = false;
    	infoArea.setText("");
    	String setText = "";
    	ERRmsg.setText("");
    	switch(chooseInfo.getValue()){
    	case "Semesters":
			setText += "<<Semesters:>> \n";
        	ArrayList<ArrayList<String>> semester = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT semID,status FROM semester");
    		if(semester == null){setText += "There is no courses from the current semester to show.\n"; infoArea.setText(setText); return;}
        	if(semester != null && semester.get(0) != null && semester.get(0).get(0) != null){
    			for(ArrayList<String> row : semester){
					setText += "Semester ID: " + row.get(0) + ", Status: " + row.get(1) + "\n";
					setText += "<<Courses in semester:>> \n";
		        	ArrayList<ArrayList<String>> semesters = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT idcourses,courseName FROM coursesinsemester,courses WHERE  Cid=idcourses AND Sem='" + row.get(0)+"'");
		    		if(semesters == null){setText += "There are no courses in this semester.\n"; infoArea.setText(setText);}
		        	if(semesters != null && semesters.get(0) != null && semesters.get(0).get(0) != null){
		    			for(ArrayList<String> row2 : semesters){
							setText += "Course ID: " + row2.get(0) + "Course Name: " + row2.get(1) +"\n";
		    			}
					}
					setText += "<<Courses in semester:>> \n";
		        	ArrayList<ArrayList<String>> pCourse = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT precourse FROM precourse WHERE currCourse='" + row.get(0)+"'");
		    		if(pCourse == null){setText += "There are no precourses to this course.\n"; infoArea.setText(setText);}
		        	if(pCourse != null && pCourse.get(0) != null && pCourse.get(0).get(0) != null){
		    			for(ArrayList<String> row2 : pCourse){
							setText += "preCourse ID: " + row2.get(0) + "\n";
		    			}
					}
					setText += "\n__________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________\n";
    			}
			}
    		infoArea.setText(setText);
    	break;
    	case "Students":
    		ArrayList<ArrayList<String>> students= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT StudentID,GPA,Age FROM student");
    		System.out.println("Students list: "+students);
    		if(students == null){setText += "There is no students to show."; infoArea.setText(setText); return;}
    		for(ArrayList<String> row : students){
    			if(students.get(0) != null && students.get(0).get(0) != null){
    				setText += "Student ID: " + row.get(0) + ", GPA: " + row.get(1) + ", Age: " + row.get(2);
    				ArrayList<ArrayList<String>> sUser= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT userName, userPSW, Type, Email, status FROM user,student WHERE userID="+ row.get(0));
    				if(sUser != null && sUser.get(0) != null && sUser.get(0).get(0) != null){
	    				setText += ", User Name: " + sUser.get(0).get(0) + ", User password: " + sUser.get(0).get(1) + ", Type: " + sUser.get(0).get(2)  + ", Email: " + sUser.get(0).get(3) + ", status: " + sUser.get(0).get(4) +"\n";
	        			
	    				setText += "\n<<Courses that this student took:>> \n";
	        			ArrayList<ArrayList<String>> sCourses= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT identityCourse,Grade,IdenClas FROM studentincourse WHERE identityStudent="+ row.get(0));
	        			if(sCourses == null){setText += "This student did not take any course.\n ";}
	    				else{
	    					for(ArrayList<String> row2 : sCourses){
			        			if(row2 != null && row2.get(0) != null){
			    					setText += "Course ID: " + row2.get(0) + ", Grade: " + row2.get(1) + ", Class ID: " + row2.get(2) +"\n";
			    				}
		    				}
	    				}
	        			
	    				setText += "<<Tasks that this student submitted:>> \n";
	        			ArrayList<ArrayList<String>> sTasks= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT mytaskname,semesterName,IDNcourse,Mark,grade,Comments FROM subtask WHERE stIDENT="+ row.get(0));
	        			if(sTasks == null){setText += "This student did not take any course.\n ";}
	    				else{
	    					for(ArrayList<String> row2 : sTasks){
			        			if(row2 != null && row2.get(0) != null){
			    					setText += "Task Name: " + row2.get(0) + ", Semester ID: " + row2.get(1) + ", Course ID: " + row2.get(2) + ", Mark: " + row2.get(3) + ", Grade: " + row2.get(4) + ", Comments: " + row2.get(5) +"\n";
			    				}
		    				}
	    				}
	        			
	    				setText += "<<Classes that this student is in:>> \n";
	        			ArrayList<ArrayList<String>> sClass= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT identityclass FROM studentinclass WHERE stuID="+ row.get(0));
	        			if(sClass == null){setText += "This student did not include in any class.\n ";}
	    				else{
	    					for(ArrayList<String> row3 : sClass){
			        			if(row3 != null && row3.get(0) != null){
			    					setText += "Class ID: " + row3.get(0) + "\n";
			    				}
		    				}
	    				}
	        			
	    				setText += "<<Courses that this student took in previous semesters:>> \n";
	        			ArrayList<ArrayList<String>> sPreCourse= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT pCourseID,FinalGrade,TheSemester FROM studentinprecourse WHERE childID="+ row.get(0));
	        			if(sPreCourse == null){setText += "This student did not took any previous courses..\n ";}
	    				else{
	    					for(ArrayList<String> row4 : sPreCourse){
			        			if(row4 != null && row4.get(0) != null){
			    					setText += "Course ID: " + row4.get(0) + ", Final Grade: " + row4.get(1) + ", Semester ID: " + row4.get(2) +"\n";
			    				}
		    				}
	    				}
	        			
	        			setText += "<<Students parents>> \n";
	        			ArrayList<ArrayList<String>> sParent= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT parentID,Block FROM childparent,parent WHERE PID=ParentID AND studenid="+ row.get(0));
	        			if(sParent == null){setText += "This student dosen't have parents\n ";}
	    				else{
	    					for(ArrayList<String> row4 : sParent){
			        			if(row4 != null && row4.get(0) != null){
			    					setText += "Parent ID: " + row4.get(0) + ", Block: " + row4.get(1) +"\n";
			    				}
		    				}
	    				}
    				}
    			}
				setText += "\n__________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________\n";
    		}
			infoArea.setText(setText);
    	break;
    	case "Teachers":
    		ArrayList<ArrayList<String>> teachers= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT teacherid,MaxHour FROM teacher");
    		if(teachers == null){setText += "There is no teachers to show.\n"; infoArea.setText(setText); return;}
			if(teachers != null && teachers.get(0) != null && teachers.get(0).get(0) != null){
    			for(ArrayList<String> row2 : teachers){
    				setText += "Teacher ID: " + row2.get(0) + ", Teacher Max Hour: " + row2.get(1);
    				ArrayList<ArrayList<String>> sUser= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT userName, userPSW, Type, Email, status FROM user,student WHERE userID="+ row2.get(0));
    				if(sUser != null && sUser.get(0) != null && sUser.get(0).get(0) != null){
	    				setText += ", User Name: " + sUser.get(0).get(0) + ", User password: " + sUser.get(0).get(1) + ", Type: " + sUser.get(0).get(2)  + ", Email: " + sUser.get(0).get(3) + ", status: " + sUser.get(0).get(4) +"\n";
    				}
	    			setText += "\n";
	    			if(teachers.get(0) != null && teachers.get(0).get(0) != null){
	    				setText += "<<Teacher's teaching unit:>> \n";
	    				ArrayList<ArrayList<String>> tUnit= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT TUnum,TUname FROM teachingunit,teacherinteachunit WHERE teachUnitNum=TUnum AND idenTeach="+ row2.get(0));
	        			if(tUnit == null){setText += "This teacher is not in any teaching unit.\n ";}
	        			else{
	    					for(ArrayList<String> row4 : tUnit){
			        			if(row4 != null && row4.get(0) != null){
			    					setText += "Teaching Unit ID: " + row4.get(0) + ", Teaching Unit Name: " + row4.get(1) +"\n";
			    				}
		    				}
	    				}
	    				setText += "<<Teacher's classes and courses:>> \n";
	    				ArrayList<ArrayList<String>> tClsCrs= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT clasID,coID,AVG,SemesId FROM teacherinclassincourse WHERE Tidentity=" + row2.get(0));
	        			if(tClsCrs == null){setText += "This teacher has no classes and courses.\n ";}
	        			else{
	    					for(ArrayList<String> row5 : tClsCrs){
			        			if(row5 != null && row5.get(0) != null){
			    					setText += "Class ID: " + row5.get(0) + ", Course ID: " + row5.get(1) + ", Semester ID: " + row5.get(3) + ", Class Average: " + row5.get(2) + "\n";
			    				}
		    				}
	    				}
	        			setText += "<<Tasks that this teacher submitted.>> \n";
	    				ArrayList<ArrayList<String>> tSUB= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT TaskName,IDsem,idcorse,SubDate,fileExtN FROM task WHERE Teach=" + row2.get(0));
	        			if(tSUB == null){setText += "This teacher has no submitted tasks.\n ";}
	        			else{
	    					for(ArrayList<String> row6 : tSUB){
			        			if(row6 != null && row6.get(0) != null){
			    					setText += "Task Name: " + row6.get(0) + ", Semester ID: " + row6.get(1) + ", Course ID: " + row6.get(2) + ", Submission Date: " + row6.get(3) + ", File Name: " + row6.get(4) + "\n";
			    				}
		    				}
	    				}
					}
					setText += "\n__________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________\n";
    			}
			}
			infoArea.setText(setText);
    	break;
    	case "Courses":
			setText += "<<Courses:>> \n";
        	ArrayList<ArrayList<String>> courses = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT idcourses,courseName,teachingUnit,hours FROM courses");
    		if(courses == null){setText += "There is no courses from the current semester to show.\n"; infoArea.setText(setText); return;}
        	if(courses != null && courses.get(0) != null && courses.get(0).get(0) != null){
    			for(ArrayList<String> row : courses){
					setText += "Couse ID: " + row.get(0) + ", Course Name: " + row.get(1) + ", Teaching Unit: " + row.get(2) + ", Hours: " + row.get(3) + "\n";
					setText += "<<Semesters when the course was transferred:>> \n";
		        	ArrayList<ArrayList<String>> semesters = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT Sem FROM coursesinsemester WHERE Cid=" + row.get(0));
		    		if(semesters == null){setText += "There are no semesters in which the course was transferred.\n"; infoArea.setText(setText);}
		        	if(semesters != null && semesters.get(0) != null && semesters.get(0).get(0) != null){
		    			for(ArrayList<String> row2 : semesters){
							setText += "Semester ID: " + row2.get(0) + "\n";
		    			}
					}
					setText += "<<Precourses:>> \n";
		        	ArrayList<ArrayList<String>> pCourse = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT precourse FROM precourse WHERE currCourse=" + row.get(0));
		    		if(pCourse == null){setText += "There are no precourses to this course.\n"; infoArea.setText(setText);}
		        	if(pCourse != null && pCourse.get(0) != null && pCourse.get(0).get(0) != null){
		    			for(ArrayList<String> row2 : pCourse){
							setText += "preCourse ID: " + row2.get(0) + "\n";
		    			}
					}
					setText += "\n__________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________\n";
    			}
			}
    		infoArea.setText(setText);
    	break;
    	case "Classes":
			setText += "<<Classes:>> \n";
        	ArrayList<ArrayList<String>> classes = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT classID FROM class");
    		if(classes == null){setText += "There is no classes to show.\n"; infoArea.setText(setText); return;}
        	if(classes != null && classes.get(0) != null && classes.get(0).get(0) != null){
    			for(ArrayList<String> row : classes){
    				setText += "Class ID: " + row.get(0) + "\n";
    				setText += "<<Students:>> \n";
		        	ArrayList<ArrayList<String>> pCourse = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT stuID FROM studentinclass WHERE identityclass='" + row.get(0) + "'");
		    		if(pCourse == null){setText += "There are no students in this class.\n"; infoArea.setText(setText);}
		        	if(pCourse != null && pCourse.get(0) != null && pCourse.get(0).get(0) != null){
		    			for(ArrayList<String> row2 : pCourse){
							setText += "Student ID: " + row2.get(0) + "\n";
		    			}
					}
					setText += "\n__________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________\n";
    			}
			}
    		infoArea.setText(setText);
    	break;
    	case "Tasks":
			setText += "<<Tasks:>> \n";
        	ArrayList<ArrayList<String>> tasks = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT TaskName,IDsem,idcorse,SubDate,fileExtN FROM task");
    		if(tasks == null){setText += "There is no tasks to show.\n"; infoArea.setText(setText); return;}
        	if(tasks != null && tasks.get(0) != null && tasks.get(0).get(0) != null){
    			for(ArrayList<String> row : tasks){
					setText += "Task Name: " + row.get(0) + ", Semester ID: " + row.get(1) + ", Course ID: " + row.get(2) + ", Submission Date: " + row.get(3) + ", File Name: " + row.get(4) + "\n";
					setText += "\n__________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________\n";
    			}
			}
    		infoArea.setText(setText);
    	break;
    	case "Users":
        	ArrayList<ArrayList<String>> users = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT userID, userName, userPSW, Type, Email, status FROM user");
    		if(users == null){setText += "There is no users to show.\n"; infoArea.setText(setText); return;}
        	if(users != null && users.get(0) != null && users.get(0).get(0) != null){
    			for(ArrayList<String> row : users){
    				setText += "User ID: " + row.get(0) + ", User Name: " + row.get(1) + ", User Password: " + row.get(2) + ", User Type: " + row.get(3) + ", Email: " + row.get(4) + ", Status: " + row.get(5) + "\n";
					setText += "\n__________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________\n";
    			}
			}
		infoArea.setText(setText);
    	break;
    	}
    }
    
    /**
     * function that return to the last screen
     * @param event
     */
	@FXML
	void TurningBack(ActionEvent event)
	{
		this.nextController = new SchoolManagerMainController("SchoolManagerMainController");
		this.Back("/SchoolManager/SchoolManagerMainWindow.fxml",nextController, event);
	}
	//--
    
    
    /**
     * this function initialize the screen whit the name of the user.
     */
    	@Override
		public void initialize(URL arg0, ResourceBundle arg1) {//this method perform when this controller scene is showing up.
			User user = User.getCurrentLoggedIn();
			userID.setText(user.GetUserName());
			ArrayList<String> chooseInfo = new ArrayList<String>();
			chooseInfo.add("Students");
			chooseInfo.add("Teachers");
			chooseInfo.add("Courses");
			chooseInfo.add("Semesters");
			chooseInfo.add("Users");
			chooseInfo.add("Classes");
			chooseInfo.add("Tasks");
		    ObservableList obList= FXCollections.observableList(chooseInfo);;
		    this.chooseInfo.setItems(obList);
		}
}

