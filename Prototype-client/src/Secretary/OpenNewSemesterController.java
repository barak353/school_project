package Secretary;

import java.io.IOException;
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

import javax.swing.Timer;

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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * 
 * This controller handles the Open new semester action.
 */
public class OpenNewSemesterController extends QueryController implements Initializable{
	
	//-----------------------------------------------------------//
	public OpenNewSemesterController (String controllerID)
	{
			super(controllerID);
	}
	//-----------------------------------------------------------// 
	private Object nextController=null;	
	private ArrayList<ArrayList<String>> resultArray=null;
    @FXML
    private Button logout;
    @FXML
    private Text userID;
    @FXML
    private Button back;
    @FXML
    private Button OpenSemester;
    @FXML
    private Text currentDate;
    @FXML
    private Text errorText;
    @FXML
    private Text text;
    @FXML
    private Text t;
    @FXML
    private TextField type1;
    @FXML
    private DialogPane dialogID;
    @FXML
    private Text dialogText;
    @FXML
    private Button doneButton;
    @FXML
    private Button addButton;
    @FXML
    private ComboBox<?> courseList;
    @FXML
    private Label OpenNewSemester;
    @FXML
    private Text TypeText;
    @FXML
    private TextArea DataField;
    private String AllDetails="";
    private String Mystr="";
    //----------------------------------------------------------//
    /**
	 * 
	 * The function Open semester will check if the details of the new semester are correct,
	 * if so, the function will enter to the DB the new semester and update the status of the 
	 * previous one.
	 * @param event
	 */
    @FXML
    void openSemester(ActionEvent event) 
    {
    	boolean isValidInput = true;
    	String type = this.type1.getText();
    	if(!type.equals("A") && !type.equals("B") && !type.equals("C") && !type.equals("a") && !type.equals("b") && !type.equals("c"))
    	{
    		errorText.setText("Type most be just 'A' or 'B' or 'C'"); 
    		isValidInput = false;
    	}
    	//-------------------------------------------------------------------------------//
    	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy");
		LocalDateTime now = LocalDateTime.now();
    	ArrayList<ArrayList<String>> res= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM semester WHERE semID='" + dtf.format(now) + ":" + type + "'");
    	if (res!=null)
    	{
    	    errorText.setText("The semester: "+type+":"+dtf.format(now)+" already exists");  
    		isValidInput=false;
    	}
     	//Get courses List:
    	if (isValidInput==true)
    	{
    		resultArray= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM courses");
    		if(resultArray == null)
    		{
    			errorText.setText("There is no courses saved in the DB."); 
    			isValidInput = false;
    		}
    	}
    	//-------------------------------------------------------------------------------//
    	if(isValidInput == true)
    	{
        	//-------------------------------------------------------------------------------//
    		Semester semester = new Semester(dtf.format(now),this.type1.getText(),true,AllDetails,Mystr); //Creating new semester
    		
    		transfferQueryToServer("UPDATE semester SET status='false' WHERE status= 'true'"); //Update the status of previous semester
    		transfferQueryToServer("INSERT INTO semester (semID,status) VALUES ('" + semester.getYear() + ":" + semester.getType() + "','true')");// Set New semester
			
    		ArrayList<ArrayList<String>> StudentsInCourse= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM studentincourse");
		    if(StudentsInCourse!=null)
		    {
		    	for(int i=0;i<StudentsInCourse.size();i++)
		    	{
		    		transfferQueryToServer("INSERT INTO studentinprecourse (childID,pCourseID,FinalGrade,TheSemester) VALUES ('" + StudentsInCourse.get(i).get(0) + "','" + StudentsInCourse.get(i).get(1)+ "','" +StudentsInCourse.get(i).get(2)+"','"+semester.getCurrentSemester().getYear()+":"+semester.getCurrentSemester().getType()+"')");
		    		transfferQueryToServer("DELETE FROM studentincourse WHERE identityStudent="+StudentsInCourse.get(i).get(0)+" and identityCourse="+StudentsInCourse.get(i).get(1)+"");
		    	}
		    }
    		Semester.setCurrentSemester(semester);
    		//-------------------------------------------------------------------//
    		dialogText.setText("Choose course you want to add to semester: " + semester.getType() + "- " +semester.getYear());
    		//Change dialog:
    		AllDetails=AllDetails+"Semester: "+Semester.getCurrentSemester().getType()+":"+Semester.getCurrentSemester().getYear()+"\n";
    		DataField.setText(AllDetails);
    		AllDetails=AllDetails+"Courses:\n";
    		DataField.setVisible(true);
    		OpenNewSemester.setVisible(false);
    		TypeText.setVisible(false);
    		errorText.setVisible(false);
    		type1.setVisible(false);
    		OpenSemester.setVisible(false);
    		currentDate.setVisible(false);
    		dialogText.setVisible(true);
    		dialogID.setVisible(true);
    	    doneButton.setVisible(true);
    	    addButton.setVisible(true);
    	    text.setVisible(true);
    	    t.setVisible(true);
    	    //------------------------------------------------------------------//
    	    ArrayList<String> list = new ArrayList<String>();
    	    for(ArrayList<String> arr : resultArray)
    	    {
    	    	list.add("("+arr.get(0)+") - "+arr.get(1));
    	    }
    	    //------------------------------------------------------------------//
    	    ObservableList obList= FXCollections.observableList(list);
    	    courseList.setItems(obList);
    	    courseList.setVisible(true);
    	}
    	//------------------------------------------------------------------//
    }
    //-------------------------------------------------------------------------------------------//
    /**
	 * 
	 * The function add course will enter the chosen courses from the combobox to the DB, to 
	 * the table: courses in semester, this courses will be open in this new current semester.
	 * @param event
	 */
    @FXML
	void addCourse(ActionEvent event)
	{	
			int counter=0;
			boolean flag=true;
	     	ArrayList<ArrayList<String>> res=null;
			Semester semester=Semester.getCurrentSemester();
			String choise =  (String) courseList.getValue();//get the item that was pressed in the combo box.
	    	if (choise==null)
	    	{
	    		text.setText("");
	    		t.setText("Please fill all the details");
	    		flag=false;
	    	}
	    	else if (flag==true)
	    	{
	    		String requiredString = choise.substring(choise.indexOf("(") + 1, choise.indexOf(")"));//get the idcourses that is inside a ( ).
		    	res= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM coursesinsemester WHERE Cid='" + requiredString + "' AND Sem='" + semester.getYear() + ":" + semester.getType() + "'");
		    	if (res!=null)
		    	{
		    		text.setText("");
		    		t.setText("The course is already in this semester");
		    	}
		    	else
		    	{
		    		semester.setCourseList(choise);
		    		transfferQueryToServer("INSERT INTO coursesinsemester (Cid,Sem) VALUES ('" + requiredString + "','" + semester.getYear() + ":" + semester.getType() + "')");
		    		t.setText("");
		    		text.setText("The course added successfully");
		    		AllDetails=AllDetails+choise+"\n";
		    		DataField.setText(AllDetails);
		    		counter++;
		    		Timer t = new Timer(1000, new java.awt.event.ActionListener() {
		                @Override
		                public void actionPerformed(java.awt.event.ActionEvent e) {
		                	try{
		                		text.setText("");
		                	}catch(java.lang.NullPointerException e1){
		                		
		                	}
		                }
		            });
		            t.setRepeats(false);
		            t.start();
		    	}
	    	}
	}
	//-------------------------------------------------------------------------------------------//
    /**
	 * 
	 * The function done will finish the action of adding courses to the new semester, and will
	 * open new window of assigning classes to courses.
	 * @param event
	 */
    @FXML
	void done(ActionEvent event)
	{
		Semester.setMyString(AllDetails);
		 try {
			   FXMLLoader loader = new FXMLLoader(getClass().getResource("/Secretary/MergeClassesCourses.fxml"));
			   loader.setController(new MyNewController("MergeClassesCoursesController"));
			   Pane login_screen_parent = loader.load();
			        Scene login_screen_scene=new Scene(login_screen_parent);
					Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();//the scene that the event came from.
					app_stage.hide();
					app_stage.setScene(login_screen_scene);
					app_stage.show(); 
		        } catch (IOException e) {
					System.err.println("Missing MyNewController.fxml file");
					e.printStackTrace();
				}
	}
	    //-----------------------------------------------------------------------------------------//
    /**
	 * 
	 * The function Turning back return's to the main screen of the secretary.
	 * @param event
	 */
    	@FXML
		void TurningBack(ActionEvent event)
		{
			this.nextController = new SecretaryMainController("SecretaryMainControllerID");
			this.Back("/Secretary/SecretaryMainWindow.fxml",nextController,event);
		}
	
	    //-----------------------------------------------------------------------------------------//
    	/**
    	 * 
    	 * Initialize function, shows the logged in user, and show's the current date of opening new semester.
    	 * @param arg0
    	 * @param arg1
    	 */
    	@Override
		public void initialize(URL arg0, ResourceBundle arg1) {//this method perform when this controller scene is showing up.
			User user = User.getCurrentLoggedIn();
			userID.setText(user.GetUserName());
	        LocalDate localDate = LocalDate.now();
	        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	        LocalDateTime now = LocalDateTime.now();
	    	currentDate.setText("Open new semester for the current date: " + dtf.format(now));
		}
		//-----------------------------------------------------------------------------------------//
}