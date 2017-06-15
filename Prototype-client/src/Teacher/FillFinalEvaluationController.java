package Teacher;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

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
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;


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
    private ComboBox<String> CourseList;

    @FXML
    private Text userID;
    
    @FXML
    private Circle markTask;

    @FXML
    private Text textMSG;
    
  // private String chooseStudent;
	
    private boolean isTaskChoosed;
    private int isStudentChoosed=0;
    private String chooseTask;
    private String chooseCourse;
    /*---------------------------------------------------------------------------------*/
    @FXML
    void chooseTask(ActionEvent event) {
    	isTaskChoosed = true;
   
    }
    
    @FXML
    void TurningBack(ActionEvent event) {
    	this.nextController = new TeacherMainController("TeacherMainController");
    	this.Back("/Teacher/TeacherMain.fxml",nextController, event);
    }
    //
    @FXML
    void saveB(ActionEvent event) {
    	textMSG.setVisible(false);
    	if(isTaskChoosed){
	        chooseTask = TaskList.getValue();
	    	System.out.println("chooseTask: "+chooseTask);
	    	chooseTask = chooseTask.substring(chooseTask.indexOf("(") + 1, chooseTask.indexOf(")"));
	        chooseCourse = CourseList.getValue();
	    	chooseCourse = chooseCourse.substring(chooseCourse.indexOf("(") + 1, chooseCourse.indexOf(")"));
	    	
	    	String finalGrade = grade.getText();
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
	    	//insert into the DB the grade and the comments of specific student
	    	transfferQueryToServer("UPDATE subtask SET grade = "+ finalGrade +", Comments='"+ finalCom +"' WHERE idTASK="
	    							+chooseTask+" AND IDstudent="+StudentList.getValue()+" AND IDcourse="+ chooseCourse); 
	    	textMSG.setText("You have successfully inserted the data into the DB:\ngrade " +finalGrade +" to student: "+ StudentList.getValue() );
	    	textMSG.setVisible(true);
	    	//textMSG.setVisible(false);
    	}else{
	    	textMSG.setText("Please choose Task");
	    	textMSG.setVisible(true);
    	}
    	
    }
    
    public void initialize(URL arg0, ResourceBundle arg1) {//this method perform when this controller scene is showing up.
    	User user = User.getCurrentLoggedIn();
    	userID.setText(user.GetUserName());
    	String teacherID = user.GetID();
    	
    	ArrayList<ArrayList<String>> res = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT coID FROM teacherinclassincourse WHERE Tidentity="+teacherID);
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
    	}
    }
    
    @FXML
    void chooseCourse(ActionEvent event) {
    	textMSG.setVisible(false);
    	isTaskChoosed = false;
    	String chooseCourse = CourseList.getValue();
    	String idcourses = chooseCourse.substring(chooseCourse.indexOf("(") + 1, chooseCourse.indexOf(")"));//get the idcourses that is inside a ( ).
    	ArrayList<ArrayList<String>> res = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT identityStudent FROM studentincourse WHERE identityCourse="+idcourses);
    	if (res==null)
    	{
    		textMSG.setText("ther no information in the DB");
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
    }
    
   @FXML
    void chooseStudent(ActionEvent event) {
	   isTaskChoosed = false;
	   textMSG.setVisible(false);
	   isStudentChoosed = 1;
    	String chooseStudent =  StudentList.getValue();
    	System.out.println("chooseStudent: "+chooseStudent);
    	
       	ArrayList<ArrayList<String>> res = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT idTASK FROM subtask WHERE IDstudent="+chooseStudent);
       	System.out.println("res2: "+res);
       	if (res==null)
    	{
    		textMSG.setText("This student don't submmit any task.");
    		textMSG.setVisible(true);
    	}
		else {
	       	ArrayList<ArrayList<String>> res2;
	    	//create array list of task name and task id and show in the combobox
	    	ArrayList<String> TaskNameList = new ArrayList<String>();
	       	for(ArrayList<String> row:res){
	        	res2 = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT TaskName,idTASK FROM task WHERE idTASK="+row.get(0));
	        	TaskNameList.add(res2.get(0).get(0)+"("+res2.get(0).get(1)+")");
	    	}
	       	ObservableList obList= FXCollections.observableList(TaskNameList);
	    	TaskList.setItems(obList);	
			}
		}
 
 
   //func that return to the log in sfreen
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

}
	/* ArrayList<String> mark = new ArrayList<String> ();
ArrayList<ArrayList<String>> res = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT Mark FROM subtask WHERE idTASK="+chooseTask+ 
			" AND IDcourse="+ chooseCourse+" AND IDstudent="+ StudentList.getValue());
 System.out.println("res:" + res);
 if(res ==null){
		textMSG.setText("this student did not submmit the task ");
		textMSG.setVisible(true);
		markTask.setFill(Color.BLACK);
		markTask.setVisible(true);
 }
 else{
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
 }*/