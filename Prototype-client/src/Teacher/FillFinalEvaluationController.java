package Teacher;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Login.LoginController;
import Entity.Semester;
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
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

/**
 * 
 * this controller handles the action: fill final evaluation of student in specific task, by teacher
 * first, the teacher choose the course she teaches ,then choose task and then, choose the student she want to fill about him
 * 
 */


public class FillFinalEvaluationController extends QueryController implements Initializable {

	Object nextController=null;	
	//-----------------------------------------------------------//
	
	public FillFinalEvaluationController(String controllerID)
	{
			super(controllerID);
	}
	
    @FXML
    private Button logout;

    @FXML
    private ComboBox<String> StudentList;

    @FXML
    private TextArea comments;

    @FXML
    private ComboBox<String> TaskList;

    @FXML
    private TextField grade;

    @FXML
    private Button save;

    @FXML
    private Button back;
    
    @FXML
    private Button AVG;

    @FXML
    private ComboBox<String> CourseList;

    @FXML
    private Text userID;
    
    @FXML
    private Circle markTask;

    @FXML
    private Text textMSG;
    
    @FXML
    private Text semesterID;
  // private String chooseStudent;
	
    private boolean isTaskChoosed=false;
    private boolean isCourseChoosed = false;
    private boolean isStudentChoosed = false;
    private String chooseTask;
    private String chooseCourse;
    private String idcourses;
    private int flag;
    private Semester semester;
    private String studentID;
    private String teacherID;
    /*---------------------------------------------------------------------------------*/

    /**
     * function that return to the last screen
     * @param event
     */
    
    @FXML
    void TurningBack(ActionEvent event) {
    	this.nextController = new TeacherMainController("TeacherMainController");
    	this.Back("/Teacher/TeacherMain.fxml",nextController, event);
    }
    
