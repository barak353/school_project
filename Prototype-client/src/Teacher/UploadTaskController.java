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
import Entity.Semester;
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
    private boolean isUploaded = false;
    private File file;
    private boolean isDateSetted = false;
    private String teacherID;
	//-----------------------------------------------------------//
    /**
     * function that return to the last screen
     * @param event
     */
    @FXML
	void TurningBack(ActionEvent event) {//return to the last screen
    	this.nextController = new SetUpTaskController("SetUpTaskControllerID");
    	this.Back("/Teacher/SetUpTask.fxml",nextController, event);
    }
    
    /**
     * this function initialize the screen white the name of the user
     */
    
    public void initialize(URL arg0, ResourceBundle arg1) {//this method perform when this controller scene is showing up.
    	User user = User.getCurrentLoggedIn();
    	userID.setText(user.GetUserName());
    	teacherID = user.GetID();
    	courseName.setText(courseN);
    	isDateSetted = false;
    	isUploaded = false;
    }
    /**
     * function that return to the log in screen
     * @param event
     */ 
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
    
    
    /**
     * this function check if the user set date
     * @param event
     */
    
    @FXML
    void setDate(ActionEvent event) {
		textMSG.setText("");
    	isDateSetted = true;
    }
    
    @FXML
    void saveB(ActionEvent event) {//func insert information into the DB
		textMSG.setText("");
    	if(setDate.getValue()==null){
    		textMSG.setText("Please insert submission date of task");
    		textMSG.setVisible(true);
    		return;
    	}
    	if(isUploaded == false){textMSG.setText("Please upload file.");return;}
    	if(isDateSetted == false){textMSG.setText("Please set date.");return;}
        LocalDate now = LocalDate.now();
    	LocalDate choseDate =(LocalDate)setDate.getValue();
    	if(TaskName.getText().trim().isEmpty()){
    		textMSG.setText("Please insert task name.");
    		textMSG.setVisible(true);
    		return;
    	}
    	String task = TaskName.getText();
    	Semester semester = Semester.getCurrentSemester();
    	String IDsem = Semester.getCurrentSemester().getYear()+":"+Semester.getCurrentSemester().getType();
    	ArrayList<ArrayList<String>> res = (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT TaskName FROM task WHERE idcorse="+courseID+" AND IDsem='"+IDsem+"' AND "
    			+ "Teach="+teacherID);
    	if(res != null){
    		for(ArrayList<String> row: res){
    			System.out.println(row.get(0)+", task:" + task);
    			if(((String)row.get(0)).equals(task)){textMSG.setText("This task name is already exists.");return;}
    		}
    	}
        if (now.compareTo(choseDate) > 0) {//check if the date is pass
    		textMSG.setText("the submmision date is pass");
    		textMSG.setVisible(true);
    		return;
   	} 
    	transfferQueryToServer("INSERT INTO task (TaskName,IDsem,idcorse,SubDate,fileExtN,Teach) VALUES ('" + TaskName.getText() + "', " 
    							+ "'"+IDsem+"'," + courseID + ",'" +setDate.getValue()+"','" + file.getName() + "',"+ User.getCurrentLoggedIn().GetID() +")");
    	textMSG.setText("You have successfully inserted the data into DB:\ntask " +TaskName.getText()
    					+" to course: "+courseID );
    	textMSG.setVisible(true);
    	String[] parts = IDsem.split(":");
    	String part1 = parts[0]; // year
    	String part2 = parts[1]; // type
    	IDsem = parts[0]+parts[1];
		Object ans = uploadFileToServer(file,IDsem+"//"+courseID);
		isDateSetted = false;
    }

	@FXML
	void upload(ActionEvent event) {
		textMSG.setText("");
		JFileChooser chooser= new JFileChooser();
		int choice = chooser.showOpenDialog(chooser);
		if (choice != JFileChooser.APPROVE_OPTION) return;
		file = chooser.getSelectedFile();
		if (file.exists())
			System.out.println("file or directory denoted by this abstract pathname exists.");
		else
			System.out.println("file or directory denoted by this abstract pathname is not exists.");
		isUploaded = true;
	}
    
    
	public void setCourseID(String courseID) {
		this.courseID = courseID;
	}

}
