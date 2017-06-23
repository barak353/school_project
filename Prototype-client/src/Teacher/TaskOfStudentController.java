 package Teacher;

import application.QueryController;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.JFileChooser;

import javafx.scene.control.ComboBox;
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

/**
 * 
 * this controller handles the action: checks home work of student by teacher
 * 
 */

public class TaskOfStudentController extends QueryController implements Initializable {

	Object nextController=null;	
//-----------------------------------------------------------//

public TaskOfStudentController(String controllerID)
{
		super(controllerID);
}

		@FXML
		private Button logout;
		
		@FXML
		private Button openFile;
		
		@FXML
		private ComboBox<String> StudentList;
		
		@FXML
		private Button save;
		
		@FXML
		private Button back;
		
		@FXML
		private Text userID;
		
		 @FXML
		 private Text textMSG;
		 
	    @FXML
	   private Button uploadFile;

	    @FXML
	    private Circle markTask;
	  
		 @FXML
		 private Text courseName;
		 
		 @FXML
		 private Text TaskName;
		 
		  private String courseID;
		  private String courseN;
		  private String taskName;
		  private File file;
		  private int isstudentChoosed = 0;  
		
		  
		    /**
		     * function that return to the last screen
		     * @param event
		     */
		    
		 @FXML
		void TurningBack(ActionEvent event) {//func that return to the last screen
			this.nextController = new ChecksHomeworkController("ChecksHomeworkController");
			this.Back("/Teacher/ChecksHomework.fxml",nextController, event);
		}
		 
	 /**
	  * this function initialize the screen whit the name of the user,name of the course that chosen and the combobox of the student 
	  */
	 
	public void initialize(URL arg0, ResourceBundle arg1) {//this method perform when this controller scene is showing up.
		User user = User.getCurrentLoggedIn();
		userID.setText(user.GetUserName());
		courseName.setText(courseN);
		TaskName.setText(taskName);
		ObservableList obList;
		//A query that shows students who Registered to this course
		String sem = Semester.getCurrentSemester().getYear()+":"+Semester.getCurrentSemester().getType();
		ArrayList<ArrayList<String>> res = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT stIDENT FROM subtask WHERE IDNcourse="+courseID
																									+" AND mytaskname='"+ taskName +"'"+" AND semesterName='"+ sem +"'");
		System.out.println("task-res: "+res);
		if (res==null)
		{
			textMSG.setText("This student dose'nt submit the task.");
			textMSG.setVisible(true);
		    obList= FXCollections.observableList(new ArrayList());;
		    StudentList.setItems(obList);
		}
		else{
		ArrayList<String> studentList = new ArrayList<String>();
		//loop for insert the id of the student to array list for the combobox
		for(ArrayList<String> row:res){
			studentList.add(row.get(0));
		}
		//print the array list in the combbox
	    obList= FXCollections.observableList(studentList);;
	    StudentList.setItems(obList);
		}
	}
	
    @FXML
    void openFile(ActionEvent event) {
    	//downloadFileFromServer(folder, secondFolder);
    	File file = new File("file//test.*");
    }
    
	   /**
     *  After pressing the save button, this function insert to the DB the file whit the comment of the teacher about the submission of the student
     * @param event
     */
	@FXML
	void saveB(ActionEvent event) {
		if(isstudentChoosed==0){//Check whether the user has selected a student
			textMSG.setText("you didn't choose student");
			textMSG.setVisible(true);
			return;
			
		}
	
		//save file to server.
		Object ans = uploadFileToServer(file,courseID);
		
	}
	 /**
     *  After choose student in the combobox, this function show the mark 
     *  about the submission of this student in the task that the teacher was chosen.
     *  this mark show if the student submit the task and if yes, if the student submit late or in time.
     * @param event
     */ 
	@FXML
	void chooseStudent(ActionEvent event) {
	   isstudentChoosed = 1;
	   ArrayList<String> mark = new ArrayList<String> ();
	   String chooseStudent= StudentList.getValue();
       Semester semester = Semester.getCurrentSemester();
       String IDsem = Semester.getCurrentSemester().getYear()+":"+Semester.getCurrentSemester().getType();
	   ArrayList<ArrayList<String>> res = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT Mark FROM subtask WHERE mytaskname='"+taskName+ 
				"' AND IDNcourse="+ courseID+" AND stIDENT="+ chooseStudent + " AND semesterName='"+IDsem+"'");
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
	   }
	   
	
	}
	/**
	 * function that upload file to the DB - the file is the comments of the teacher in the submission of the student
	 * @param event
	 */
	
	@FXML
	void upload(ActionEvent event) {//func that upload a file into the DB
		JFileChooser chooser= new JFileChooser();
		int choice = chooser.showOpenDialog(chooser);
		if (choice != JFileChooser.APPROVE_OPTION) return;
		file = chooser.getSelectedFile();
		if (file.exists())
			System.out.println("file or directory denoted by this abstract pathname exists.");
		else
			System.out.println("file or directory denoted by this abstract pathname is not exists.");
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
	
	public void setCourseID(String courseID) {
		this.courseID = courseID;
		
	}
	
	public void setCourseN(String courseN) {
		this.courseN = courseN;
		
	}
	
	public void setTaskName(String taskName) {
		this.taskName = taskName;
		
	}
	

}	