    /**
     *  After pressing the save button, this function insert to the DB the grade and the comments about specific task in course
     * @param event
     */
    @FXML
    void saveB(ActionEvent event){
    	textMSG.setVisible(false);
    	if(!isCourseChoosed){
    		textMSG.setText("please choose course!");
    		textMSG.setVisible(true);
    		return;
    	}
    	else{
    		if(!isTaskChoosed){
    			textMSG.setText("please choose task!");
        		textMSG.setVisible(true);
        		return;
    		}
    		else{
    			if(isStudentChoosed){
	    		chooseTask = TaskList.getValue();
		    	//chooseCourse = CourseList.getValue();
		    	System.out.println("CourseList.getValue(): "+CourseList.getValue());
		    	chooseCourse =  CourseList.getValue().substring(CourseList.getValue().indexOf("(") + 1, CourseList.getValue().indexOf(")"));
		    	System.out.println("chooseCourse: "+chooseCourse);
		    	String finalGrade = grade.getText();
		    	if(grade.getText().trim().isEmpty()){
		    		textMSG.setText("please insert grade");
	        		textMSG.setVisible(true);
	        		return;
		    	}
		    	int fnlGrade;
		    	try{
		    		fnlGrade = Integer.parseInt(finalGrade);
		    	}
		    	catch(NumberFormatException e){
		    		textMSG.setText("Final grade most contain only numbers.");
		    		textMSG.setVisible(true);
		    		return;
		    	}
		    	if(fnlGrade > 100 || fnlGrade < 0){
		    		textMSG.setText("Final grade most be between 100 to 0.");
		    		textMSG.setVisible(true);
		    		return;
		    	}
		    	String finalCom = comments.getText();
		        if(comments.getText().trim().isEmpty()){
		    		textMSG.setText("Please insert a comment.");
		    		textMSG.setVisible(true);
		    		return;
		    	}
		        if(flag ==0){//if the student did not submit the task(he dont exists in the table
		        	String sem = Semester.getCurrentSemester().getYear() + ":" + Semester.getCurrentSemester().getType();
		        	transfferQueryToServer("INSERT INTO subtask (semesterName,mytaskname, IDNcourse, stIDENT, grade, Comments) VALUES ('"+sem+"','"+chooseTask+"',"+chooseCourse+","
		        			+StudentList.getValue()+","+finalGrade +",'"+ finalCom +"')");	
		        	textMSG.setText("You have successfully inserted the data into the DB:\ngrade " +finalGrade +" to student: "+ StudentList.getValue() );
		        	textMSG.setVisible(true);

		        }
		        else{
		    	//insert into the DB the grade and the comments of specific student
		        String sem = Semester.getCurrentSemester().getYear()+":"+ Semester.getCurrentSemester().getType();
		      
		    	transfferQueryToServer("UPDATE subtask SET grade = "+ finalGrade +", Comments='"+ finalCom +"' WHERE mytaskname='"
		    							+chooseTask+"' AND stIDENT="+StudentList.getValue()+" AND IDNcourse="+ chooseCourse + " AND semesterName='"+ sem +"'"); 
		    	textMSG.setText("You have successfully inserted the data into the DB:\ngrade " +finalGrade +" to student: "+ StudentList.getValue() );
		    	textMSG.setVisible(true);
		        }
		        //update GPA of student in course
		    	String avg = new String();
		       	System.out.println("stIDENT="+StudentList.getValue());
		       	System.out.println("IDNcourse="+idcourses);
		       	ArrayList<ArrayList<String>> res = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT AVG(grade) FROM subtask WHERE stIDENT="+StudentList.getValue()+
		    		        		" AND IDNcourse="+idcourses);
		        if(res == null){
		        	System.out.println("error");
		       		
		        }
		 		System.out.println("avg:"+res);
		       	avg = res.get(0).get(0);
		       	float avg1 = Float.parseFloat(avg);
		       	String avgsave = String.format("%.2f", avg1);
		       	System.out.println("avg1:"+avgsave);
		       	 transfferQueryToServer("UPDATE studentincourse SET Grade='"+avgsave+"' WHERE identityStudent="+StudentList.getValue()+" AND identityCourse="+idcourses);
		       	 //update the general GPA of the student
		     	String gpa = new String();
		       	System.out.println("stIDENT="+StudentList.getValue());
		       	   	ArrayList<ArrayList<String>> res1 = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT AVG(Grade) FROM studentincourse WHERE identityStudent="+StudentList.getValue());
		        if(res1 == null){
		        	System.out.println("error");
		       		
		        }
		       	System.out.println("gpa:"+gpa);
		       	gpa = res1.get(0).get(0);
		       	float gpa1 = Float.parseFloat(gpa);
		       	String gpasave = String.format("%.2f", gpa1);
		       	System.out.println("gpa1:"+gpasave);
		       	 transfferQueryToServer("UPDATE student SET GPA='"+gpasave+"' WHERE StudentID="+StudentList.getValue());
    			
    			}else{
		    	textMSG.setText("Please choose student!");
		    	textMSG.setVisible(true);
	    	}
	    	}
	    	}
    	
    }
    
    /**
     * this function initialize the screen white the name of the user, and the combobox  of course
     */
    
    public void initialize(URL arg0, ResourceBundle arg1) {//this method perform when this controller scene is showing up.
    	User user = User.getCurrentLoggedIn();
    	userID.setText(user.GetUserName());
    	 teacherID = user.GetID();
    	
    	ArrayList<ArrayList<String>> res = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT DISTINCT coID FROM teacherinclassincourse WHERE Tidentity="+teacherID);
    	if (res==null)
    	{
    		textMSG.setText("there is no courses in the DB.");
    		textMSG.setVisible(true);
    	}
    	else{
    	ArrayList<String> courseNameList = new ArrayList<String>();
    	ArrayList<ArrayList<String>> res2;
    	//loop that create array list of the name and id of the courses
    	for(ArrayList<String> row:res){
        	res2 = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT courseName,idcourses FROM courses WHERE idcourses="+row.get(0));
    		courseNameList.add(res2.get(0).get(0)+"("+res2.get(0).get(1)+")");
    	}
    	//print the array list in the combbox
	    ObservableList obList= FXCollections.observableList(courseNameList);;
	    CourseList.setItems(obList);
    	semester = Semester.getCurrentSemester();
    	String IDsem = Semester.getCurrentSemester().getYear()+":"+Semester.getCurrentSemester().getType();
	    semesterID.setText(semesterID.getText()+IDsem);
    	}
    }
    
    /**
     *  After choose course in the combobox, this function initialize the combobox of task in this course
     * @param event
     */
    
    @FXML
    void chooseCourse(ActionEvent event) {
    	textMSG.setVisible(false);
    	 chooseCourse = CourseList.getValue();
    	System.out.println("chooseCourse: "+chooseCourse);
    	String IDsem = Semester.getCurrentSemester().getYear()+":"+Semester.getCurrentSemester().getType();
    	 idcourses = chooseCourse.substring(chooseCourse.indexOf("(") + 1, chooseCourse.indexOf(")"));//get the idcourses that is inside a ( ).
    	 System.out.println("idcourses: "+idcourses);
    	 ArrayList<ArrayList<String>> res = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT TaskName FROM task WHERE idcorse="+idcourses+" AND IDsem='"+IDsem+"'"+
    			 " AND Teach="+ teacherID);
           	if (res==null)
        	{
        		textMSG.setText("There are no assignments in this course");
        		textMSG.setVisible(true);
        	}
    		else {
    	       	ArrayList<ArrayList<String>> res2;
    	    	//create array list of task name and task id that this teacher uploaded and show in the combobox
    	    	ArrayList<String> TaskNameList = new ArrayList<String>();
    	       	for(ArrayList<String> row:res){
    	        	//res2 = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT TaskName,idTASK FROM task WHERE idTASK="+row.get(0));
    	        	if(row != null) TaskNameList.add(row.get(0));
    	    	}
    	       	ObservableList obList= FXCollections.observableList(TaskNameList);
    	    	TaskList.setItems(obList);	
    	    	isCourseChoosed = true;
    			}
    }
    
    /**
     *  After choose task in the combobox, this function initialize the combobox of student in this course
     * @param event
     */
    
    @FXML
    void chooseTask(ActionEvent event) {
    	if(isCourseChoosed == true){
	    	chooseTask = TaskList.getValue();
			ArrayList<ArrayList<String>> res1 = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT clasID FROM teacherinclassincourse WHERE Tidentity="+teacherID+
					" AND coID="+idcourses);
			if(res1==null){
				textMSG.setText("There is no class that registered to this course with this teacher.");
				textMSG.setVisible(true);
			}
			ArrayList<String> clas = new ArrayList<String>();
			for(ArrayList<String> row:res1){
	    	    if(row != null) clas.add(row.get(0));
	    	}
			System.out.println("class="+clas);

			//A query that shows Students registered for this course, and in this class the teacher teaches
			String sem = Semester.getCurrentSemester().getYear()+":"+Semester.getCurrentSemester().getType();
			if(clas.isEmpty()){
				textMSG.setVisible(true);
				textMSG.setText("There is no class that registered to this course with this teacher."); 
				return;
				}
			
				String query="SELECT identityStudent FROM studentincourse WHERE";
				for(String c: clas){
					query += " IdenClas=" +"'"+ c+"'" + " OR";
				}
				query = query.substring(0, query.length() - 2);
				ArrayList<ArrayList<String>> res = (ArrayList<ArrayList<String>>)
						transfferQueryToServer(query);
	    	if (res==null)
	    	{
	    		textMSG.setText("there is no information in the DB");
	    		textMSG.setVisible(true);
	    	}
    		else {
		    	ArrayList<String> resultArray = new ArrayList<String>();
		    	//loop for insert the id of the student to array list for the combobox
		    	for(ArrayList<String> row:res){
		    		resultArray.add(row.get(0));
		    	}
		    	ObservableList obList= FXCollections.observableList(resultArray);
		    	StudentList.setItems(obList);
			}
	    	isTaskChoosed = true;
    	}
    }
    
    /**
     *  After choose student in the combobox, this function show the mark 
     *  about the submission of this student in the task that the teacher was choose.
     *  this mark show if the student submit the task and if yes, if the student submit late or in time.
     * @param event
     */ 
   @FXML
    void chooseStudent(ActionEvent event) {
	   if(isTaskChoosed == true){
		   	studentID = StudentList.getValue();
		   	System.out.println("studentID: "+studentID);
		   	isStudentChoosed = true;
		   textMSG.setVisible(false);
		   String  TaskName = TaskList.getValue();
				   //chooseTask.substring(chooseTask.indexOf("(") + 1, chooseTask.indexOf(")"));//get the idtask that is inside a ( ).
		   ArrayList<String> mark = new ArrayList<String> ();
		   String sem = Semester.getCurrentSemester().getYear() + ":" + Semester.getCurrentSemester().getType();
		   ArrayList<ArrayList<String>> res = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT Mark FROM subtask WHERE mytaskname='"+TaskName+ 
		   			"' AND IDNcourse="+ idcourses +" AND stIDENT="+ studentID + " AND semesterName='"+sem+"'");
		    System.out.println("res:" + res);
		    //check the submition of the relevant student
		    if(res == null){
		   		textMSG.setText("this student did not submmit the task ");
		   		textMSG.setVisible(true);
		   		markTask.setFill(Color.BLACK);
		   		markTask.setVisible(true);
		   		flag = 0;
		    }
		    else{
		    	flag=1;
		   	   mark = res.get(0);
		   	   System.out.println("mark:" + mark);
		   	   char[] chars = mark.toString().toCharArray();
		   	   System.out.println("chars:" + chars[0]);
		   	   if(chars[1]=='1')
		   	   {
		   		   textMSG.setText("this student submmit the task Late ");
		   		   textMSG.setVisible(true);
		   		   markTask.setFill(Color.RED);
		   		   markTask.setVisible(true);
		   	   }
		   	   else{
		   	   textMSG.setText("this student submmit the task in time ");
		   	   textMSG.setVisible(true);
		   	   markTask.setFill(Color.GREEN);
		   	   markTask.setVisible(true);
		   	   }
		    }
	    }
		}
 /**
  * function that return to the log in screen
  * @param event
  */
 

    @FXML
    void LogOut(ActionEvent event) {
		 try 
		 {
			    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login/LoginWindow.fxml"));
		        loader.setController(new LoginController("LoginController"));
			    Pane login_screen_parent = loader.load();
			        Scene login_screen_scene=new Scene(login_screen_parent);
					Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();//the scene that the event came from.
					app_stage.hide();
					app_stage.setScene(login_screen_scene);
					app_stage.show(); 
		        } 
		 catch (IOException e) {
				System.err.println("Missing LoginWindow.fxml file");
				e.printStackTrace();
				}
    }

    @FXML
    void CalAvg(ActionEvent event) {
    	String avg = new String();
   	System.out.println("stIDENT="+StudentList.getValue());
   	System.out.println("IDNcourse="+idcourses);
   	ArrayList<ArrayList<String>> res = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT AVG(grade) FROM subtask WHERE stIDENT="+StudentList.getValue()+
		        		" AND IDNcourse="+idcourses);
    if(res == null){
    	System.out.println("error");
   		
    }
   	System.out.println("avg:"+res);
   	avg = res.get(0).get(0);
   	float avg1 = Float.parseFloat(avg);
   	String avgsave = String.format("%.2f", avg1);
   	System.out.println("avg1:"+avgsave);
   	 transfferQueryToServer("UPDATE studentincourse SET Grade='"+avgsave+"' WHERE identityStudent="+StudentList.getValue()+" AND identityCourse="+idcourses);
    }

}