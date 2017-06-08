package Secretary;

import java.net.URL;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;

import Login.LoginController;
import Entity.Course;
import Entity.Semester;
import SystemManager.SystemManagerAddCourseController;
import Entity.User;
import application.QueryController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class OpenNewSemesterController extends QueryController implements Initializable{
	
	//-----------------------------------------------------------//
	
	public OpenNewSemesterController (String controllerID)
	{
			super(controllerID);
	}
	
	//-----------------------------------------------------------// 

	Object nextController=null;	

    @FXML
    private Button logout;

    @FXML
    private Text userID;

    @FXML
    private Button back;

    @FXML
    private TextField type;

    @FXML
    private Text currentDate;
    
    @FXML
    private Button doneButton;

    @FXML
    private Button addButton;

    @FXML
    private DialogPane dialogID;
    
    @FXML
    private Text dialogText;

    @FXML
    private Text errorText;
    
    @FXML
    private ComboBox<String> couseList;
    
    @FXML
    void openSemester(ActionEvent event) {
    	errorText.setText("");
    	boolean isValidInput = true;
    	String type = this.type.getText();
    	if(!type.equals("A") && !type.equals("B") && !type.equals("C") && !type.equals("a") && !type.equals("b") && !type.equals("c")){errorText.setText("Type most be just 'A' or 'B' or 'C'"); isValidInput = false;}
    	Object obj = null;
    	int r = 0;
        ArrayList<ArrayList<String>> resultArray= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM courses");
    	if(resultArray == null){errorText.setText("There is no courses saved in the DB."); isValidInput = false;}
    	if(isValidInput == true){
    		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy");
    		LocalDateTime now = LocalDateTime.now();
            System.out.println(dtf.format(now));
    		Semester sem = new Semester(dtf.format(now)+":"+this.type.getText(),true);
    		dialogText.setText("Choose from the comboBox which course you want to add to semester:  "+sem.getidSemester());
    		Semester.setCurrentSemester(sem);
    		dialogText.setVisible(true);
    		dialogID.setVisible(true);
    	    doneButton.setVisible(true);
    	    addButton.setVisible(true);
    	    ArrayList<String> list = new ArrayList<String>();
    	    for(ArrayList<String> arr : resultArray){
    	    	list.add("("+arr.get(0)+")"+arr.get(1));
    	    }
    	    couseList.setVisible(true);
    	    ObservableList obList= FXCollections.observableList(list);;
    	    couseList.setItems(obList);
    	    //insert new semester detail to DB.
        	String query = "INSERT INTO semester (idsemester,status) VALUES ('" + Semester.getCurrentSemester().getidSemester() + "','true')";
        	System.out.println("query: "+ query);
        	transfferQueryToServer(query);
    	}

    }
    
    

	    @FXML
	    void addCourse(ActionEvent event) {
	    	String course = couseList.getValue();
	    	String idcourses = course.substring(course.indexOf("(") + 1, course.indexOf(")"));//get the idcourses that is inside a ( ).
	    	String query = "INSERT INTO courseinsemester (idcourseinsemester,cid) VALUES ('" + Semester.getCurrentSemester().getidSemester() + "','"+idcourses+"')";
	    	System.out.println("query: "+ query);
	    	System.out.println("idcourses: "+idcourses+"Semester.getCurrentSemester().getidSemester(): "+Semester.getCurrentSemester().getidSemester());
	    	transfferQueryToServer(query);
	    }
	    
	    @FXML
	    void done(ActionEvent event) {
    		dialogText.setVisible(false);
    		dialogID.setVisible(false);
    	    doneButton.setVisible(false);
    	    doneButton.setVisible(false);
    	    addButton.setVisible(false);
    	    couseList.setVisible(false);
	    }//hide the add courses window when done as pressed.

		@FXML
		void TurningBack(ActionEvent event)
		{
			this.nextController = new SecretaryMainController("SecretaryMainControllerID");
			this.Back("/Secretary/SecretaryMainWindow.fxml",nextController, event);
		}

		
	    @FXML
	    void addCourseToSemester(ActionEvent event) {
	    }
	    
		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {//this method perform when this controller scene is showing up.
			User user = User.getCurrentLoggedIn();
			userID.setText(user.GetUserName());
	        LocalDate localDate = LocalDate.now();
	        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
	        LocalDateTime now = LocalDateTime.now();
	    	currentDate.setText("Open new semester for the current date: " + dtf.format(now));
		}
}