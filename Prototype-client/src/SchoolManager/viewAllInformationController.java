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

    @FXML
    void printInfo(ActionEvent event) {
		boolean isNext = false;
    	infoArea.setText("");
    	String setText = "";
    	ERRmsg.setText("");
    	switch(chooseInfo.getValue()){
    	case "Semesters":
    		setText += "Semesters: \n  -------------- \n";
    		ArrayList<ArrayList<String>> res= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM semester");
    		if(res == null || res.get(0) == null){setText += "/n**There is no class in semester in the system.**/n/n";infoArea.setText(setText); isNext = true;}
    		if(isNext == false){
	    		for(ArrayList<String> row : res){
	    			if(row.get(0) != null){setText += "Semester ID: " + row.get(0) +", status: " + row.get(1) + "\n";}
	    		}
    		}
    		isNext = false;
    		setText += "\nCourses in semesters: \n  -------------- \n";
    		ArrayList<ArrayList<String>> res2= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM courses");
    		if(res2 == null || res2.get(0) == null){setText += "/n**There is no class in semester in the system.**/n/n";infoArea.setText(setText); isNext = true;}
    		if(isNext == false){
	    		for(ArrayList<String> row : res2){
	    			if(row.get(0) != null){setText += "Semester ID: " + row.get(1) +", Course ID: " + row.get(0) + "\n";}
	    		}
    		}
    		isNext = false;
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
					}
					setText += "\n__________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________\n";
    			}
			}
			infoArea.setText(setText);
    	break;
    	case "Courses":
    		
    	break;
    	case "Classes":
    		
    	break;
    	case "Tasks":
        	ArrayList<ArrayList<String>> semestersC = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT count(semID) FROM semester");

        	ArrayList<ArrayList<String>> semesters = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT semID FROM semester");
        	if(semesters != null){
        		if(semesters.get(0) != null){
        			for(ArrayList<String> row : semesters){
        				
        				
        			}
        		}        		
        	}
    	break;
    	case "Users":
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
			chooseInfo.add("Semesters");
			chooseInfo.add("Classes");
			chooseInfo.add("Courses");
			chooseInfo.add("Teachers");
			chooseInfo.add("Users");
			chooseInfo.add("Tasks");
			chooseInfo.add("Students");
		    ObservableList obList= FXCollections.observableList(chooseInfo);;
		    this.chooseInfo.setItems(obList);
		}
}

