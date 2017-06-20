package Teacher;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.util.ResourceBundle;

import javax.swing.JFileChooser;

import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import Login.LoginController;
import Entity.User;
import application.QueryController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * 
 * this controller handles the action: checks home work of student by teacher
 * 
 */

public class UploadTaskController extends QueryController implements Initializable {

		Object nextController=null;	
	//-----------------------------------------------------------//
	
	public UploadTaskController(String controllerID)
	{
			super(controllerID);
	}
	//-----------------------------------------------------------//
    @FXML
    private Button logout;
    
    @FXML
    private Button uploadFile;

    @FXML
    private Button back;

    @FXML
    private Button save;

    @FXML
    private Text userID;
    
    @FXML
    private TextField TaskName;
    
    @FXML
    private Text courseName;
    
    @FXML
    private Text textMSG;
    
     @FXML
    private DatePicker setDate;
     
    private String courseID;
    private String courseN;
    
    private File file;

	//-----------------------------------------------------------//
    
    @FXML
	void TurningBack(ActionEvent event) {//return to the last screen
    	this.nextController = new SetUpTaskController("SetUpTaskControllerID");
    	this.Back("/Teacher/SetUpTask.fxml",nextController, event);
    }
	
    public void initialize(URL arg0, ResourceBundle arg1) {//this method perform when this controller scene is showing up.
    	User user = User.getCurrentLoggedIn();
    	userID.setText(user.GetUserName());
    	courseName.setText(courseN);
    }
    @FXML
    void LogOut(ActionEvent event) {//return to the log in screen
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
    
    public void setCourseN(String courseN){
    	this.courseN = courseN;
    }
    
    @FXML
    void saveB(ActionEvent event) {//func insert information into the DB
        LocalDate now = LocalDate.now();
    	LocalDate choseDate =(LocalDate)setDate.getValue();
    	System.out.println(now +","+choseDate);
    	if(TaskName.getText().trim().isEmpty()){
    		textMSG.setText("you don't insert name of task");
    		textMSG.setVisible(true);
    		return;
    	}
    	String task = TaskName.getText();
    	ArrayList<ArrayList<String>> res = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT TaskName FROM task WHERE idcorse="+courseID);
      	ArrayList<String> listTask = new ArrayList() ;
      	System.out.println("res:"+ res);
    	//create array list of task name 
    	ArrayList<String> TaskNameList = new ArrayList<String>();
    	System.out.println("tasklistis:"+TaskNameList);
    	for(int i=0;i<res.size();i++){
    		listTask.addAll(res.get(i));
    		
    	}
       	System.out.println("tasklistis2:"+listTask);
       	for(int i=0;i<listTask.size();i++)
    	if(task.equals(listTask.get(i))){
    		textMSG.setText("This task name already exists");
    		textMSG.setVisible(true);
    		return;
    	}
    	if(setDate.getValue()==null){
    		textMSG.setText("you don't insert submmision date of task");
    		textMSG.setVisible(true);
    		return;
    	}
    
    	
        if (now.compareTo(choseDate) > 0) {//check if the date is pass
            System.out.println(now +"is after"+ choseDate);
    	
    		textMSG.setText("the submmision date is pass");
    		textMSG.setVisible(true);
    		return;
   	} 
    	
    	transfferQueryToServer("INSERT INTO task (TaskName,idcorse,SubDate) VALUES ('" + TaskName.getText() + "', " 
    							+ courseID + ",'" +setDate.getValue()+"')");
    	textMSG.setText("You have successfully inserted the data into DB:\ntask " +TaskName.getText()
    					+" to course: "+courseID );
    	textMSG.setVisible(true);
		System.out.println("send file to server");

		Object ans = uploadFileToServer(file,courseID);
		System.out.println("arrived");
    }

	@FXML
	void upload(ActionEvent event) {
		JFileChooser chooser= new JFileChooser();
		int choice = chooser.showOpenDialog(chooser);
		if (choice != JFileChooser.APPROVE_OPTION) return;
		file = chooser.getSelectedFile();
		if (file.exists())
			System.out.println("file or directory denoted by this abstract pathname exists.");
		else
			System.out.println("file or directory denoted by this abstract pathname is not exists.");
	}
    
    
	public void setCourseID(String courseID) {
		this.courseID = courseID;
		// TODO Auto-generated method stub
		
	}

}
