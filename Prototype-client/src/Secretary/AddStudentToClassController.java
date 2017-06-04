package Secretary;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import User.User;
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
import Class.Class;
public class AddStudentToClassController extends QueryController  implements Initializable{

	Object nextController=null;
	@FXML
	private Button logout;
	@FXML
	private Button back;
    @FXML
    private Text userID;
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
	private int flag=0;
	//------------------------------------------------//
	public AddStudentToClassController(String controllerID)
	{
			super(controllerID);
			ChosenClass=new Class(ClassDefineController.GetClassName(),ClassDefineController.GetClassNum());
	} 
	//------------------------------------------------// 
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {//this method perform when this controller scene is showing up.
		User user = User.getCurrentLoggedIn();
		userID.setText(user.GetUserName());
	}
	//------------------------------------------------// 
    @FXML
    void TurningBack(ActionEvent event)
    {
    	this.flag=1;
    	SaveAndExit(event);
    	this.nextController = new ClassDefineController("ClassDefineController");
    	this.Back("/Secretary/ClassDefine.fxml",nextController, event);
    } 
	//------------------------------------------------// 
    @FXML
    void AddMore(ActionEvent event)
    {
    	StudentID.clear();
    } 
	//------------------------------------------------//
    @FXML
    void Save(ActionEvent event)
    {
    	int exists=0;
    	StID=StudentID.getText();
        if(StID.equals(""))
        {
        	ErrorMSG.setText("Please Enter Student ID");//show error message.
        	successMSG.setText("");
        }
        else
        {
            ArrayList<ArrayList<String>> resultArray= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM student WHERE studentID='" + StID + "'");
            if(resultArray==null)
            {
            	ErrorMSG.setText("There is NO such student");//show error message.
            	successMSG.setText("");
            }
            else
            {
            	//Check if the student is in right age for this class
            	studentDetails=resultArray.get(0);
            	if (studentDetails.get(4).equals(ChosenClass.GetName())==false)
            	{
            		ErrorMSG.setText("The student is not of the age of this class ");//show error message.
            		successMSG.setText("");
            		exists=2;
            	}
            	//Check if the student already exists in DB in some class
            	ArrayList<ArrayList<String>> r= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM studentinclass WHERE StudID='" + StID + "'");
            	for (int i=0;i<ChosenClass.GetStudent().size();i++) //Search the student if exists in the sub array
            	{
            		if (ChosenClass.GetStudent().get(i).equals(StID)==true) exists=1; 
            	}
            	if (r==null && exists==0 ) //The student not exists in DB and in the right age
            	{
            		studentDetails=resultArray.get(0); //Get first row
            		UID=studentDetails.get(1); //Get UserID
            		ArrayList<ArrayList<String>> resArray= (ArrayList<ArrayList<String>>) transfferQueryToServer("SELECT * FROM user WHERE userID='" + UID + "'");
            		studentDetails=resArray.get(0); //Get first raw
            		SName=studentDetails.get(1);
            		successMSG.setText("The student:  "+SName+"  was added to class.\nTo add another student to this class press on Add More button");
            		ErrorMSG.setText("");
            		ChosenClass.SetStudent(StID); //Insert to the array
            	}
            	else if (r!=null&& exists!=2) //If The student Exists in DB
            	{
            		studentDetails=r.get(0); //Get first row - Check if the student is in other class or in this class
            		if (studentDetails.get(0).equals(ChosenClass.GetNum())==false || studentDetails.get(2).equals(ChosenClass.GetName())==false )
            		{
            			ErrorMSG.setText("The student belongs to other class: " +studentDetails.get(2) + studentDetails.get(0));//show error message.
            			successMSG.setText("");
            		}
            		else
            		{
            			ErrorMSG.setText("The student is already in this class: " +ChosenClass.GetName() +ChosenClass.GetNum() );//show error message.
            			successMSG.setText("");
            		}
                	
            	}
            	else if (exists==1) //If the student in the array
            	{
            		ErrorMSG.setText("The student is already in this class: " +ChosenClass.GetName() +ChosenClass.GetNum() );//show error message.
        			successMSG.setText("");
            	}
            }
        }
    } 
	//------------------------------------------------//
    @FXML
    void SaveAndExit(ActionEvent event)
    {
    	String id;
    	String ClassN;
    	String Name;
    	//------------------------------------------------//
    	for (int i=0;i<ChosenClass.GetStudent().size();i++)
    	{
    		id=ChosenClass.GetStudent().get(i); //Get StudentID
    		ClassN=ChosenClass.GetNum();
    		Name=ChosenClass.GetName();
    		transfferQueryToServer("INSERT INTO studentinclass (ClassNumber,StudID,CLetter) VALUES ('" + ClassN + "','" + id + "','" + Name + "')");
    	}
    	ChosenClass.GetStudent().clear();
    	if (flag==0) //If want to exit to the main screen
    	{
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
    }
    //------------------------------------------------//
    @FXML
    void Log(ActionEvent event)
    {
    	this.flag=1;
    	SaveAndExit(event);
    	LogOutScreen(event);
    } 
    //------------------------------------------------//
}
