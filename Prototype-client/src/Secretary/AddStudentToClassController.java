package Secretary;

import java.io.IOException;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.Timer;

import Entity.Class;
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
 * This controller handles the action of adding a student to class.
 *
 */
public class AddStudentToClassController extends QueryController  implements Initializable{

	Object nextController=null;
	@FXML
	private Button logout;
	@FXML
	private Button back;
    @FXML
    private Text userID;
	@FXML
	private Text ChosenC;
    @FXML
	private TextField StudentID;
    @FXML
   	private Button SaveAndExit;
	@FXML
	private Button AddMore;
	@FXML
	private Text ErrorMSG;
	@FXML
	private Text successMSG;
	private Class ChosenClass;
	private String StID;
	private String UID;
	private String SName;
	ArrayList<String> studentDetails = null;
	//------------------------------------------------//
	public AddStudentToClassController(String controllerID)
	{
			super(controllerID);
			ChosenClass=new Class(ClassDefineController.GetClassName());
	} 
	//------------------------------------------------// 
	 /**
  	 * Initialize function, shows the logged in user, and showing the chosen class in the previous screen.
  	 * @param arg0
  	 * @param arg1
  	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {//this method perform when this controller scene is showing up.
		User user = User.getCurrentLoggedIn();
		userID.setText(user.GetUserName());
		ChosenC.setText("Class: "+ClassDefineController.GetClassName());
		
	}
	//------------------------------------------------// 
	  /**
	 * 
	 * The function TurningBack return's to the class define window.
	 * @param event
	 */
	@FXML
    void TurningBack(ActionEvent event)
    {
        this.nextController = new ClassDefineController("ClassDefineController");
    	this.Back("/Secretary/ClassDefine.fxml",nextController, event);
    } 
	//------------------------------------------------// 
	  /**
		 * 
		 * The function AddMore clear the previous ID of the student.
		 * @param event
		 */
	@FXML
    void AddMore(ActionEvent event)
    {
    	StudentID.clear();
    } 
	//------------------------------------------------//
	  /**
			 * 
			 * The function Save check's the student ID that was entered and adding him to the Arraylist
			 * of students in the class.
			 * @param event
			 */
    @FXML
    void Save(ActionEvent event) //Enter StudentID:
    {
    	int exists=0;
    	StID=StudentID.getText();
        if(StID.equals(""))
        {
        	ErrorMSG.setText("Please Enter Student ID");//show error message.
        	successMSG.setText("");
        	Timer time = new Timer(1500, new java.awt.event.ActionListener() {
	                @Override
	                public void actionPerformed(java.awt.event.ActionEvent e) {
	                	try{
	                		ErrorMSG.setText("");
	                	}catch(java.lang.NullPointerException e1){
	                		
	                	}
	                }
	            });
	            time.setRepeats(false);
	            time.start();
        	
        }
        else
        {
            ArrayList<ArrayList<String>> resultArray= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM student WHERE StudentID='" + StID + "'");
            if(resultArray==null)
            {
            	ErrorMSG.setText("There is NO such student");//show error message.
            	successMSG.setText("");
              	Timer time = new Timer(1500, new java.awt.event.ActionListener() {
	                @Override
	                public void actionPerformed(java.awt.event.ActionEvent e) {
	                	try{
	                		ErrorMSG.setText("");
	                	}catch(java.lang.NullPointerException e1){
	                		
	                	}
	                }
	            });
	            time.setRepeats(false);
	            time.start();
            	
            }
            else
            {
            	//Check if the student is in right age for this class
            	studentDetails=resultArray.get(0);
            	if (studentDetails.get(2).equals(ChosenClass.GetName().substring(0, 1))==false)
            	{
            		ErrorMSG.setText("The student is not of the age of this class ");//show error message.
            		successMSG.setText("");
            	   	Timer time = new Timer(1500, new java.awt.event.ActionListener() {
    	                @Override
    	                public void actionPerformed(java.awt.event.ActionEvent e) {
    	                	try{
    	                		ErrorMSG.setText("");
    	                	}catch(java.lang.NullPointerException e1){
    	                		
    	                	}
    	                }
    	            });
    	            time.setRepeats(false);
    	            time.start();
            		exists=2;
            	}
            	//Check if the student already exists in DB in some class
            	ArrayList<ArrayList<String>> r= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM studentinclass WHERE stuID='" + StID + "'");
            	for (int i=0;i<ChosenClass.GetStudent().size();i++) //Search the student if exists in the sub array
            	{
            		if (ChosenClass.GetStudent().get(i).equals(StID)==true) exists=1; 
            	}
            	if (r==null && exists==0 ) //The student not exists in DB and in the right age
            	{
            		studentDetails=resultArray.get(0); //Get first row
            		UID=studentDetails.get(0); //Get UserID
            		ArrayList<ArrayList<String>> resArray= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM user WHERE userID='" + UID + "'");
            		studentDetails=resArray.get(0); //Get first raw
            		SName=studentDetails.get(1);
            		successMSG.setText("The student:  "+SName+"  was added to class.\nTo add another student to this class press on Add More button.");
            		ErrorMSG.setText("");
            		ChosenClass.SetStudent(StID); //Insert to the array
            	}
            	else if (r!=null&& exists!=2) //If The student Exists in DB
            	{
            		studentDetails=r.get(0); //Get first row - Check if the student is in other class or in this class
            		if (studentDetails.get(1).equals(ChosenClass.GetName())==false)
            		{
            			ErrorMSG.setText("The student belongs to other class: " +studentDetails.get(1));//show error message.
            			successMSG.setText("");
            		}
            		else
            		{
            			ErrorMSG.setText("The student is already in this class: " +ChosenClass.GetName());//show error message.
            			successMSG.setText("");
            		   	Timer time = new Timer(1500, new java.awt.event.ActionListener() {
        	                @Override
        	                public void actionPerformed(java.awt.event.ActionEvent e) {
        	                	try{
        	                		ErrorMSG.setText("");
        	                	}catch(java.lang.NullPointerException e1){
        	                		
        	                	}
        	                }
        	            });
        	            time.setRepeats(false);
        	            time.start();
            		}
                	
            	}
            	else if (exists==1) //If the student in the array
            	{
            		ErrorMSG.setText("The student is already in this class: " +ChosenClass.GetName());//show error message.
        			successMSG.setText("");
        		   	Timer time = new Timer(1500, new java.awt.event.ActionListener() {
    	                @Override
    	                public void actionPerformed(java.awt.event.ActionEvent e) {
    	                	try{
    	                		ErrorMSG.setText("");
    	                	}catch(java.lang.NullPointerException e1){
    	                		
    	                	}
    	                }
    	            });
    	            time.setRepeats(false);
    	            time.start();
            	}
            }
        }
    } 
	//------------------------------------------------//
    /**
	 * 
	 * The function SaveAndExit copy all the ArrayList of students that was assigned to the class to the DB.
	 * @param event
	 */
    @FXML
    void SaveAndExit(ActionEvent event)
    {
    	String id;
    	String Name;
    	//------------------------------------------------//
    	for (int i=0;i<ChosenClass.GetStudent().size();i++)
    	{
    		id=ChosenClass.GetStudent().get(i); //Get StudentID
    		Name=ChosenClass.GetName();
    		transfferQueryToServer("INSERT INTO studentinclass (stuID,identityclass) VALUES ('" + id + "','" + Name+"')");
    	}
    	ChosenClass.GetStudent().clear();
			 try {
				   FXMLLoader loader = new FXMLLoader(getClass().getResource("/Secretary/SecretaryMainWindow.fxml"));
				   loader.setController(new SecretaryMainController ("SecretaryMainController"));
				   Pane login_screen_parent = loader.load();
				        Scene login_screen_scene=new Scene(login_screen_parent);
						Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();//the scene that the event came from.
						app_stage.hide();
						app_stage.setScene(login_screen_scene);
						app_stage.show(); 
			        } catch (IOException e) {
						System.err.println("Missing SecretaryMainWindow.fxml file");
						e.printStackTrace();
					}
    }
    //------------------------------------------------//
}
